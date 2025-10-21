package br.ifsp.arsw.esdemo.command.account.events;

public record MoneyWithdrawnEvent(String accountId, long amount) {}
