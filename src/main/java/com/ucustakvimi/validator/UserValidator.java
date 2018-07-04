package com.ucustakvimi.validator;

import com.ucustakvimi.model.FlightUser;
import com.ucustakvimi.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
	@Autowired
	private UserService userService;

	public boolean supports(Class<?> aClass) {
		return FlightUser.class.equals(aClass);
	}

	public void validate(Object o, Errors errors) {
		FlightUser user = (FlightUser) o;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email",
				"NotEmpty");
		if (user.getEmail().length() < 6 || user.getEmail().length() > 32) {
			errors.rejectValue("email", "Size.userForm.email");
		}
		if (userService.findByEmail(user.getEmail()) != null) {
			errors.rejectValue("email", "Duplicate.userForm.email");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"NotEmpty");
		if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
			errors.rejectValue("password", "Size.userForm.password");
		}

		if (!user.getPasswordConfirm().equals(user.getPassword())) {
			errors.rejectValue("passwordConfirm",
					"Diff.userForm.passwordConfirm");
		}
	}
}
