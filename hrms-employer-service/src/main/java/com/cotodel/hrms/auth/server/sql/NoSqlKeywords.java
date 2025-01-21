package com.cotodel.hrms.auth.server.sql;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoSqlKeywordsValidator.class)
public @interface NoSqlKeywords {
	 String message() default "Input contains forbidden SQL keywords.";
	    Class<?>[] groups() default {};
	    Class<? extends Payload>[] payload() default {};
}
