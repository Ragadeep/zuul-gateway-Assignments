package com.moneymoney.app.transaction.service;

import java.util.List;

import com.moneymoney.app.transaction.entity.Transaction;

public interface TransactionService {
	
	Double deposit(Integer accountNumber, String transactionDetails, double currentBalance, Double amount);

	Double withdraw(Integer accountNumber, String transactionDetails, Double currentBalance, Double amount);

	List<Transaction> getStatement(); 
	 
	
} 
