package cz.muni.pa165.teamwhite.formula1.service.facade;

import cz.muni.pa165.teamwhite.formula1.dto.DriverDTO;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Driver;
import cz.muni.pa165.teamwhite.formula1.service.DriverService;
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

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Karolina Hecova
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class DriverFacadeImplTest extends AbstractTransactionalTestNGSpringContextTests {
    @Mock
    private DriverService driverService;

    @Mock
    private BeanMappingService beanMappingService;

    @InjectMocks
    private final DriverFacadeImpl driverFacade = new DriverFacadeImpl();

    private final Driver driver = new Driver(null, "Lewis", "Hamilton", "GB", true, 10, 10);
    private final Driver driver2 = new Driver(null, "Valtteri", "Bottas", "FI", true, 9, 9);

    private final DriverDTO driverDTO = new DriverDTO(null, "Lewis", "Hamilton", "GB", true, 10, 10);
    private final DriverDTO driverDTO2 = new DriverDTO(null, "Valtteri", "Bottas", "FI", true, 9, 9);

    List<Driver> driverList = List.of(driver, driver2);
    List<DriverDTO> driverDTOList = List.of(driverDTO, driverDTO2);

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllDrivers() {
        when(driverService.findAll()).thenReturn(driverList);
        when(beanMappingService.mapTo(driverList, DriverDTO.class)).thenReturn(driverDTOList);

        Assert.assertSame(driverFacade.getAllDrivers(), driverDTOList);
    }

    @Test
    public void testCreateDriver() {
        when(beanMappingService.mapTo(driverDTO, Driver.class)).thenReturn(driver);
        when(driverService.createDriver(driver)).thenReturn(666L);

        Assert.assertEquals((long) driverFacade.createDriver(driverDTO), 666L);
    }

    @Test
    public void testDeleteDriver() {
        driverFacade.deleteDriver(666L);
        verify(driverService).remove(666L);
    }

    @Test
    public void testGetDriverById() {
        when(driverService.findById(driver.getId())).thenReturn(driver);
        when(beanMappingService.mapTo(driver, DriverDTO.class)).thenReturn(driverDTO);

        Assert.assertEquals(driverFacade.getDriverById(driver.getId()), driverDTO);
    }
}
