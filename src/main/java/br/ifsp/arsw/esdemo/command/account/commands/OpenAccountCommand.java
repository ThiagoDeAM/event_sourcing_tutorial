package br.ifsp.arsw.esdemo.command.account.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record OpenAccountCommand(
        @TargetAggregateIdentifier String accountId,
        long initialBalance
) {}
