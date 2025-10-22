package br.ifsp.arsw.esdemo.presentation.controller.account;

import  br.ifsp.arsw.esdemo.presentation.dto.DomainEventDto;
import br.ifsp.arsw.esdemo.infrastructure.audit.AccountEventReader;
import br.ifsp.arsw.esdemo.domain.account.commands.DepositMoneyCommand;
import br.ifsp.arsw.esdemo.domain.account.commands.OpenAccountCommand;
import br.ifsp.arsw.esdemo.domain.account.commands.WithdrawMoneyCommand;
import br.ifsp.arsw.esdemo.query.account.model.AccountSummary;
import br.ifsp.arsw.esdemo.query.account.repository.AccountSummaryRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final CommandGateway commandGateway;
    private final AccountSummaryRepository repository;
    private final AccountEventReader accountEventReader;

    public AccountController(CommandGateway commandGateway, AccountSummaryRepository repository, AccountEventReader accountEventReader) {
        this.commandGateway = commandGateway;
        this.repository = repository;
        this.accountEventReader = accountEventReader;
    }

    @PostMapping
    public CompletableFuture<String> open(@RequestParam long initial) {
        String id = UUID.randomUUID().toString();
        return commandGateway.send(new OpenAccountCommand(id, initial))
                .thenApply(r -> id);
    }

    @PostMapping("/{id}/deposit")
    public CompletableFuture<Void> deposit(@PathVariable String id, @RequestParam long amount) {
        return commandGateway.send(new DepositMoneyCommand(id, amount));
    }

    @PostMapping("/{id}/withdraw")
    public CompletableFuture<Void> withdraw(@PathVariable String id, @RequestParam long amount) {
        return commandGateway.send(new WithdrawMoneyCommand(id, amount));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountSummary> get(@PathVariable String id) {
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/events")
    public List<DomainEventDto> listEvents(@PathVariable String id) {
        return accountEventReader.list(id);
    }
}
