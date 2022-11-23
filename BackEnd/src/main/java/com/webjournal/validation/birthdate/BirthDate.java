package com.webjournal.validation.birthdate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class BirthDate
 * @since 11/1/2022 - 18.24
 **/
@Documented
@Constraint(validatedBy = BirthDateValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BirthDate {
    String message() default "your age shouldn't be less than 18";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
