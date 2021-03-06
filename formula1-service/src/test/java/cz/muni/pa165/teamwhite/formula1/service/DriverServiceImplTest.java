package cz.muni.pa165.teamwhite.formula1.service;

import cz.muni.pa165.teamwhite.formula1.persistence.dao.DriverDao;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Car;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Driver;
import cz.muni.pa165.teamwhite.formula1.service.exception.Formula1ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Karolina Hecova
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class DriverServiceImplTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private DriverDao driverDao;

    @InjectMocks
    private final DriverServiceImpl driverService = new DriverServiceImpl();

    private Driver driver;
    private Driver driver2;

    @BeforeMethod
    public void initDriver() {
        driver = new Driver(null, "Lewis", "Hamilton", "GB", true, 10, 10);
        driver.setId(666L);

        driver2 = new Driver(null, "Valtteri", "Bottas", "FI", true, 9, 9);
        driver2.setId(13L);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateDriver() {
        driverService.createDriver(driver);
        verify(driverDao).create(driver);
    }

    @Test(expectedExceptions = Formula1ServiceException.class)
    public void testCreateRethrowsDataAccessExceptionOnError() {
        doThrow(new DuplicateKeyException("failed")).when(driverDao).create(driver);
        driverService.createDriver(driver);
    }

    @Test
    public void testFindByIdReturnsDriver() {
        when(driverDao.findById(driver.getId())).thenReturn(driver);
        Assert.assertSame(driverService.findById(driver.getId()), driver);
    }

    @Test
    public void testFindByIdReturnsNullIfDriver() {
        when(driverDao.findById(42L)).thenReturn(null);
        Assert.assertNull(driverService.findById(42L));
    }

    @Test
    public void testFindAllReturnsListOfDrivers() {
        ArrayList<Driver> driverList = new ArrayList<>();
        driverList.add(driver);
        driverList.add(driver2);

        when(driverDao.findAll()).thenReturn(driverList);

        Assert.assertEquals(driverService.findAll(), driverList);
        Assert.assertEquals(driverService.findAll().size(), 2);
        Assert.assertTrue(driverService.findAll().contains(driver));
        Assert.assertTrue(driverService.findAll().contains(driver2));
    }

    @Test
    public void testUpdateDriver() {
        driverService.update(driver);
        verify(driverDao).update(driver);
    }

    @Test
    public void testRemoveWhenExistingDriver() {
        when(driverDao.findById(driver.getId())).thenReturn(driver);
        doNothing().when(driverDao).remove(driver);
        driverService.remove(driver.getId());
        verify(driverDao).remove(driver);
    }

    @Test
    public void testRemoveWhenDriverDoesNotExist() {
        when(driverDao.findById(driver.getId())).thenReturn(null);
        driverService.remove(driver.getId());
        verify(driverDao, times(0)).remove(driver);
    }

    @Test
    public void testSetIsAggressive() {
        driver.setIsAggressive(false);
        driverService.update(driver);
        verify(driverDao).update(driver);
    }

    @Test
    public void testSetCar() {
        Car car = new Car();
        car.setName("Mercedes");
        driver.setCar(car);
        driverService.update(driver);
        verify(driverDao).update(driver);
    }

    @Test
    public void testSetWetDriving() {
        driver.setWetDriving(5);
        driverService.update(driver);
        verify(driverDao).update(driver);
    }

    @Test
    public void testSetReactions() {
        driver.setReactions(5);
        driverService.update(driver);
        verify(driverDao).update(driver);
    }
}