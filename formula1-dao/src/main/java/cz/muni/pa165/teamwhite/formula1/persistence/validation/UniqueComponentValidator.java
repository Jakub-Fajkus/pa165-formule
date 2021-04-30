package cz.muni.pa165.teamwhite.formula1.persistence.validation;

import cz.muni.pa165.teamwhite.formula1.persistence.entity.Component;
import cz.muni.pa165.teamwhite.formula1.persistence.enums.ComponentType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UniqueComponentValidator implements ConstraintValidator<UniqueComponentByType, Set<Component>> {
    @Override
    public void initialize(UniqueComponentByType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Set<Component> componentList, ConstraintValidatorContext constraintValidatorContext) {
        if (componentList == null) {
            return true;
        }

        try {
            Map<ComponentType, Component> map = componentList.stream()
                    .collect(Collectors.toMap(Component::getType, component -> component));

            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }
}