package net.nemisolv.lib.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import net.nemisolv.lib.core.validation.impl.EnumValueChecker;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Constraint(validatedBy = {EnumValueChecker.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
public @interface EnumValue {

    String message() default "Invalid value. This is not permitted.";

    Class<? extends Enum<?>> enumClass();

    // groups
    Class<?>[] groups() default {};

    // payload
    Class<? extends Payload>[] payload() default {};

    @Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        EnumValue[] value();
    }
}