package br.ifsp.arsw.esdemo.command.account.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record WithdrawMoneyCommand(
        @TargetAggregateIdentifier String accountId,
        long amount
) {}
