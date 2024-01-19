package com.in28minutes.springboot.myfirstwebapp.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller     // Anotation which is used to deal with the UI design 
public class SayHelloController {

	@RequestMapping("/hello")
	@ResponseBody
	public String sayHello() {
		return "Hello, what are u doing today";
	}
	
	
	@RequestMapping("/sayhellojsp")
	public String sayHelloJsp() {
		return "sayHello";
	}
}
