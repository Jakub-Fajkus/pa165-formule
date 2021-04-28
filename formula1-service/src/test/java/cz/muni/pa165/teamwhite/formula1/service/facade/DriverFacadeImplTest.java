package cz.muni.pa165.teamwhite.formula1.service.facade;


import cz.muni.pa165.teamwhite.formula1.service.DriverService;
import cz.muni.pa165.teamwhite.formula1.service.mapping.BeanMappingService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

/**
 * @author Jiri Andrlik
 */
@ContextConfiguration
public class DriverFacadeImplTest extends AbstractTransactionalTestNGSpringContextTests {
    @Mock
    private DriverService driverService;

    @Mock
    private BeanMappingService beanMappingService;

    @InjectMocks
    private final DriverFacadeImpl driverFacade = new DriverFacadeImpl();


}
