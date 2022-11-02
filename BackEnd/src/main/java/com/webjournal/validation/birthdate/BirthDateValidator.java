package com.webjournal.validation.birthdate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class BirthDateValidator
 * @since 11/1/2022 - 18.25
 **/
public class BirthDateValidator implements ConstraintValidator<BirthDate, LocalDate> {
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) return true;
        return value.isBefore(LocalDate.now().minusYears(18)) && value.isAfter(LocalDate.now().minusYears(100));
    }
}
