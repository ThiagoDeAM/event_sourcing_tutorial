package br.ifsp.arsw.esdemo.query.account.projector;

import br.ifsp.arsw.esdemo.command.account.events.AccountOpenedEvent;
import br.ifsp.arsw.esdemo.command.account.events.MoneyDepositedEvent;
import br.ifsp.arsw.esdemo.command.account.events.MoneyWithdrawnEvent;
import br.ifsp.arsw.esdemo.query.account.model.AccountSummary;
import br.ifsp.arsw.esdemo.query.account.repository.AccountSummaryRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@ProcessingGroup("account-projections")
public class AccountProjector {
    private final AccountSummaryRepository repository;

    public AccountProjector(AccountSummaryRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(AccountOpenedEvent e) {
        repository.save(new AccountSummary(e.accountId(), e.initialBalance(), Instant.now()));
    }

    @EventHandler
    public void on(MoneyDepositedEvent e) {
        repository.findById(e.accountId()).ifPresent(s -> {
            s.setBalance(s.getBalance() + e.amount());
            s.setLastUpdate(Instant.now());
            repository.save(s);
        });
    }

    @EventHandler
    public void on(MoneyWithdrawnEvent e) {
        repository.findById(e.accountId()).ifPresent(s -> {
            s.setBalance(s.getBalance() - e.amount());
            s.setLastUpdate(Instant.now());
            repository.save(s);
        });
    }
}
