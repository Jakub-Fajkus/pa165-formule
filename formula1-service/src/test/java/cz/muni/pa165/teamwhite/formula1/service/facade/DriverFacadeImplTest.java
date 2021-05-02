package cz.muni.pa165.teamwhite.formula1.service.facade;

import cz.muni.pa165.teamwhite.formula1.dto.CarDTO;
import cz.muni.pa165.teamwhite.formula1.dto.DriverDTO;
import cz.muni.pa165.teamwhite.formula1.facade.CarFacade;
import cz.muni.pa165.teamwhite.formula1.facade.DriverFacade;
import cz.muni.pa165.teamwhite.formula1.service.ServiceConfiguration;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.Test;

import javax.transaction.Transactional;

/**
 * @author Jiri Andrlik
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class DriverFacadeImplTest {
    @Autowired
    private DriverFacade driverFacade;

    @Autowired
    private CarFacade carFacade;

    @Test
    public void testCreateAndThenUpdateOnlyName() {
        DriverDTO driverDTO = new DriverDTO(new CarDTO("audi", null, null), "Valtteri", "Bottas", "FI", true, 9, 9);
        Long id = driverFacade.createDriver(driverDTO);

        DriverDTO updated = driverFacade.update(new DriverDTO(null, "Michael", null, null, null, null, null));

        CarDTO botas = driverFacade.getDriverById(id).getCar();

        Assert.assertEquals(botas.getDriver(), updated);
        Assert.assertEquals(updated.getName(), "Michael");
        Assert.assertEquals(updated.getCar(), driverDTO.getCar());
    }
}
