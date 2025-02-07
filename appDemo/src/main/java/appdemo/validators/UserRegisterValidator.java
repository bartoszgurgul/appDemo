package appdemo.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import appdemo.entity.User;
import appdemo.constants.AppDemoConstants;
import appdemo.utilities.AppdemoUtils;

/**
 * @author BartoszGurgul
 * walidacje skierowane do user - poniewaz tam nie stosowalismy żadnych walidacji z adnotacji 
 * 
 * support ktory jako argument przyjnye klase 
 * 
 * validator - to jest konkretny walidatore 
 * otrzymujemy obiekt ktory 
 */
public class UserRegisterValidator implements org.springframework.validation.Validator {

	@Override
	public boolean supports(Class<?> cls) {
		return User.class.equals(cls);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		User u = (User) obj;
		
		
		ValidationUtils.rejectIfEmpty(errors, "name", "error.userName.empty");
		ValidationUtils.rejectIfEmpty(errors, "lastName", "error.userLastName.empty");
		ValidationUtils.rejectIfEmpty(errors, "email", "error.userEmail.empty");
		ValidationUtils.rejectIfEmpty(errors, "password", "error.userPassword.empty");
		
		if(u.getEmail() != null) {
			boolean isMatch = AppdemoUtils.checkEmailOrPassword(AppDemoConstants.EMAIL_PATTERN, u.getEmail());
			if(!isMatch) {
				errors.rejectValue("email", "error.userEmailIsNotMatch");
			}
		}
		
		if(u.getPassword() != null) {
			boolean isMatch = AppdemoUtils.checkEmailOrPassword(AppDemoConstants.PASSWORD_PATTERN, u.getPassword());
			if(!isMatch) {
				errors.rejectValue("password", "error.userPasswordIsNotMatch");
			}
		}
		
	}

	public void validateEmailExist(User user, Errors errors) {
		if (user != null) {
			errors.rejectValue("email", "error.userEmailExist");
		}
	}



}
