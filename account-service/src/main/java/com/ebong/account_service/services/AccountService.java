package com.ebong.account_service.services;

import com.ebong.account_service.models.Account;
import com.ebong.account_service.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Create account
    public Account createAccount(Account account) {
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        return accountRepository.save(account);
    }

    // Get all accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Get account by ID
    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    // Update account
    public Account updateAccount(Long id, Account updatedAccount) {
        Account existingAccount = getAccountById(id);

        existingAccount.setAccountName(updatedAccount.getAccountName());
        existingAccount.setAccountType(updatedAccount.getAccountType());
        existingAccount.setBalance(updatedAccount.getBalance());
        existingAccount.setStatus(updatedAccount.getStatus());
        existingAccount.setUpdatedAt(LocalDateTime.now());

        return accountRepository.save(existingAccount);
    }

    // Delete account
    public void deleteAccount(Long id) {
        Account account = getAccountById(id);
        accountRepository.delete(account);
    }
}