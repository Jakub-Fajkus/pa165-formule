package cz.muni.pa165.teamwhite.formula1.service.facade;

import cz.muni.pa165.teamwhite.formula1.dto.CarDTO;
import cz.muni.pa165.teamwhite.formula1.dto.DriverDTO;
import cz.muni.pa165.teamwhite.formula1.facade.CarFacade;
import cz.muni.pa165.teamwhite.formula1.persistence.PersistenceConfig;
import cz.muni.pa165.teamwhite.formula1.persistence.dao.CarDao;
import cz.muni.pa165.teamwhite.formula1.persistence.dao.ComponentDao;
import cz.muni.pa165.teamwhite.formula1.persistence.dao.DriverDao;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Car;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Component;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Driver;
import cz.muni.pa165.teamwhite.formula1.persistence.enums.ComponentType;
import cz.muni.pa165.teamwhite.formula1.service.ServiceConfiguration;
import cz.muni.pa165.teamwhite.formula1.service.mapping.BeanMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Objects;
import java.util.Set;

/**
 * @author Jakub Fajkus
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
//@ContextConfiguration(classes = PersistenceConfig.class)
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class CarFacadeImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private CarFacade carFacade;

    @Autowired
    private BeanMappingService mappingService;

    @Test
    public void CarWithNullNameTest() {
        CarDTO createdDTO = new CarDTO("mercedes", new DriverDTO(null, "Valtteri", "Bottas", "FI", true, 9, 9), null);
        Long id = carFacade.createCar(createdDTO);

        CarDTO updated = carFacade.update(new CarDTO(id, "McLaren", null, null));

        Assert.assertEquals(updated.getName(), "McLaren");
        Assert.assertEquals(updated.getDriver(), createdDTO.getDriver());
        Assert.assertEquals(updated.getComponents(), createdDTO.getComponents());

    }


    @Test
    public void CarWithNullNameTest() {
        CarDTO createdDTO = new CarDTO("mercedes", new DriverDTO(null, "Valtteri", "Bottas", "FI", true, 9, 9), null);
        Long id = carFacade.createCar(createdDTO);

        CarDTO updated = carFacade.update(new CarDTO(id, "McLaren", null, null));

        Assert.assertEquals(updated.getName(), "McLaren");
        Assert.assertEquals(updated.getDriver(), createdDTO.getDriver());
        Assert.assertEquals(updated.getComponents(), createdDTO.getComponents());

    }
}
