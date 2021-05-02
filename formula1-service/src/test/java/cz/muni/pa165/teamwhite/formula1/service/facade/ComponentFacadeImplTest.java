package cz.muni.pa165.teamwhite.formula1.service.facade;

import cz.muni.pa165.teamwhite.formula1.dto.CarDTO;
import cz.muni.pa165.teamwhite.formula1.dto.ComponentDTO;
import cz.muni.pa165.teamwhite.formula1.enums.ComponentType;
import cz.muni.pa165.teamwhite.formula1.facade.CarFacade;
import cz.muni.pa165.teamwhite.formula1.facade.ComponentFacade;
import cz.muni.pa165.teamwhite.formula1.service.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.transaction.Transactional;

/**
 * @author Tomas Sedlacek
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class ComponentFacadeImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private CarFacade carFacade;

    @Autowired
    private ComponentFacade componentFacade;

    @Test
    public void testCreateAComponentAndThenUpdateOnlyItsName() {
        ComponentDTO createdDTO = new ComponentDTO(ComponentType.ENGINE, "engine", new CarDTO("mercedes", null, null));
        Long id = componentFacade.createComponent(createdDTO);

        ComponentDTO updated = componentFacade.update(new ComponentDTO(id, null, "new engine", null));

        CarDTO mercedes = componentFacade.getComponentById(id).getCar();

        Assert.assertEquals(mercedes.getComponents().size(), 1);
        Assert.assertTrue(mercedes.getComponents().toArray()[0].equals(updated));
        Assert.assertEquals(updated.getName(), "new engine");
        Assert.assertEquals(updated.getCar(), createdDTO.getCar());
        Assert.assertEquals(updated.getType(), createdDTO.getType());
    }

    @Test
    public void testCreateAComponentAndThenUpdateOnlyItsType() {
        ComponentDTO createdDTO = new ComponentDTO(ComponentType.ENGINE, "component", new CarDTO("mercedes", null, null));
        Long id = componentFacade.createComponent(createdDTO);

        ComponentDTO updated = componentFacade.update(new ComponentDTO(id, ComponentType.RIMS, null, null));

        CarDTO mercedes = componentFacade.getComponentById(id).getCar();

        Assert.assertEquals(mercedes.getComponents().size(), 1);
        Assert.assertTrue(mercedes.getComponents().toArray()[0].equals(updated));
        Assert.assertEquals(updated.getName(), createdDTO.getName());
        Assert.assertEquals(updated.getCar(), createdDTO.getCar());
        Assert.assertEquals(updated.getType(), ComponentType.RIMS);
    }

    @Test
    public void testCreateAComponentAndThenUpdateOnlyItsCar() {
        ComponentDTO createdDTO = new ComponentDTO(ComponentType.ENGINE, "component", new CarDTO("mercedes", null, null));
        Long id = componentFacade.createComponent(createdDTO);

        CarDTO newCar =  new CarDTO("ferrari", null, null);
        carFacade.createCar(newCar);
        ComponentDTO updated = componentFacade.update(new ComponentDTO(id, null, null, newCar));
        CarDTO ferrari = componentFacade.getComponentById(id).getCar();

        Assert.assertEquals(ferrari.getComponents().size(), 1);
        Assert.assertTrue(ferrari.getComponents().toArray()[0].equals(updated));
        Assert.assertEquals(updated.getName(), createdDTO.getName());
        Assert.assertEquals(updated.getCar(), newCar);
        Assert.assertEquals(updated.getType(), createdDTO.getType());
    }

    @Test
    public void testCreateAComponentWithoutCarAndThenAddToCar() {
        ComponentDTO newComponentDTO = new ComponentDTO(ComponentType.ENGINE, "engine", null);
        Long componentId = componentFacade.createComponent(newComponentDTO);

        CarDTO carForComponent = new CarDTO("mercedes", null, null);

        ComponentDTO updated = componentFacade.update(new ComponentDTO(componentId, null, null, carForComponent));

        Assert.assertEquals(updated.getType(), newComponentDTO.getType());
        Assert.assertEquals(updated.getName(), newComponentDTO.getName());
        Assert.assertEquals(updated.getCar(), carForComponent);
    }


}
