package br.ifsp.arsw.esdemo.command.account.aggregate;

import br.ifsp.arsw.esdemo.command.account.commands.DepositMoneyCommand;
import br.ifsp.arsw.esdemo.command.account.commands.OpenAccountCommand;
import br.ifsp.arsw.esdemo.command.account.commands.WithdrawMoneyCommand;
import br.ifsp.arsw.esdemo.command.account.events.AccountOpenedEvent;
import br.ifsp.arsw.esdemo.command.account.events.MoneyDepositedEvent;
import br.ifsp.arsw.esdemo.command.account.events.MoneyWithdrawnEvent;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@Aggregate(snapshotTriggerDefinition = "snapshotTrigger")
@JsonAutoDetect(fieldVisibility = ANY)
public class BankAccountAggregate {

    @AggregateIdentifier
    private String accountId;
    private long balance;

    protected BankAccountAggregate() {}

    @JsonIgnore
    @CommandHandler
    public BankAccountAggregate(OpenAccountCommand cmd) {
        if (cmd.initialBalance() < 0) throw new IllegalArgumentException("Saldo inicial negativo");
        AggregateLifecycle.apply(new AccountOpenedEvent(cmd.accountId(), cmd.initialBalance()));
    }

    @EventSourcingHandler
    public void on(AccountOpenedEvent e) {
        this.accountId = e.accountId();
        this.balance = e.initialBalance();
    }

    @CommandHandler
    public void handle(DepositMoneyCommand cmd) {
        if (cmd.amount() <= 0) throw new IllegalArgumentException("Depósito inválido");
        AggregateLifecycle.apply(new MoneyDepositedEvent(accountId, cmd.amount()));
    }

    @EventSourcingHandler
    public void on(MoneyDepositedEvent e) {
        this.balance += e.amount();
    }

    @CommandHandler
    public void handle(WithdrawMoneyCommand cmd) {
        if (cmd.amount() <= 0) throw new IllegalArgumentException("Saque inválido");
        if (balance - cmd.amount() <= 0) throw new IllegalStateException("Saldo insuficiente");
        AggregateLifecycle.apply(new MoneyWithdrawnEvent(accountId, cmd.amount()));
    }

    @EventSourcingHandler
    public void on(MoneyWithdrawnEvent e) {
        this.balance -= e.amount();
    }
}
