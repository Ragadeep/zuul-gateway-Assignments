package com.moneymoney.web.controller;

import java.util.ArrayList;
import java.util.List;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.moneymoney.web.entity.CurrentDataSet;
import com.moneymoney.web.entity.Transaction;

@RefreshScope
@Controller
public class BankAppController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	@RequestMapping("/depositForm")
	public String depositForm() {
		return "DepositForm";
	}
	@RequestMapping("/deposit")
	public String deposit(@ModelAttribute Transaction transaction,
			Model model) {
		restTemplate.postForEntity("http://zuulgatway/transactionservice/transactions", 
				transaction, null);
		model.addAttribute("message","Success!");
		return "DepositForm";
	}
	
	@RequestMapping("/withdrawForm")
	public String withdrawForm() {
		return "withdrawForm";
	}
	
	@RequestMapping("/withdraw")
	public String withdraw(@ModelAttribute Transaction transaction,
			Model model) {
		restTemplate.postForEntity("http://zuulgatway/transactionservice/transactions/withdraw", 
				transaction, null);
		model.addAttribute("message","Withdraw Success!");
		return "withdrawForm";
	}
	
	@RequestMapping("/fundTransferForm")
	public String fundTransferForm() {
		return "fundTransferForm";
	}
	
	@RequestMapping("/fundTransfer")
	public String fundTransfer(@RequestParam ("senderAccountNumber") int senderAccountNumber,
			@RequestParam ("receiverAccountNumber") int receiverAccountNumber,
			@ModelAttribute Transaction transaction,
			Model model) {
		transaction.setAccountNumber(senderAccountNumber);
		restTemplate.postForEntity("http://zuulgatway/transactionservice/transactions/withdraw", 
				transaction, null);
		transaction.setAccountNumber(receiverAccountNumber);
		restTemplate.postForEntity("http://zuulgatway/transactionservice/transactions", 
				transaction, null);
		model.addAttribute("message","Transfer Success!");
		return "fundTransferForm";
	}
	
	@RequestMapping("/statement")
	public ModelAndView getStatement(@RequestParam("offset") int offset, @RequestParam("size") int size) {
		CurrentDataSet currentDataSet = restTemplate.getForObject("http://zuulgatway/transactionservice/transactions/statement", CurrentDataSet.class);
		int currentSize=size==0?5:size;
		int currentOffset=offset==0?1:offset;
		Link next=linkTo(methodOn(BankAppController.class).getStatement(currentOffset+currentSize,currentSize)).withRel("next");
		Link previous=linkTo(methodOn(BankAppController.class).getStatement(currentOffset-currentSize, currentSize)).withRel("previous");
		List<Transaction> transactions = currentDataSet.getTransactions();
		List<Transaction> currentDataSetList = new ArrayList<Transaction>();
		
		for (int i = currentOffset - 1; i < currentSize + currentOffset - 1; i++) { 
			  if((transactions.size()<=i && i>0) || currentOffset<1) 
				  break;
			Transaction transaction = transactions.get(i);
			currentDataSetList.add(transaction);
			
		}
		CurrentDataSet dataSet = new CurrentDataSet(currentDataSetList, next, previous);
		/*
		 * currentDataSet.setNextLink(next); currentDataSet.setPreviousLink(previous);
		 */
		return new ModelAndView("index","currentDataSet",dataSet);
	}
}
