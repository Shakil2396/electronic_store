package com.sps.electronic.store.validate;

import com.sps.electronic.store.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return true; // You may also add additional logic here
        }

        // Check if email exists in the database
        return !userRepository.existsByEmail(email); // Assuming existsByEmail is a method in your UserRepository
    }
}
