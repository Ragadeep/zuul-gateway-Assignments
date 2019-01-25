package com.cg.helloworld.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class ClientController {
	
	
	
	@GetMapping
	public String home() {
		return "hello world and Hello capgemini";
	}
}
