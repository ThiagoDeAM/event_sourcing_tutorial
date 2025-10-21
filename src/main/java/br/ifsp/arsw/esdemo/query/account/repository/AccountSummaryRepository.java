package br.ifsp.arsw.esdemo.query.account.repository;

import br.ifsp.arsw.esdemo.query.account.model.AccountSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountSummaryRepository extends JpaRepository<AccountSummary, String> {
}
