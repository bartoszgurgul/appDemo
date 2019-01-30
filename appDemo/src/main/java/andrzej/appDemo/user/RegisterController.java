/**
 * 
 */
package andrzej.appDemo.user;

import javax.ws.rs.GET;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author BartoszGurgul
 * get - oznacza ze ta klasa odbiera dane   
 * request mapping - jezeli zostanie odpalna strona register - to znak ze ta klasa ma sie odpali c
 * mamy jedna metode registerForm ktora pzyjmuje model z frameWorku spring
 * model pozwala na przekazanie user dokłądnie do modelAttribute="user" na stronie register
 */
@Controller
public class RegisterController {

	@GET
	@RequestMapping(value = "/register")
	public String registerForm(Model model) {
		User u = new User();
		model.addAttribute("user", u);
		return "register";
	}
}
