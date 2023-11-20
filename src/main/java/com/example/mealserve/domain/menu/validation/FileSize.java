package com.example.mealserve.domain.menu.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FileSizeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FileSize {
    String message() default "파일의 크기가 너무 큽니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long max();
}
