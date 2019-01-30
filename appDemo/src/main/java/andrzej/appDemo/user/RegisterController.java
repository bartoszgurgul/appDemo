/**
 * 
 */
package andrzej.appDemo.user;

import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import andrzej.appDemo.validators.UserRegisterValidator;

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
	
	@GET
	@RequestMapping(value = "/register")
	public String registerForm(Model model) {
		User u = new User();
		model.addAttribute("user", u);
		return "register";
	}
	
	/**
	 * POST oznacza wsad do bazy a
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
		
		new UserRegisterValidator().validate(user, result);
		
		if(userExist != null) {
			result.rejectValue("email", messageSource.getMessage("user.register.success",null, locale));
		} else {
			userService.saveUser(user);
			model.addAttribute("message", messageSource.getMessage("user.register.success", null, locale));
			model.addAttribute("user", new User());
			returnPage = "register";
		}
		return returnPage;
	}
}
