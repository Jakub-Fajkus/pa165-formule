package cz.muni.pa165.teamwhite.formula1;

import cz.muni.pa165.teamwhite.formula1.dao.CarDao;
import cz.muni.pa165.teamwhite.formula1.dao.DriverDao;
import cz.muni.pa165.teamwhite.formula1.entity.Car;
import cz.muni.pa165.teamwhite.formula1.entity.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.TransactionSystemException;
import org.testng.Assert;
import org.testng.annotations.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author Karolina Hecova
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class DriverTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private DriverDao driverDao;

    @Autowired
    private CarDao carDao;

    @AfterMethod
    public void afterClass() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.createQuery("delete from Driver").executeUpdate();
            em.createQuery("delete from Car").executeUpdate();
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Test
    public void createDriverWithoutCarTest() {
        Driver driver = new Driver();
        driver.setName("Lewis");
        driver.setSurname("Hamilton");
        driver.setNationality("GB");
        driver.setAggressive(true);
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
        driver.setAggressive(true);
        driver.setWetDriving(10);
        driver.setReactions(10);

        driverDao.create(driver);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createNullDriverSurnameTest() {
        Driver driver = new Driver();
        driver.setName("Lewis");
        driver.setNationality("GB");
        driver.setAggressive(true);
        driver.setWetDriving(10);
        driver.setReactions(10);

        driverDao.create(driver);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createNullDriverNationalityTest() {
        Driver driver = new Driver();
        driver.setName("Lewis");
        driver.setSurname("Hamilton");
        driver.setAggressive(true);
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
        driver.setAggressive(true);
        driver.setWetDriving(10);
        driver.setReactions(10);

        driverDao.create(driver);

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
    }

    @Test
    public void findAllDriversTest() {
        Driver driver = new Driver();
        driver.setName("Lewis");
        driver.setSurname("Hamilton");
        driver.setNationality("GB");
        driver.setAggressive(true);
        driver.setWetDriving(10);
        driver.setReactions(10);

        driverDao.create(driver);

        Assert.assertEquals(driverDao.findAll().size(), 1);

        Driver driver2 = new Driver();
        driver2.setName("Valtteri");
        driver2.setSurname("Bottas");
        driver2.setNationality("FI");
        driver2.setAggressive(true);
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
        driver.setAggressive(true);
        driver.setWetDriving(10);
        driver.setReactions(10);

        driverDao.create(driver);

        driver.setWetDriving(8);

        Driver dbDriver = driverDao.findById(driver.getId());

        Assert.assertEquals(dbDriver.getWetDriving(), 10);

        dbDriver = driverDao.update(driver);

        Assert.assertEquals(dbDriver.getWetDriving(), 8);
    }

    @Test(expectedExceptions = TransactionSystemException.class)
    public void updateNullDriverNameTest() {
        Driver driver = new Driver();
        driver.setName("Lewis");
        driver.setSurname("Hamilton");
        driver.setNationality("GB");
        driver.setAggressive(true);
        driver.setWetDriving(10);
        driver.setReactions(10);

        driverDao.create(driver);

        Driver dbDriver = driverDao.findById(driver.getId());

        Assert.assertEquals(dbDriver.getId(), driver.getId());
        Assert.assertEquals(dbDriver.getName(), driver.getName());

        driver.setName(null);
        driverDao.update(driver);
    }

    @Test
    public void removeDriverTest() {
        Driver driver = new Driver();
        driver.setName("Lewis");
        driver.setSurname("Hamilton");
        driver.setNationality("GB");
        driver.setAggressive(true);
        driver.setWetDriving(10);
        driver.setReactions(10);

        driverDao.create(driver);

        Driver driver2 = new Driver();
        driver2.setName("Valtteri");
        driver2.setSurname("Bottas");
        driver2.setNationality("FI");
        driver2.setAggressive(true);
        driver2.setReactions(9);
        driver2.setWetDriving(9);

        driverDao.create(driver2);

        Assert.assertEquals(driverDao.findAll().size(), 2);

        driverDao.remove(driver2);

        Assert.assertEquals(driverDao.findAll().size(), 1);
    }
}