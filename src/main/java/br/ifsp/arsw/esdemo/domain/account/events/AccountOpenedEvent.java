package br.ifsp.arsw.esdemo.domain.account.events;

public record AccountOpenedEvent(String accountId, long initialBalance) {}
