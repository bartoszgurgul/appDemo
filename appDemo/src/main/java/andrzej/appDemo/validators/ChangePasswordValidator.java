package andrzej.appDemo.validators;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import andrzej.appDemo.Entity.User;
import andrzej.appDemo.constants.AppDemoConstants;
import andrzej.appDemo.utilities.AppdemoUtils;



public class ChangePasswordValidator implements Validator {

	@Override
	public boolean supports(Class<?> cls) {
		return User.class.equals(cls);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		@SuppressWarnings("unused")
		User u = (User) obj;
		
		ValidationUtils.rejectIfEmpty(errors, "newPassword", "error.userPassword.empty");
		
	}
	
	public void checkPasswords(String newPass, Errors errors) {
		
		if (!newPass.equals(null)) {
			boolean isMatch = AppdemoUtils.checkEmailOrPassword(AppDemoConstants.PASSWORD_PATTERN, newPass);
			if(!isMatch) {
				errors.rejectValue("newPassword", "error.userPasswordIsNotMatch");
			}
		}
	}
}
