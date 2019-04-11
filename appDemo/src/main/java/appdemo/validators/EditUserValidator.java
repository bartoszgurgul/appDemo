package appdemo.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import appdemo.entity.User;
import appdemo.constants.AppDemoConstants;
import appdemo.utilities.AppdemoUtils;

public class EditUserValidator implements Validator {

	@Override
	public boolean supports(Class<?> cls) {
		return User.class.equals(cls);
	}

	@Override
	public void validate(Object obj, Errors errors) {

		User user = (User) obj;
		
		ValidationUtils.rejectIfEmpty(errors, "name", "error.userName.empty");
		ValidationUtils.rejectIfEmpty(errors, "lastName", "error.userLastName.empty");
		ValidationUtils.rejectIfEmpty(errors, "email", "error.userEmail.empty");
		
		if(user.getEmail() != null) {
			boolean isMatch = AppdemoUtils.checkEmailOrPassword(AppDemoConstants.EMAIL_PATTERN, user.getEmail());
			if(!isMatch) {
				errors.rejectValue("email", "error.userEmailIsNotMatch");
			}
		}
	}

}
