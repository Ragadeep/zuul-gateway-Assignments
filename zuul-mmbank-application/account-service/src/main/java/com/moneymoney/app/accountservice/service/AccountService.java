package com.moneymoney.app.accountservice.service;

import java.util.List;
import java.util.Optional;


import com.moneymoney.app.accountservice.entity.Account;
import com.moneymoney.app.accountservice.entity.SavingsAccount;

public interface AccountService {

	void addNewAccount(Account account);

	List<Account> getAllAccounts();

	Optional<Account> getAccountById(int accountNumber);

	void deleteAccount(int accountNumber);

	void updateAccount(SavingsAccount account);

	void updateBalance(Account account);   
}
