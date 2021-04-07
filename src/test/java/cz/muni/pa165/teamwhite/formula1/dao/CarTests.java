package cz.muni.pa165.teamwhite.formula1.dao;

import cz.muni.pa165.teamwhite.formula1.PersistenceSampleApplicationContext;
import cz.muni.pa165.teamwhite.formula1.entity.Car;
import cz.muni.pa165.teamwhite.formula1.entity.Component;
import cz.muni.pa165.teamwhite.formula1.entity.Driver;
import cz.muni.pa165.teamwhite.formula1.enums.ComponentType;
import org.springframework.beans.factory.annotation.Autowired;
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
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
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
        Driver lewis = createDriverLewis();
        Driver fernando = createDriverFernando();

        Car car1 = new Car();
        car1.setName("monopost2021");
        car1.setDriver(lewis);

        Car car2 = new Car();
        car2.setName("monopost2020");
        car2.setDriver(fernando);

        lewis.setCar(car1);
        fernando.setCar(car2);

        Component transmission = createTransmission();
        Component engine = createEngine();

        car1.addComponent(transmission);
        car2.addComponent(engine);

        driverDao.create(lewis);
        driverDao.create(fernando);

        componentDao.create(engine);
        componentDao.create(transmission);

        carDao.create(car1);
        carDao.create(car2);

        Assert.assertEquals(carDao.findAll().size(), 2);
    }

    @Test
    public void findByIdTest() {
        Driver lewis = createDriverLewis();
        Car monopost2021 = new Car();

        monopost2021.setName("monopost 2021");
        monopost2021.setDriver(lewis);
        lewis.setCar(monopost2021);

        driverDao.create(lewis);
        carDao.create(monopost2021);

        Car found = carDao.findById(monopost2021.getId());
        Assert.assertEquals(found, monopost2021);
    }

    @Test
    public void findByNameTest() {
        Driver lewis = createDriverLewis();
        Car monopost2021 = new Car();

        monopost2021.setName("monopost 2021");
        monopost2021.setDriver(lewis);
        lewis.setCar(monopost2021);

        driverDao.create(lewis);
        carDao.create(monopost2021);

        Car found = carDao.findByName(monopost2021.getName());
        Assert.assertEquals(found, monopost2021);
    }

    @Test
    public void findAllTest() {
        Car car1 = new Car();
        car1.setName("monopost1");
        carDao.create(car1);
        Assert.assertEquals(carDao.findAll().size(), 1);

        Car car2 = new Car();
        car2.setName("monopost2");
        carDao.create(car2);
        Assert.assertEquals(carDao.findAll().size(), 2);

        Car car3 = new Car();
        car3.setName("monopost3");
        carDao.create(car3);
        Assert.assertEquals(carDao.findAll().size(), 3);
    }

    @Test
    public void updateCar() {
        Car car = new Car();
        car.setName("AMG Petronas 2020");

        Driver lewis = createDriverLewis();
        Driver fernando = createDriverFernando();

        car.setDriver(lewis);
        lewis.setCar(car);

        driverDao.create(lewis);
        driverDao.create(fernando);
        carDao.create(car);

        car.setName("AMG petronas ultra fast uber new car");
        car.setDriver(fernando);
        lewis.setCar(null);
        fernando.setCar(car);

        driverDao.update(lewis);
        driverDao.update(fernando);
        carDao.update(car);

        Car found = carDao.findById(car.getId());
        Assert.assertEquals(found.getName(), "AMG petronas ultra fast uber new car");
        Assert.assertNull(lewis.getCar());
        Assert.assertEquals(found.getDriver(), fernando);
    }

    @Test
    public void removeCarTest() {
        Car carFerrari = new Car();
        carFerrari.setName("F1 Scuderia Ferrari 2021 - car1");
        carDao.create(carFerrari);

        Car carMercedes = new Car();
        carMercedes.setName("Petronas Mercedes - car1");
        carDao.create(carMercedes);

        Assert.assertEquals(carDao.findAll().size(), 2);

        carDao.remove(carFerrari);

        Assert.assertEquals(carDao.findAll().size(), 1);
    }

    public Driver createDriverLewis() {
        Driver driver = new Driver();
        driver.setName("Lewis");
        driver.setSurname("Hamilton");
        driver.setNationality("GB");
        driver.setIsAggressive(true);
        driver.setWetDriving(10);
        driver.setReactions(10);

        return driver;
    }

    public Driver createDriverFernando() {
        Driver driver = new Driver();
        driver.setName("Fernando");
        driver.setSurname("Alonso");
        driver.setNationality("ES");
        driver.setIsAggressive(true);
        driver.setWetDriving(9);
        driver.setReactions(8);

        return driver;
    }

    public Component createEngine() {
        Component engine = new Component(ComponentType.ENGINE, "BlueFire3000");
        return engine;

    }

    public Component createTransmission() {
        Component transmission = new Component(ComponentType.TRANSMISSION, "7 gears");
        return transmission;
    }
}
