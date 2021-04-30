package cz.muni.pa165.teamwhite.formula1.persistence.validation;

import cz.muni.pa165.teamwhite.formula1.persistence.PersistenceConfig;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Component;
import cz.muni.pa165.teamwhite.formula1.persistence.enums.ComponentType;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jakub Fajkus
 */
@ContextConfiguration(classes = PersistenceConfig.class)
public class UniqueComponentValidatorTest extends AbstractTestNGSpringContextTests {
    @Mock
    private ConstraintValidatorContext context;

    private final UniqueComponentValidator validator = new UniqueComponentValidator();

    @Test
    public void testNullSetIsValid() {
        Assert.assertTrue(validator.isValid(null, context));
    }


    @Test
    public void testEmptySetIsValid() {
        Assert.assertTrue(validator.isValid(new HashSet<>(), context));
    }

    @Test
    public void testSetWithOneComponentIsValid() {
        Set<Component> componentSet = Set.of(new Component(ComponentType.ENGINE, "engine"));

        Assert.assertTrue(validator.isValid(componentSet, context));
    }

    @Test
    public void testSetWithTwoComponentWithDistinctTypesIsValid() {
        Set<Component> componentSet = Set.of(new Component(ComponentType.ENGINE, "engine"), new Component(ComponentType.TRANSMISSION, "trans"));

        Assert.assertTrue(validator.isValid(componentSet, context));
    }

    @Test
    public void testSetWithTwoSameTypesIsInvalid() {
        Set<Component> componentSet = Set.of(
                new Component(ComponentType.ENGINE, "engine"),
                new Component(ComponentType.ENGINE, "turboengine"));

        Assert.assertFalse(validator.isValid(componentSet, context));
    }

    @Test
    public void testSetWithTwoSameTypesAndOneDistinctIsInvalid() {
        Set<Component> componentSet = Set.of(
                new Component(ComponentType.ENGINE, "engine"),
                new Component(ComponentType.ENGINE, "turboengine"),
                new Component(ComponentType.TRANSMISSION, "trans")
        );

        Assert.assertFalse(validator.isValid(componentSet, context));
    }
}