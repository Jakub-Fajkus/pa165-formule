package cz.muni.pa165.teamwhite.formula1.service;

import cz.muni.pa165.teamwhite.formula1.persistence.dao.DriverDao;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Driver;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class DriverServiceImplTest extends AbstractTransactionalTestNGSpringContextTests {
    @Mock
    private DriverDao driverDao;

    @InjectMocks
    private final DriverServiceImpl driverService = new DriverServiceImpl();

    private Driver driver;

    @BeforeMethod
    public void initDriver() {
        driver = new Driver(null, "Lewis", "Hamilton", "GB", true, 10, 10);
        driver.setId(666L);
    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateDriver() {
        driverService.createDriver(driver);

//        Assert.assertEquals(driver.getPassword(), "hashed");
        verify(driverDao).create(driver);
    }

    @Test(expectedExceptions = DuplicateKeyException.class)
    public void testCreateRethrowsDataAccessExceptionOnError() {
        doThrow(new DuplicateKeyException("failed")).when(driverDao).create(driver);

        driverService.createDriver(driver);
    }

    @Test
    public void testFindByIdReturnsUserIfItExists() {
        when(driverDao.findById(driver.getId())).thenReturn(driver);

        Assert.assertSame(driverService.findById(driver.getId()), driver);
    }

    @Test
    public void testFindByIdReturnsNullIfDriverNotFound() {
        when(driverDao.findById(42L)).thenReturn(null);

        Assert.assertNull(driverService.findById(42L));
    }

    @Test
    public void testFindAllReturnsListOfDrivers() {
        ArrayList<Driver> driverList = new ArrayList<>();
        driverList.add(driver);

        when(driverDao.findAll()).thenReturn(driverList);

        Assert.assertEquals(driverService.findAll(), driverList);
        Assert.assertTrue(driverService.findAll().contains(driver));
    }

    @Test
    public void testUpdateDriver() {
        driverService.update(driver);

        verify(driverDao).update(driver);
    }

    @Test
    public void testRemoveWhenDriverExists() {
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
}