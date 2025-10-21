package br.ifsp.arsw.esdemo.command.account.events;

public record MoneyDepositedEvent(String accountId, long amount) {}
