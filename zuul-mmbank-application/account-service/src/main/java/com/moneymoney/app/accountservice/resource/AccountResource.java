package com.moneymoney.app.accountservice.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moneymoney.app.accountservice.entity.Account;
import com.moneymoney.app.accountservice.entity.SavingsAccount;
import com.moneymoney.app.accountservice.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountResource {
	
	@Autowired
	private AccountService service;
	
/*	@PostMapping
	public void addNewAccount(@RequestBody Account account) {
		service.addNewAccount(account);
	}*/
	
	@GetMapping
	public ResponseEntity<List<Account>> getAllAccounts() {
		List<Account> account = service.getAllAccounts();
 		return new ResponseEntity<>(account, HttpStatus.OK);
	}
	
	@GetMapping("/{accountNumber}")
	public ResponseEntity<Account> getAccount(@PathVariable int accountNumber) {
		Optional<Account> optionalAccount = service.getAccountById(accountNumber);
		Account account = optionalAccount.get(); 
		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}
	
	@DeleteMapping("/{accountNumber}")
	public void deleteAccount(@PathVariable int accountNumber) {
		service.deleteAccount(accountNumber);
	}
	
	@PutMapping
	public void updateAccount(@RequestBody SavingsAccount account) {
		service.updateAccount(account);
	}
	
	@GetMapping("/{accountNumber}/balance")
	public ResponseEntity<Double> checkBalance(@PathVariable int accountNumber) {
		Optional<Account> account = service.getAccountById(accountNumber);
		Double checkBalance = account.get().getCurrentBalance();
		if(account.get() == null) {
			return new ResponseEntity<>(checkBalance, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(checkBalance, HttpStatus.OK);
	} 
	
	@PutMapping("/{accountNumber}")
	public ResponseEntity<Account> updateBalance(@RequestParam("currentBalance") double currentBalance, @PathVariable int accountNumber) {
		Optional<Account> optAccount = service.getAccountById(accountNumber);
		Account account = optAccount.get();
		if(account == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		account.setCurrentBalance(currentBalance); 
		service.updateBalance(account);
		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}
}
