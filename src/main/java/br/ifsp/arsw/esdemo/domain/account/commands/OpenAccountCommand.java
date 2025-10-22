package br.ifsp.arsw.esdemo.domain.account.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record OpenAccountCommand(
        @TargetAggregateIdentifier String accountId,
        long initialBalance
) {}
