package cz.muni.pa165.teamwhite.formula1.service.facade;

import cz.muni.pa165.teamwhite.formula1.dto.CarDTO;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Car;
import cz.muni.pa165.teamwhite.formula1.service.CarService;
import cz.muni.pa165.teamwhite.formula1.service.ServiceConfiguration;
import cz.muni.pa165.teamwhite.formula1.service.mapping.BeanMappingService;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jiri Andrlik
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class CarFacadeImplUnitTest extends AbstractTransactionalTestNGSpringContextTests {
    @Mock
    private CarService carService;

    @Mock
    private BeanMappingService beanMappingService;

    @InjectMocks
    private final CarFacadeImpl carFacade = new CarFacadeImpl();

    private final Car formula = new Car("formula", null, new HashSet<>());

    private final CarDTO formulaDTO = new CarDTO("formula", null, new HashSet<>());

    List<Car> carList = List.of(formula);
    List<CarDTO> carDTOList = List.of(formulaDTO);

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCars() {
        when(carService.findAll()).thenReturn(carList);
        when(beanMappingService.mapTo(carList, CarDTO.class)).thenReturn(carDTOList);

        Assert.assertSame(carFacade.getAllCars(), carDTOList);
    }

    @Test
    public void testCreateCar() {
        when(beanMappingService.mapTo(formulaDTO, Car.class)).thenReturn(formula);
        when(carService.createCar(formula)).thenReturn(42L);

        Assert.assertEquals((long) carFacade.createCar(formulaDTO), 42L);
    }

    @Test
    public void testDeleteCar() {

        carFacade.deleteCar(42L);

        verify(carService).remove(42L);
    }

    @Test
    public void testGetCarById() {
        when(carService.findById(formula.getId())).thenReturn(formula);
        when(beanMappingService.mapTo(formula, CarDTO.class)).thenReturn(formulaDTO);

        Assert.assertEquals(carFacade.getCarById(formula.getId()), formulaDTO);
    }
}
