package cz.muni.pa165.teamwhite.formula1;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;


/**
 * @author Jakub Fajkus
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class TestsTest extends AbstractTestNGSpringContextTests {
    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    public void testCreateDB() {

    }
}
