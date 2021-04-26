package cz.muni.pa165.teamwhite.formula1.persistence;

import cz.muni.pa165.teamwhite.formula1.persistence.dao.CarDao;
import cz.muni.pa165.teamwhite.formula1.persistence.dao.ComponentDao;
import cz.muni.pa165.teamwhite.formula1.persistence.dao.DriverDao;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Car;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Component;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Driver;
import cz.muni.pa165.teamwhite.formula1.persistence.enums.ComponentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

/**
 * @author Tomas Sedlacek
 */
@ContextConfiguration(classes = PersistenceConfig.class)
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class CarTests extends AbstractTestNGSpringContextTests {
    @Autowired
    private DriverDao driverDao;

    @Autowired
    private ComponentDao componentDao;

    @Autowired
    private CarDao carDao;

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void CarWithNullNameTest() {
        Car car = new Car(null, null, null);

        carDao.create(car);
    }

    @Test
    public void createTwoCars() {
        Driver lewis = createDriverLewisDao();
        Driver fernando = createDriverFernandoDao();

        Car mercedes = createCarMercedesDao();
        mercedes.setDriver(lewis);

        Car ferrari = createcarFerrariDao();
        ferrari.setDriver(fernando);

        lewis.setCar(mercedes);
        fernando.setCar(ferrari);

        Component transmission = createTransmissionDao();
        Component engine = createEngineDao();

        mercedes.addComponent(engine);
        ferrari.addComponent(transmission);

        driverDao.update(lewis);
        driverDao.update(fernando);

        componentDao.update(engine);
        componentDao.update(transmission);

        carDao.update(ferrari);
        carDao.update(mercedes);

        Assert.assertEquals(carDao.findAll().size(), 2);
    }

    @Test
    public void findByIdTest() {
        Driver lewis = createDriverLewisDao();
        Car mercedes = createCarMercedesDao();

        mercedes.setDriver(lewis);
        lewis.setCar(mercedes);

        driverDao.update(lewis);
        carDao.update(mercedes);

        Car found = carDao.findById(mercedes.getId());
        Assert.assertEquals(found, mercedes);
    }

    @Test
    public void findByNameTest() {
        Driver lewis = createDriverLewisDao();
        Car mercedes = createCarMercedesDao();

        mercedes.setDriver(lewis);
        lewis.setCar(mercedes);

        driverDao.update(lewis);
        carDao.update(mercedes);

        Car found = carDao.findByName(mercedes.getName());
        Assert.assertEquals(found, mercedes);
    }

    @Test
    public void findAllTest() {
        Car ferrari = createcarFerrariDao();
        Assert.assertEquals(carDao.findAll().size(), 1);

        Car mercedes = createCarMercedesDao();
        Assert.assertEquals(carDao.findAll().size(), 2);

        Car car3 = new Car();
        car3.setName("monopost3");
        carDao.create(car3);
        Assert.assertEquals(carDao.findAll().size(), 3);
    }

    @Test
    public void updateCar() {
        Car ferrari = createcarFerrariDao();

        Driver lewis = createDriverLewisDao();
        Driver fernando = createDriverFernandoDao();

        ferrari.setDriver(lewis);
        lewis.setCar(ferrari);

        driverDao.update(lewis);
        driverDao.update(fernando);
        carDao.update(ferrari);

        ferrari.setName("AMG petronas ultra fast uber new car");
        ferrari.setDriver(fernando);
        lewis.setCar(null);
        fernando.setCar(ferrari);

        driverDao.update(lewis);
        driverDao.update(fernando);
        carDao.update(ferrari);

        Car found = carDao.findById(ferrari.getId());
        Assert.assertEquals(found.getName(), "AMG petronas ultra fast uber new car");
        Assert.assertNull(lewis.getCar());
        Assert.assertEquals(found.getDriver(), fernando);
    }

    @Test
    public void removeCarTest() {
        Car ferrari = createcarFerrariDao();
        Car mercedes = createCarMercedesDao();

        Assert.assertEquals(carDao.findAll().size(), 2);

        carDao.remove(ferrari);

        Assert.assertEquals(carDao.findAll().size(), 1);
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void removeCarNotInDbTest() {
        Car carInDb = createCarMercedesDao();

        Car carNotInDb = createCarFerrari();

        carDao.remove(carNotInDb);
        Assert.assertEquals(carDao.findAll().size(), 1);
    }

    private Car createCarFerrari() {
        Car ferrari = new Car();
        ferrari.setName("F1 Scuderia Ferrari 2021 - car1");

        return ferrari;
    }

    private Car createcarFerrariDao() {
        Car ferrari = createCarFerrari();
        carDao.create(ferrari);

        return ferrari;
    }

    private Car createCaMercedes() {
        Car mercedes = new Car();
        mercedes.setName("Petronas Mercedes - car1");

        return mercedes;
    }

    private Car createCarMercedesDao() {
        Car mercedes = createCaMercedes();
        carDao.create(mercedes);

        return mercedes;
    }

    private Driver createDriverLewis() {
        Driver driver = new Driver();
        driver.setName("Lewis");
        driver.setSurname("Hamilton");
        driver.setNationality("GB");
        driver.setIsAggressive(true);
        driver.setWetDriving(10);
        driver.setReactions(10);

        return driver;
    }

    private Driver createDriverLewisDao() {
        Driver lewis = createDriverLewis();
        driverDao.create(lewis);

        return lewis;
    }

    private Driver createDriverFernando() {
        Driver driver = new Driver();
        driver.setName("Fernando");
        driver.setSurname("Alonso");
        driver.setNationality("ES");
        driver.setIsAggressive(true);
        driver.setWetDriving(9);
        driver.setReactions(8);

        return driver;
    }

    private Driver createDriverFernandoDao() {
        Driver fernando = createDriverFernando();
        driverDao.create(fernando);

        return fernando;
    }

    private Component createEngine() {
        Component engine = new Component(ComponentType.ENGINE, "BlueFire3000");

        return engine;
    }

    private Component createEngineDao() {
        Component engine = createEngine();
        componentDao.create(engine);

        return engine;
    }

    private Component createTransmission() {
        Component transmission = new Component(ComponentType.TRANSMISSION, "7 gears");

        return transmission;
    }

    private Component createTransmissionDao() {
        Component transmission = createTransmission();
        componentDao.create(transmission);

        return transmission;
    }
}
