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
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.Test;

import javax.transaction.Transactional;
import java.util.Set;

/**
 * @author Jiri Andrlik
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class DriverFacadeImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private DriverFacade driverFacade;

    @Autowired
    private CarFacade carFacade;

    @Test
    public void testCreateAndThenUpdateOnlyName() {
        DriverDTO driverDTO = new DriverDTO(null, "Valtteri", "Bottas", "Czech", true, 9, 9);
        Long id = driverFacade.createDriver(driverDTO);
        DriverDTO updated = driverFacade.update(new DriverDTO(id, null, "Michael", null, null, null, null, null));

        Assert.assertEquals("Michael", updated.getName());
        Assert.assertEquals("Czech", updated.getNationality());
    }

    @Test
    public void testCreateAndThenUpdateOnlySurname() {
        DriverDTO driverDTO = new DriverDTO(null, "Valtteri", "Bottas", "Czech", true, 9, 9);
        Long id = driverFacade.createDriver(driverDTO);
        DriverDTO updated = driverFacade.update(new DriverDTO(id, null, null, "Schumacher", null, null, null, null));

        Assert.assertEquals("Schumacher", updated.getSurname());
        Assert.assertEquals("Czech", updated.getNationality());
    }

    @Test
    public void testCreateAndThenUpdateOnlyNationality() {
        DriverDTO driverDTO = new DriverDTO(null, "Valtteri", "Bottas", "FI", true, 9, 9);
        Long id = driverFacade.createDriver(driverDTO);
        DriverDTO updated = driverFacade.update(new DriverDTO(id, null, null, null, "Czech", null, null, null));

        Assert.assertEquals(updated.getName(), "Valtteri");
        Assert.assertEquals(updated.getNationality(), "Czech");
    }

    @Test
    public void testCreateAndThenUpdateOnlyAggressivity() {
        DriverDTO driverDTO = new DriverDTO(null, "Valtteri", "Bottas", "FI", true, 9, 9);
        Long id = driverFacade.createDriver(driverDTO);
        DriverDTO updated = driverFacade.update(new DriverDTO(id, null, null, null, null, false, null, null));

        Assert.assertEquals(updated.getName(), "Valtteri");
        Assert.assertEquals(updated.isAggressive(), false);
    }

    @Test
    public void testCreateAndThenUpdateOnlyWetdriving() {
        DriverDTO driverDTO = new DriverDTO(null, "Valtteri", "Bottas", "FI", true, 9, 9);
        Long id = driverFacade.createDriver(driverDTO);
        DriverDTO updated = driverFacade.update(new DriverDTO(id, null, null, null, null, null, 2, null));

        Assert.assertEquals(updated.getReactions().intValue(), 9);
        Assert.assertEquals(updated.getWetDriving().intValue(), 2);
    }

    @Test
    public void testCreateAndThenUpdateOnlyReactions() {
        DriverDTO driverDTO = new DriverDTO(null, "Valtteri", "Bottas", "FI", true, 9, 9);
        Long id = driverFacade.createDriver(driverDTO);
        DriverDTO updated = driverFacade.update(new DriverDTO(id, null, null, null, null, null, null, 2));

        Assert.assertEquals(updated.getReactions().intValue(), 2);
        Assert.assertEquals(updated.getWetDriving().intValue(), 9);
    }

    @Test
    public void testCreateAndThenUpdateOnlyCar() {
        DriverDTO driverDTO = new DriverDTO(null, "Valtteri", "Bottas", "Czech", true, 9, 9);
        Long id = driverFacade.createDriver(driverDTO);
        DriverDTO updated = driverFacade.update(new DriverDTO(id, new CarDTO("audi", driverDTO, Set.of()), null, null, null, null, null, null));

        Assert.assertEquals("audi", updated.getCar().getName());
    }

}
