package com.kamadhenu.warehousemanagementsystem.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static java.util.regex.Pattern.compile;

public class BlankOrPatternValidator implements ConstraintValidator<BlankOrPatternConstraint, String> {

    private Pattern pattern;

    public void initialize(BlankOrPatternConstraint parameters) {
        javax.validation.constraints.Pattern.Flag[] flags = parameters.flags();
        int intFlag = 0;
        for (javax.validation.constraints.Pattern.Flag flag : flags) {
            intFlag = intFlag | flag.getValue();
        }

        try {
            pattern = compile(parameters.regexp(), intFlag);
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Invalid regular expression.", e);
        }
    }

    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.length() == 0) {
            return true;
        }
        Matcher m = pattern.matcher(value);
        return m.matches();
    }
}