package com.moneymoney.app.transaction.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.moneymoney.app.transaction.entity.CurrentDataSet;
import com.moneymoney.app.transaction.entity.Transaction;
import com.moneymoney.app.transaction.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionResource {
	
	@Autowired
	private RestTemplate template;
	
	@Autowired
	private TransactionService service;
	
	@PostMapping
	public ResponseEntity<Transaction> deposit(@RequestBody Transaction transaction) {
		ResponseEntity<Double> entity = template.getForEntity("http://accountservice/accounts/"+transaction.getAccountNumber()+"/balance", Double.class); 
		double currntBalance = entity.getBody();
		double updatedBalance = service.deposit(transaction.getAccountNumber(), transaction.getTransactionDetails(), currntBalance, transaction.getAmount());
			template.put("http://accountservice/accounts/"+transaction.getAccountNumber()+"?currentBalance="+updatedBalance, null); 
			return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PostMapping("/withdraw")
	public ResponseEntity<Transaction> withdraw(@RequestBody Transaction transaction) {
		ResponseEntity<Double> entity = template.getForEntity("http://accountservice/accounts/"+transaction.getAccountNumber()+"/balance", Double.class);
		double currentBalance = entity.getBody();
		double updatedBalance = service.withdraw(transaction.getAccountNumber(), transaction.getTransactionDetails(), currentBalance, transaction.getAmount());
			template.put("http://accountservice/accounts/"+transaction.getAccountNumber()+"?currentBalance="+updatedBalance, null); 
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
 
	@GetMapping("/statement")
	public ResponseEntity<CurrentDataSet> getStatement(){
		CurrentDataSet currentDataSet = new CurrentDataSet();
		List<Transaction> transactions = service.getStatement();
		currentDataSet.setTransactions(transactions);
		return new ResponseEntity<CurrentDataSet>(currentDataSet,HttpStatus.OK);
	}
}
 