package com.moneymoney.app.transaction.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneymoney.app.transaction.entity.Transaction;
import com.moneymoney.app.transaction.entity.TransactionType;
import com.moneymoney.app.transaction.repo.TransactionRepository;

@Service
public class TransatctionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository repository;

	@Override
	public Double deposit(Integer accountNumber, String transactionDetails, double currentBalance, Double amount) {
		Transaction transaction = new Transaction();
		transaction.setAccountNumber(accountNumber);
		transaction.setTransactionDetails(transactionDetails);
		currentBalance += amount;
		transaction.setCurrentBalance(currentBalance);
		transaction.setAmount(amount);
		transaction.setTransactionDate(LocalDateTime.now());
		transaction.setTransactionType(TransactionType.DEPOSIT);
		repository.save(transaction);
		return currentBalance;
	}

	@Override
	public Double withdraw(Integer accountNumber, String transactionDetails, Double currentBalance, Double amount)  {
		Transaction transaction = new Transaction();
		transaction.setAccountNumber(accountNumber);
		transaction.setTransactionDetails(transactionDetails);
		currentBalance -= amount;
		transaction.setCurrentBalance(currentBalance);
		transaction.setAmount(amount);
		transaction.setTransactionDate(LocalDateTime.now());
		transaction.setTransactionType(TransactionType.WITHDRAW);
		repository.save(transaction);
		return currentBalance;
	}

	@Override
	public List<Transaction> getStatement() {
		return repository.findAll();
	}
}
