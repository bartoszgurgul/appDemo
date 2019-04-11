/**
 * 
 */
package appdemo.controller;

import javax.ws.rs.GET;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageController implements ErrorController {

	private static final Logger LOG = LoggerFactory.getLogger(ErrorPageController.class);
	
	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	@GET
	@RequestMapping(value = "/error")
	public String showErrorPage() {
		LOG.info("Błąd");
		return "error";
	}


}