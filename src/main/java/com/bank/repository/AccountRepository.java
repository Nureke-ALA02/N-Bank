package com.bank.repository;
import com.bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByOwnerId(Long userId);

    Account findByOwner_Id(Long id);
}
