package cz.muni.pa165.teamwhite.formula1.persistence;


import cz.muni.pa165.teamwhite.formula1.persistence.PersistenceSampleApplicationContext;
import cz.muni.pa165.teamwhite.formula1.persistence.dao.ComponentDao;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Car;
import cz.muni.pa165.teamwhite.formula1.persistence.dao.CarDao;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Component;
import cz.muni.pa165.teamwhite.formula1.persistence.enums.ComponentType;
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
 * @author Jakub Fajkus
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class ComponentTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private ComponentDao componentDao;

    @Autowired
    private CarDao carDao;

    @Test
    public void testCreateFetchByIdAndRemoveComponents() {
        Component v12Engine = new Component(ComponentType.ENGINE, "V12 engine", null);
        Component v6Engine = new Component(ComponentType.ENGINE, "V16 engine", null);
        Component rims = new Component(ComponentType.RIMS, "23", null);

        componentDao.create(v12Engine);
        componentDao.create(v6Engine);
        componentDao.create(rims);

        Component v12EngineDB = componentDao.findById(v12Engine.getId());
        Assert.assertSame(v12EngineDB, v12Engine);

        Component v6EngineDB = componentDao.findById(v6Engine.getId());
        Assert.assertSame(v6EngineDB, v6Engine);

        Component rimsDB = componentDao.findById(rims.getId());
        Assert.assertSame(rimsDB, rims);

        componentDao.remove(v6EngineDB);
        componentDao.remove(v12EngineDB);
        componentDao.remove(rims);

        Assert.assertNull(componentDao.findById(v12Engine.getId()));
        Assert.assertNull(componentDao.findById(v6Engine.getId()));
        Assert.assertNull(componentDao.findById(rims.getId()));

    }

    @Test
    public void testAssociationCar() {
        Car car = new Car();
        car.setName("Blue lightning");
        carDao.create(car);

        Component engine = new Component(ComponentType.ENGINE, "V12 engine", null);
        car.addComponent(engine);
        engine.setCar(car);

        Assert.assertEquals(engine.getCar(), car);

        componentDao.create(engine);
        carDao.update(car);

        Component engineDB = componentDao.findById(engine.getId());
        Car carDB = carDao.findById(car.getId());

        Assert.assertEquals(engineDB.getName(), engine.getName());
        Assert.assertEquals(carDB.getName(), car.getName());

        Assert.assertEquals(carDB.getComponents().size(), 1);
        Assert.assertEquals(((Component) carDB.getComponents().toArray()[0]).getName(), engine.getName());
    }

    @Test
    public void testUpdate() {
        Component engine = new Component(ComponentType.ENGINE, "V12 engine", null);
        componentDao.create(engine);

        engine.setName("V12 engine 667Hp");
        componentDao.update(engine);

        Assert.assertEquals(componentDao.findById(engine.getId()).getName(), "V12 engine 667Hp");

        componentDao.update(engine); // we need to call update to force hibernate to save the data to the DB
        Component foundEngine = componentDao.findById(engine.getId());

        Assert.assertSame(engine, foundEngine);
        Assert.assertEquals(foundEngine.getName(), engine.getName());
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testComponentTypeNotNull() {
        Component component = new Component(null, "component");
        componentDao.create(component);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testNameNotNull() {
        Component component = new Component(ComponentType.RIMS, null);
        componentDao.create(component);
    }
}

