package br.hfs.util.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy={CNPJConstraintValidator.class})
@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CNPJ
{
  Class<?>[] groups() default {};
  
  String message() default "{validator.CNPJValidator}";
  
  Class<? extends Payload>[] payload() default {};
}
