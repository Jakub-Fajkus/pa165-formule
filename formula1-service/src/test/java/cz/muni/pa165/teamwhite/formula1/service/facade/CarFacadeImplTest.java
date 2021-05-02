package cz.muni.pa165.teamwhite.formula1.service.facade;

import cz.muni.pa165.teamwhite.formula1.dto.CarDTO;
import cz.muni.pa165.teamwhite.formula1.dto.ComponentDTO;
import cz.muni.pa165.teamwhite.formula1.dto.DriverDTO;
import cz.muni.pa165.teamwhite.formula1.enums.ComponentType;
import cz.muni.pa165.teamwhite.formula1.facade.CarFacade;
import cz.muni.pa165.teamwhite.formula1.facade.ComponentFacade;
import cz.muni.pa165.teamwhite.formula1.facade.DriverFacade;
import cz.muni.pa165.teamwhite.formula1.service.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

/**
 * @author Jakub Fajkus
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class CarFacadeImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private CarFacade carFacade;

    @Autowired
    private ComponentFacade componentFacade;

    @Autowired
    private DriverFacade driverFacade;

    @Test
    public void testCreateACarAndThenUpdateOnlyItsName() {
        CarDTO createdDTO = new CarDTO("mercedes", new DriverDTO(null, "Valtteri", "Bottas", "FI", true, 9, 9), null);
        Long id = carFacade.createCar(createdDTO);

        CarDTO updated = carFacade.update(new CarDTO(id, "McLaren", null, null));

        DriverDTO botas = carFacade.getCarById(id).getDriver();

        Assert.assertEquals(botas.getCar(), updated);
        Assert.assertEquals(updated.getName(), "McLaren");
        Assert.assertEquals(updated.getDriver(), createdDTO.getDriver());
        Assert.assertEquals(updated.getComponents(), createdDTO.getComponents());
    }


    @Test
    public void testCreateACarAndThenUpdateOnlyItsDriver() {
        CarDTO createdDTO = new CarDTO("mercedes", new DriverDTO(null, "Valtteri", "Bottas", "FI", true, 9, 9), null);
        Long id = carFacade.createCar(createdDTO);

        Long botasId = carFacade.getCarById(id).getDriver().getId();

        DriverDTO newDriver = new DriverDTO(null, "Pepik", "Novaku", "CZE", false, 0, 0);
        CarDTO updated = carFacade.update(new CarDTO(id, null, newDriver, null));

        Assert.assertNull(driverFacade.getDriverById(botasId).getCar());
        Assert.assertEquals(updated.getName(), "mercedes");
        Assert.assertEquals(updated.getDriver(), newDriver);
        Assert.assertEquals(updated.getComponents(), createdDTO.getComponents());

        driverFacade.getAllDrivers();
    }


    @Test
    public void testCreateACarAndThenUpdateBothItsNameAndDriver() {
        CarDTO createdDTO = new CarDTO("mercedes", new DriverDTO(null, "Valtteri", "Bottas", "FI", true, 9, 9), null);
        Long id = carFacade.createCar(createdDTO);

        Long botasId = carFacade.getCarById(id).getDriver().getId();

        DriverDTO newDriver = new DriverDTO(null, "Pepik", "Novaku", "CZE", false, 0, 0);
        CarDTO updated = carFacade.update(new CarDTO(id, "McLaren", newDriver, null));

        Assert.assertNull(driverFacade.getDriverById(botasId).getCar());
        Assert.assertEquals(updated.getName(), "McLaren");
        Assert.assertEquals(updated.getDriver(), newDriver);
        Assert.assertEquals(updated.getComponents(), createdDTO.getComponents());
    }


    @Test
    public void testCreateACarWithoutComponentsAndThenAddAComponent() {
        CarDTO createdDTO = new CarDTO("mercedes", null, null);
        Long id = carFacade.createCar(createdDTO);

        ComponentDTO component = new ComponentDTO(ComponentType.ENGINE, "engine", null);
        CarDTO updated = carFacade.update(new CarDTO(id, null, null, Set.of(component)));

        Assert.assertEquals(updated.getName(), "mercedes");
        Assert.assertEquals(updated.getDriver(), createdDTO.getDriver());
        Assert.assertEquals(updated.getComponents(), Set.of(component));

        ComponentDTO comp = (ComponentDTO) updated.getComponents().toArray()[0];
        Assert.assertEquals(componentFacade.getComponentById(comp.getId()).getCar(), updated);

        Assert.assertEquals(1, componentFacade.getAllComponents().size());
    }


    @Test
    public void testCreateACarWithComponentsAndThenSetNewComponents() {
        ComponentDTO oldComponent1 = new ComponentDTO(ComponentType.ENGINE, "engine", null);
        ComponentDTO oldComponent2 = new ComponentDTO(ComponentType.TRANSMISSION, "trans", null);

        CarDTO createdDTO = new CarDTO("mercedes", null, Set.of(oldComponent1, oldComponent2));
        Long id = carFacade.createCar(createdDTO);

        ComponentDTO newComponent = new ComponentDTO(ComponentType.ENGINE, "newEngine", null);
        CarDTO updated = carFacade.update(new CarDTO(id, "mercedes", null, Set.of(newComponent)));

        Assert.assertEquals(updated.getName(), "mercedes");
        Assert.assertEquals(updated.getDriver(), createdDTO.getDriver());
        Assert.assertEquals(updated.getComponents(), Set.of(newComponent));

        List<ComponentDTO> components = componentFacade.getAllComponents();
        Assert.assertEquals(3, components.size());

        Assert.assertTrue(components.contains(oldComponent1));
        Assert.assertTrue(components.contains(oldComponent2));
        Assert.assertTrue(components.contains(newComponent));

        ComponentDTO dbOldComponent1 = components.get(components.indexOf(oldComponent1));
        ComponentDTO dbOldComponent2 = components.get(components.indexOf(oldComponent2));
        ComponentDTO dbNewComponent = components.get(components.indexOf(newComponent));

        Assert.assertNull(dbOldComponent1.getCar());
        Assert.assertNull(dbOldComponent2.getCar());
        Assert.assertEquals(createdDTO, dbNewComponent.getCar());
    }
}
