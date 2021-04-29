package cz.muni.pa165.teamwhite.formula1.persistence;

import cz.muni.pa165.teamwhite.formula1.persistence.entity.Car;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Driver;
import cz.muni.pa165.teamwhite.formula1.persistence.dao.CarDao;
import cz.muni.pa165.teamwhite.formula1.persistence.dao.DriverDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author Karolina Hecova
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class DriverTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private DriverDao driverDao;

    @Autowired
    private CarDao carDao;

    private Driver driver;
    private Driver driver2;

    @BeforeMethod
    public void setup() {
        driver = new Driver(null, "Lewis", "Hamilton", "GB", true, 10, 10);
        driver2 = new Driver(null, "Valtteri", "Bottas", "FI", true, 9, 9);
    }

    @Test
    public void createDriverWithoutCarTest() {
        driverDao.create(driver);

        Driver dbDriver = driverDao.findById(driver.getId());

        Assert.assertNotNull(dbDriver);
        Assert.assertNotNull(dbDriver.getId());
        Assert.assertSame(dbDriver, driver);
        Assert.assertNull(dbDriver.getCar());
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createDriverWithNullAttributesTest() {
        Driver invalidDriver = new Driver();
        driverDao.create(invalidDriver);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createNullDriverNameTest() {
        driver.setName(null);
        driverDao.create(driver);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createNullDriverSurnameTest() {
        driver.setSurname(null);
        driverDao.create(driver);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createNullDriverNationalityTest() {
        driver.setNationality(null);
        driverDao.create(driver);
    }

    @Test
    public void createDriverWithCarTest() {
        Car car = new Car();
        car.setName("Mercedes");

        carDao.create(car);

        driver.setCar(car);

        driverDao.create(driver);

        car.setDriver(driver);
        carDao.update(car);

        Driver dbDriver = driverDao.findById(driver.getId());

        Assert.assertEquals(dbDriver.getCar().getName(), driver.getCar().getName());
        Assert.assertEquals(dbDriver.getCar().getName(), car.getName());
    }

    @Test
    public void tryToCreateTwoIdenticalDriverTest() {
        driverDao.create(driver);
        Assert.assertEquals(driverDao.findAll().size(), 1);

        Driver copyDriver = new Driver(null, "Lewis", "Hamilton", "GB", true, 10, 10);
        driverDao.create(copyDriver);
        Assert.assertEquals(driverDao.findAll().size(), 2);
    }

    @Test
    public void findAllDriversTest() {
        driverDao.create(driver);

        Assert.assertEquals(driverDao.findAll().size(), 1);

        driverDao.create(driver2);

        List<Driver> allDrivers = driverDao.findAll();

        Assert.assertNotNull(allDrivers);
        Assert.assertEquals(allDrivers.size(), 2);
        Assert.assertTrue(allDrivers.contains(driver));
        Assert.assertTrue(allDrivers.contains(driver2));
    }

    @Test
    public void updateDriverTest() {
        driverDao.create(driver);

        driver.setName("Luisa");
        driverDao.update(driver);

        Driver dbDriver = driverDao.findById(driver.getId());
        Assert.assertEquals(dbDriver.getName(), "Luisa");
    }

    @Test
    public void updateDriverWhichDoesNotExistInDatabaseTest() {
        Assert.assertEquals(driverDao.findAll().size(), 0);
        driverDao.update(driver);
        Assert.assertEquals(driverDao.findAll().size(), 1);
    }

    @Test
    public void updateDriverCarTest() {
        Car car1 = new Car();
        car1.setName("Mercedes");

        carDao.create(car1);

        driver.setCar(car1);
        driverDao.create(driver);

        car1.setDriver(driver);
        carDao.update(car1);

        Driver dbDriver = driverDao.findById(driver.getId());
        Assert.assertEquals(dbDriver.getCar().getName(), car1.getName());

        Car car2 = new Car();
        car2.setName("Ferrari");
        car2.setDriver(driver);
        carDao.create(car2);

        driver.setCar(car2);
        driverDao.update(driver);

        car1.setDriver(null);
        carDao.update(car1);

        dbDriver = driverDao.findById(driver.getId());

        Assert.assertNull(carDao.findById(car1.getId()).getDriver());
        Assert.assertEquals(dbDriver.getCar().getName(), car2.getName());
    }

    @Test
    public void removeDriverTest() {
        driverDao.create(driver);
        driverDao.create(driver2);

        List<Driver> allDrivers = driverDao.findAll();

        Assert.assertEquals(allDrivers.size(), 2);
        Assert.assertTrue(allDrivers.contains(driver));
        Assert.assertTrue(allDrivers.contains(driver2));

        driverDao.remove(driver);

        allDrivers = driverDao.findAll();

        Assert.assertEquals(allDrivers.size(), 1);
        Assert.assertFalse(allDrivers.contains(driver));
        Assert.assertTrue(allDrivers.contains(driver2));
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void removeDriverWhichDoesNotExistInDatabaseTest() {
        driverDao.remove(driver);
    }
}