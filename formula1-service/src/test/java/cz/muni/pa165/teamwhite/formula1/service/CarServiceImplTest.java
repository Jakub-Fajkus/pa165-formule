package cz.muni.pa165.teamwhite.formula1.service;

import cz.muni.pa165.teamwhite.formula1.persistence.dao.CarDao;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Car;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Component;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Driver;
import cz.muni.pa165.teamwhite.formula1.persistence.enums.ComponentType;
import org.hibernate.service.spi.ServiceException;
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
import java.util.HashSet;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


/**
 * @author Jiri Andrlik
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class CarServiceImplTest extends AbstractTransactionalTestNGSpringContextTests {
    @Mock
    private CarDao carDao;

    @InjectMocks
    private final CarServiceImpl carService = new CarServiceImpl();

    private final Car formula = new Car("manager", null, new HashSet<>());

    private final Driver michal = new Driver(null, "Michal", "Koupil", "Czech", true, 2, 8);

    private final Component suspension = new Component(ComponentType.SUSPENSION, "fkinSpring");
    @BeforeMethod
    public void setUp() throws ServiceException
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCar() {
        carService.createCar(formula);
        verify(carDao).create(formula);
    }

    @Test(expectedExceptions = DuplicateKeyException.class)
    public void testCreateRethrowsDataAccessExceptionOnError() {
        doThrow(new DuplicateKeyException("failed")).when(carDao).create(formula);

        carService.createCar(formula);
    }


    @Test
    public void testFindByIdReturnsNullIfCarNotFound() {
        when(carDao.findById(666L)).thenReturn(null);

        Assert.assertNull(carService.findById(666L));
    }


    @Test
    public void testFindByIdReturnsCarIfItExists() {
        when(carDao.findById(420L)).thenReturn(formula);

        Assert.assertSame(carService.findById(420L), formula);
    }


    @Test
    public void testFindAllReturnsListOfCars() {
        ArrayList<Car> list = new ArrayList<>();

        when(carDao.findAll()).thenReturn(list);

        Assert.assertEquals(carService.findAll(), list);

    }

    @Test
    public void testUpdate() {
        carService.update(formula);

        verify(carDao).update(formula);
    }

    @Test
    public void testRemoveWhenCarExists() {
        when(carDao.findById(formula.getId())).thenReturn(formula);
        doNothing().when(carDao).remove(formula);

        carService.remove(formula.getId());

        verify(carDao).remove(formula);
    }


    @Test
    public void testRemoveWhenCarDoesNotExist() {
        when(carDao.findById(formula.getId())).thenReturn(null);

        carService.remove(formula.getId());

        verify(carDao, times(0)).remove(formula);
    }

    @Test
    public void testSetDriverToCar(){
        carService.setDriver(formula, michal);

        verify(carDao).update(formula);
    }

    @Test
    public void testAddComponent(){
        carService.addComponent(formula, suspension);

        verify(carDao).update(formula);
    }
}
