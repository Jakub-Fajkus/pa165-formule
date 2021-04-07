package cz.muni.pa165.teamwhite.formula1.dao;

import cz.muni.pa165.teamwhite.formula1.PersistenceSampleApplicationContext;
import cz.muni.pa165.teamwhite.formula1.entity.Car;
import cz.muni.pa165.teamwhite.formula1.entity.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
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

    @Test
    public void createDriverWithoutCarTest() {
        Driver driver = new Driver();
        driver.setName("Lewis");
        driver.setSurname("Hamilton");
        driver.setNationality("GB");
        driver.setIsAggressive(true);
        driver.setWetDriving(10);
        driver.setReactions(10);

        driverDao.create(driver);

        Driver dbDriver = driverDao.findById(driver.getId());

        Assert.assertNotNull(dbDriver);
        Assert.assertNotNull(dbDriver.getId());
        Assert.assertNotEquals(dbDriver.getId(), 0L);
        Assert.assertEquals(dbDriver.getId(), driver.getId());
        Assert.assertEquals(dbDriver.getName(), driver.getName());
        Assert.assertEquals(dbDriver.getSurname(), driver.getSurname());
        Assert.assertEquals(dbDriver.getNationality(), driver.getNationality());
        Assert.assertEquals(dbDriver.isAggressive(), driver.isAggressive());
        Assert.assertEquals(dbDriver.getWetDriving(), driver.getWetDriving());
        Assert.assertEquals(dbDriver.getReactions(), driver.getReactions());
        Assert.assertNull(dbDriver.getCar());
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createDriverWithNullAttributesTest() {
        Driver invalidDriver = new Driver();
        driverDao.create(invalidDriver);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createNullDriverNameTest() {
        Driver driver = new Driver();
        driver.setSurname("Hamilton");
        driver.setNationality("GB");
        driver.setIsAggressive(true);
        driver.setWetDriving(10);
        driver.setReactions(10);

        driverDao.create(driver);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createNullDriverSurnameTest() {
        Driver driver = new Driver();
        driver.setName("Lewis");
        driver.setNationality("GB");
        driver.setIsAggressive(true);
        driver.setWetDriving(10);
        driver.setReactions(10);

        driverDao.create(driver);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createNullDriverNationalityTest() {
        Driver driver = new Driver();
        driver.setName("Lewis");
        driver.setSurname("Hamilton");
        driver.setIsAggressive(true);
        driver.setWetDriving(10);
        driver.setReactions(10);

        driverDao.create(driver);
    }

    @Test
    public void createDriverWithCarTest() {
        Car car = new Car();
        car.setName("Mercedes");

        carDao.create(car);

        Driver driver = new Driver();
        driver.setName("Lewis");
        driver.setSurname("Hamilton");
        driver.setNationality("GB");
        driver.setCar(car);
        driver.setIsAggressive(true);
        driver.setWetDriving(10);
        driver.setReactions(10);

        driverDao.create(driver);

        car.setDriver(driver);
        carDao.update(car);

        Driver dbDriver = driverDao.findById(driver.getId());

        Assert.assertNotNull(dbDriver);
        Assert.assertNotNull(dbDriver.getId());
        Assert.assertNotEquals(dbDriver.getId(), 0L);
        Assert.assertEquals(dbDriver.getName(), driver.getName());
        Assert.assertEquals(dbDriver.getSurname(), driver.getSurname());
        Assert.assertEquals(dbDriver.getNationality(), driver.getNationality());
        Assert.assertEquals(dbDriver.isAggressive(), driver.isAggressive());
        Assert.assertEquals(dbDriver.getWetDriving(), driver.getWetDriving());
        Assert.assertEquals(dbDriver.getReactions(), driver.getReactions());
        Assert.assertEquals(dbDriver.getCar().getName(), driver.getCar().getName());
        Assert.assertEquals(dbDriver.getCar().getName(), car.getName());
    }

    @Test
    public void findAllDriversTest() {
        Driver driver = new Driver();
        driver.setName("Lewis");
        driver.setSurname("Hamilton");
        driver.setNationality("GB");
        driver.setIsAggressive(true);
        driver.setWetDriving(10);
        driver.setReactions(10);

        driverDao.create(driver);

        Assert.assertEquals(driverDao.findAll().size(), 1);

        Driver driver2 = new Driver();
        driver2.setName("Valtteri");
        driver2.setSurname("Bottas");
        driver2.setNationality("FI");
        driver2.setIsAggressive(true);
        driver2.setReactions(9);
        driver2.setWetDriving(9);

        driverDao.create(driver2);

        List<Driver> allDrivers = driverDao.findAll();

        Assert.assertNotNull(allDrivers);
        Assert.assertEquals(allDrivers.size(), 2);
        Assert.assertTrue(allDrivers.contains(driver));
        Assert.assertTrue(allDrivers.contains(driver2));
    }

    @Test
    public void updateDriverTest() {
        Driver driver = new Driver();
        driver.setName("Lewis");
        driver.setSurname("Hamilton");
        driver.setNationality("GB");
        driver.setIsAggressive(true);
        driver.setWetDriving(10);
        driver.setReactions(10);

        driverDao.create(driver);

        driver.setName("Luisa");
        driverDao.update(driver);

        Driver dbDriver = driverDao.findById(driver.getId());
        Assert.assertEquals(dbDriver.getName(), "Luisa");
    }

    @Test
    public void updateDriverCarTest() {
        Car car1 = new Car();
        car1.setName("Mercedes");

        carDao.create(car1);

        Driver driver = new Driver();
        driver.setName("Lewis");
        driver.setSurname("Hamilton");
        driver.setNationality("GB");
        driver.setCar(car1);
        driver.setIsAggressive(true);
        driver.setWetDriving(10);
        driver.setReactions(10);

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
        Driver driver = new Driver();
        driver.setName("Lewis");
        driver.setSurname("Hamilton");
        driver.setNationality("GB");
        driver.setIsAggressive(true);
        driver.setWetDriving(10);
        driver.setReactions(10);

        driverDao.create(driver);

        Driver driver2 = new Driver();
        driver2.setName("Valtteri");
        driver2.setSurname("Bottas");
        driver2.setNationality("FI");
        driver2.setIsAggressive(true);
        driver2.setReactions(9);
        driver2.setWetDriving(9);

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
}