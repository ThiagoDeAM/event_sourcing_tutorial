package br.ifsp.arsw.esdemo.domain.account.events;

public record MoneyDepositedEvent(String accountId, long amount) {}
