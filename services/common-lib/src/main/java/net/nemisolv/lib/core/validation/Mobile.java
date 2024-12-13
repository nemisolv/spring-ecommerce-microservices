package net.nemisolv.lib.core.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import net.nemisolv.techshop.core.validation.impl.MobileValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {MobileValidator.class})
public @interface Mobile {

    String regexp() default "";

    String message() default "Phone number is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}