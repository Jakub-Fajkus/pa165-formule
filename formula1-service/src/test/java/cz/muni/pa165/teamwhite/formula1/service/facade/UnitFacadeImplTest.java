package cz.muni.pa165.teamwhite.formula1.service.facade;

import cz.muni.pa165.teamwhite.formula1.dto.UserDTO;
import cz.muni.pa165.teamwhite.formula1.enums.Role;
import cz.muni.pa165.teamwhite.formula1.facade.UserFacade;
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
 * @author Karolina Hecova
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class UnitFacadeImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    UserFacade userFacade;

    @Test
    public void testCreateUserAndThenUpdateOnlyLogin() {
        UserDTO user = new UserDTO("franta", null, Role.MANAGER);
        Long id = userFacade.createUser(user, "tajneHeslo");

        user = userFacade.getUserById(id);

        UserDTO updatedUser = userFacade.update(new UserDTO(id, "pepa", null, null));

        Assert.assertEquals(updatedUser.getLogin(), "pepa");
        Assert.assertEquals(updatedUser.getPassword(), user.getPassword());
        Assert.assertEquals(updatedUser.getRole(), user.getRole());
    }

    @Test
    public void testCreateUserAndThenUpdateOnlyRole() {
        UserDTO user = new UserDTO("franta", null, Role.MANAGER);
        Long id = userFacade.createUser(user, "tajneHeslo");

        user = userFacade.getUserById(id);

        UserDTO updatedUser = userFacade.update(new UserDTO(id, null, null, Role.ENGINEER));

        Assert.assertEquals(updatedUser.getLogin(), user.getLogin());
        Assert.assertEquals(updatedUser.getPassword(), user.getPassword());
        Assert.assertEquals(updatedUser.getRole(), Role.ENGINEER);
    }

    @Test
    public void testCreateUserAndThenUpdateOnlyPassword() {
        UserDTO user = new UserDTO("franta", null, Role.MANAGER);
        Long id = userFacade.createUser(user, "tajneHeslo");

        user = userFacade.getUserById(id);

        UserDTO updatedUser = userFacade.update(new UserDTO(id, null, "prisneTajneHeslo", null));

        Assert.assertEquals(updatedUser.getLogin(), user.getLogin());
        Assert.assertNotEquals(updatedUser.getPassword(), "prisneTajneHeslo");
        Assert.assertNotEquals(updatedUser.getPassword(), user.getPassword());
        Assert.assertEquals(updatedUser.getRole(), user.getRole());
    }

    @Test
    public void testCreateUserAndThenUpdateLoginAndRole() {
        UserDTO user = new UserDTO("franta", null, Role.MANAGER);
        Long id = userFacade.createUser(user, "tajneHeslo");

        user = userFacade.getUserById(id);

        UserDTO updatedUser = userFacade.update(new UserDTO(id, "pepa", null, Role.ENGINEER));

        Assert.assertEquals(updatedUser.getLogin(), "pepa");
        Assert.assertEquals(updatedUser.getPassword(), user.getPassword());
        Assert.assertEquals(updatedUser.getRole(), Role.ENGINEER);
    }

    @Test
    public void testCreateUserAndThenUpdateLoginRoleAndPassword() {
        UserDTO user = new UserDTO("franta", null, Role.MANAGER);
        Long id = userFacade.createUser(user, "tajneHeslo");

        user = userFacade.getUserById(id);

        UserDTO updatedUser = userFacade.update(new UserDTO(id, "pepa", "prisneTajneHeslo", Role.ENGINEER));

        Assert.assertEquals(updatedUser.getLogin(), "pepa");
        Assert.assertNotEquals(updatedUser.getPassword(), "prisneTajneHeslo");
        Assert.assertNotEquals(updatedUser.getPassword(), user.getPassword());
        Assert.assertEquals(updatedUser.getRole(), Role.ENGINEER);
    }
}
