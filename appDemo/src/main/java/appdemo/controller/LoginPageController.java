package appdemo.controller;

import javax.ws.rs.GET;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginPageController {

	
	@GET
	@RequestMapping(value = "/login")
	public String showLoginPage() {
		
		return "login";
	}

}