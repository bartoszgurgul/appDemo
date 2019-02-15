package andrzej.appDemo.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import andrzej.appDemo.Entity.User;
import andrzej.appDemo.constants.AppDemoConstants;
import andrzej.appDemo.utilities.AppdemoUtils;

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
