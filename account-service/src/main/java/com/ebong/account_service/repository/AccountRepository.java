package com.ebong.account_service.repository;

import com.ebong.account_service.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository
        extends JpaRepository<Account, Long> {

}
