package br.ifsp.arsw.esdemo.domain.account.aggregate;

import br.ifsp.arsw.esdemo.domain.account.commands.DepositMoneyCommand;
import br.ifsp.arsw.esdemo.domain.account.commands.OpenAccountCommand;
import br.ifsp.arsw.esdemo.domain.account.commands.WithdrawMoneyCommand;
import br.ifsp.arsw.esdemo.domain.account.events.AccountOpenedEvent;
import br.ifsp.arsw.esdemo.domain.account.events.MoneyDepositedEvent;
import br.ifsp.arsw.esdemo.domain.account.events.MoneyWithdrawnEvent;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate(snapshotTriggerDefinition = "snapshotTrigger")
public class BankAccountAggregate {

    @AggregateIdentifier
    @JsonProperty("accountId")
    private String accountId;

    @JsonProperty("balance")
    private long balance;

    protected BankAccountAggregate() {}

    @JsonCreator
    public BankAccountAggregate(
            @JsonProperty("accountId") String accountId,
            @JsonProperty("balance") long balance
    ) {
        this.accountId = accountId;
        this.balance = balance;
    }

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
        if (balance - cmd.amount() < 0) throw new IllegalStateException("Saldo insuficiente");
        AggregateLifecycle.apply(new MoneyWithdrawnEvent(accountId, cmd.amount()));
    }

    @EventSourcingHandler
    public void on(MoneyWithdrawnEvent e) {
        this.balance -= e.amount();
    }

    public String getAccountId() {
        return accountId;
    }

    public long getBalance() {
        return balance;
    }
}
