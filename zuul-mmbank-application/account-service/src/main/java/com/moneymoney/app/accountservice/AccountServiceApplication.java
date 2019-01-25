package com.moneymoney.app.accountservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.moneymoney.app.accountservice.entity.CurrentAccount;
import com.moneymoney.app.accountservice.entity.SavingsAccount;
import com.moneymoney.app.accountservice.repo.AccountRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner populateData(AccountRepository repository) {
		return (arg)->{	
		repository.save(new SavingsAccount(1001, "Ragadeep", 5_600.56, true));
		repository.save(new SavingsAccount(1002, "shabzan", 9_700.74, false));
		repository.save(new SavingsAccount(1003, "nikhil", 3_500.25, true));
		repository.save(new CurrentAccount(1004, "vani", 4_25_000.25, 50_000.00));
		repository.save(new CurrentAccount(1005, "hema", 10_25_000.25, 2_00_000.00));
		};
	}

}

