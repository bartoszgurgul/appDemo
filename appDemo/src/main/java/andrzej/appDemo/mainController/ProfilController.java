package andrzej.appDemo.mainController;


import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import andrzej.appDemo.Entity.User;
import andrzej.appDemo.Repository.UserService;
import andrzej.appDemo.utilities.UserUtilities;
import andrzej.appDemo.validators.ChangePasswordValidator;
import andrzej.appDemo.validators.EditUserValidator;


@Controller
public class ProfilController {
	
	private static final  String EDIT_PAS = "editpassword";
	private static final  String EDIT_PROFIL = "editprofil";
	private static final  String AFTER_EDIT = "afteredit";
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GET
	@RequestMapping(value = "/profil")
	public String showUserProfilePage(Model model) {	
		String username = UserUtilities.getLoggerUser();
		User user = userService.findUserByEmail(username);
		int nrRoli = user.getRoles().iterator().next().getId();
		user.setNrRoli(nrRoli);
		model.addAttribute("user", user);
		return "profil";
	}
	
	@GET
	@RequestMapping(value = "/editpassword")
	public String editUserPassword(Model model) {
		String username = UserUtilities.getLoggerUser();
		User user = userService.findUserByEmail(username);
		model.addAttribute("user", user);
		return EDIT_PAS;
	}
	@GET
	@RequestMapping(value = "/editprofil")
	public String changeUserProfil(Model model) {
		String username = UserUtilities.getLoggerUser();
		User user = userService.findUserByEmail(username);
		model.addAttribute("user", user);
		
		return EDIT_PROFIL;
	}
	
	@POST
	@RequestMapping(value = "/updatepass")
	public String changeUSerPassword(User user, BindingResult result, Model model, Locale locale) {
		String returnPage = null;
		new ChangePasswordValidator().validate(user, result);
		new ChangePasswordValidator().checkPasswords(user.getNewPassword(), result);
		if (result.hasErrors()) {
			returnPage = EDIT_PAS;
		} else {
			userService.updateUserPassword(user.getNewPassword(), user.getEmail());
			returnPage = EDIT_PAS;
			model.addAttribute("message", messageSource.getMessage("passwordChange.success", null, locale));
		}
		return returnPage;
	}
	
	
	@POST
	@RequestMapping( value = "/updateprofil")
	public String changeUserProfile(User user, BindingResult result, Model model, Locale locale) {
		String returnPage = null;
		
		
		new EditUserValidator().validate(user, result);
		
		if( result.hasErrors()) {
			returnPage = EDIT_PROFIL;
		} else {
			userService.updateUserProfile(user.getName(), user.getLastName(), user.getEmail(), user.getId());
			model.addAttribute("message", messageSource.getMessage("profilEdit.success",  null, locale));
			returnPage = AFTER_EDIT;
		}
		
		
		return returnPage;
	}
	

}