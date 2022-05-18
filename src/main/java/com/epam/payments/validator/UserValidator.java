package com.epam.payments.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.epam.payments.model.User;
import com.epam.payments.service.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements Validator{
	private final UserService userService;

	@Autowired
	public UserValidator(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		 return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (!validateText(user.getEmail(), "@")) {
        	errors.rejectValue("username", "Valid.userForm.email");
        }
        if (userService.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.email");
        }
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (!validateText(user.getPassword(), "^[a-zA-Z0-9]{8,20}$")) {
            errors.rejectValue("password", "Size.userForm.password");
        }
		
	}

	
	private boolean validateText(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }
}
