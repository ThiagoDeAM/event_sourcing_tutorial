package br.ifsp.arsw.esdemo.domain.account.events;

public record MoneyWithdrawnEvent(String accountId, long amount) {}
