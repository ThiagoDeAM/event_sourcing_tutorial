package br.ifsp.arsw.esdemo.query.account.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.Instant;

@Entity
public class AccountSummary {
    @Id
    private String accountId;
    private long balance;
    private Instant lastUpdate;

    public AccountSummary() {}

    public AccountSummary(String accountId, long balance, Instant lastUpdate) {
        this.accountId = accountId;
        this.balance = balance;
        this.lastUpdate = lastUpdate;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
