package com.springboot.getlinked.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.springboot.getlinked.model.User;



public class PasswordMatchValidator implements ConstraintValidator<com.springboot.getlinked.validation.PasswordMatch, User> {

	@Override
	public boolean isValid(User user, ConstraintValidatorContext c) {
		String plainPassword = user.getPlainPassword();
		String repeatPassword = user.getRepeatPassword();

		if (plainPassword == null || plainPassword.length() == 0) {
			return true;
		}

		if (plainPassword == null || !plainPassword.equals(repeatPassword)) {
			return false;
		}

		return true;
	}

}

