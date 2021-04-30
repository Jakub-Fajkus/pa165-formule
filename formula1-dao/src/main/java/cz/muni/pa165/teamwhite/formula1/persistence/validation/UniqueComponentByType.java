package cz.muni.pa165.teamwhite.formula1.persistence.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueComponentValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueComponentByType {
    String message() default "Car cannot contain multiple components of the same type.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String [] members() default {};
}
