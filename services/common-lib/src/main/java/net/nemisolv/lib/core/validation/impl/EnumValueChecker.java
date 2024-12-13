package net.nemisolv.lib.core.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.nemisolv.lib.core.validation.EnumValue;

public class EnumValueChecker implements ConstraintValidator<EnumValue, Enum<?>> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        for (Enum<?> enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.equals(value)) {
                return true;
            }
        }
        return false;
    }
}