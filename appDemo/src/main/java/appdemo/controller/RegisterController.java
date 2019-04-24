/**
 * 
 */
package appdemo.controller;

import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import appdemo.entity.User;
import appdemo.service.UserService;
import appdemo.utilities.AppdemoUtils;
import appdemo.validators.UserRegisterValidator;
import emailsender.EmailSender;

/**
 * @author BartoszGurgul
 * get - oznacza ze ta klasa odbiera dane   
 * request mapping - jezeli zostanie odpalna strona register - to znak ze ta klasa ma sie odpali c
 * mamy jedna metode registerForm ktora pzyjmuje model z frameWorku spring
 * model pozwala na przekazanie user dokłądnie do modelAttribute="user" na stronie register
 */
@Controller
public class RegisterController {

	@Autowired
	private UserService userService;
	
	/**
	 * ta zmienna pozwoli na pobieranie komunikatow z messeges.properties
	 * pozwoli na wyswietlanie kodu w Javie
	 */
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private EmailSender emailSender;
	
	@GET
	@RequestMapping(value = "/register")
	public String registerForm(Model model) {
		User u = new User();
		model.addAttribute("user", u);
		return "register";
	}
	
	/**
	 * POST - z biblioteki javax.ws.rs. tej samej co GET :) 
	 * bedzie służyć do 
	 * 
	 * @param user
	 * @param result
	 * @param model
	 * @param locale
	 * @return
	 */
	@POST
	@RequestMapping(value = "/adduser")
	public String registerAction(User user, BindingResult result, Model model, Locale locale) {
		
		String returnPage = null;
		
		User userExist = userService.findUserByEmail(user.getEmail()); 
		
		new UserRegisterValidator().validateEmailExist(userExist, result);
		
		new UserRegisterValidator().validate(user, result);
		
		
		if(result.hasErrors()){
			returnPage = "register";
		}
		else {
			user.setActivationCode(AppdemoUtils.randomStringGenerator());
			
			String content = "Wymagane potwierdzenie rejestracji. Kliknij w poniższy link aby aktywować"
					+ "konto \n http://localhost:8080/activatelink/" + user.getActivationCode();
			
					
			userService.saveUser(user);
			emailSender.sendEmail(user.getEmail(), "Potwierdzenie rejestracji", content);
			model.addAttribute("message", messageSource.getMessage("user.register.success", null, locale));
			//model.addAttribute("user", new User());
			returnPage = "register";
		}
		return returnPage;
	}
	
	@POST
	@RequestMapping(value = "/activatelink/{activationCode}")
	public String activateAccount(@PathVariable("activationCode") String activationCode, Model model, Locale locale) {
		// uruchomienie sql 
		userService.updateActivation(1, activationCode);
		
		// wpuszczenie informacji na strone 
		model.addAttribute("message", messageSource.getMessage("user.register.success", null, locale));
		
		return "index";
	}
	
	
	
	
	
	
}
