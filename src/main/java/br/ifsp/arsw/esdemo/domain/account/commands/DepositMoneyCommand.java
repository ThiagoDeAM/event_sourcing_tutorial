package br.ifsp.arsw.esdemo.domain.account.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record DepositMoneyCommand(
        @TargetAggregateIdentifier String accountId,
        long amount
) {}
