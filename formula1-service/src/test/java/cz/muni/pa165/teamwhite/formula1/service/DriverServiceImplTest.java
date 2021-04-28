package cz.muni.pa165.teamwhite.formula1.service;

import cz.muni.pa165.teamwhite.formula1.persistence.dao.DriverDao;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Car;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Driver;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;

/**
 * @author Jiri Andrlik
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class DriverServiceImplTest extends AbstractTransactionalTestNGSpringContextTests {
    @Mock
    private DriverDao driverDao;

    @InjectMocks
    private final DriverServiceImpl driverService = new DriverServiceImpl();

    private final Driver carlessDriver = new Driver(null, "Michal", "Koupil",
            "Czech", true, 2, 8);
    private final Driver carfullDriver = new Driver(null, "Jan", "Straka",
            "Czech", false, 1, 1);
    private final Car formula = new Car("formula", null, new HashSet<>());

    @BeforeMethod
    public void setup() throws ServiceException {
        carfullDriver.setCar(formula);
        carfullDriver.setId(97L);
        carlessDriver.setId(69L);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateDriver(){
        driverService.createDriver(carlessDriver);
        Driver driver = driverService.findById(carlessDriver.getId());
        List<Driver> drivers = driverService.findAll();
        Assert.assertEquals(carlessDriver, driver);

    }
}
