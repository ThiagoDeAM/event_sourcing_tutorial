package br.ifsp.arsw.esdemo.command.account.events;

public record AccountOpenedEvent(String accountId, long initialBalance) {}
