package com.casestudy.skilltracker.employee.validator;

import com.casestudy.skilltracker.employee.enums.Skills;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SkillValidator implements ConstraintValidator<ValidSkill, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        Skills[] skills = Skills.values();
        for (Skills skill : skills) {
            if (skill.getName().equals(value))
                return true;
        }
        return false;
    }
}
