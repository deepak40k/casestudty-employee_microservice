package com.casestudy.skilltracker.employee.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(
        {ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE}
)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AssociateIDValidator.class)
@Documented
public @interface AssociateID {
    String message() default "Associate Id must start with CTS";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
