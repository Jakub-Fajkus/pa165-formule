package cz.muni.pa165.teamwhite.formula1.service.facade;

import cz.muni.pa165.teamwhite.formula1.dto.DriverDTO;
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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.transaction.Transactional;

/**
 * @author Karolina Hecova
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class UserFacadeImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    UserFacade userFacade;

    private UserDTO user;

    @BeforeMethod
    public void setUp() {
        user = new UserDTO("franta", null, Role.MANAGER);
    }

    @Test
    public void testCreateUserAndThenUpdateOnlyLogin() {
        Long id = userFacade.createUser(user, "tajneHeslo");

        user = userFacade.getUserById(id);

        UserDTO updatedUser = userFacade.update(new UserDTO(id, "pepa", null, null));

        Assert.assertEquals(updatedUser.getLogin(), "pepa");
        Assert.assertEquals(updatedUser.getPassword(), user.getPassword());
        Assert.assertEquals(updatedUser.getRole(), user.getRole());
    }

    @Test
    public void testCreateUserAndThenUpdateOnlyRole() {
        Long id = userFacade.createUser(user, "tajneHeslo");

        user = userFacade.getUserById(id);

        UserDTO updatedUser = userFacade.update(new UserDTO(id, null, null, Role.ENGINEER));

        Assert.assertEquals(updatedUser.getLogin(), user.getLogin());
        Assert.assertEquals(updatedUser.getPassword(), user.getPassword());
        Assert.assertEquals(updatedUser.getRole(), Role.ENGINEER);
    }

    @Test
    public void testCreateUserAndThenUpdateOnlyPassword() {
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
        Long id = userFacade.createUser(user, "tajneHeslo");

        user = userFacade.getUserById(id);

        UserDTO updatedUser = userFacade.update(new UserDTO(id, "pepa", null, Role.ENGINEER));

        Assert.assertEquals(updatedUser.getLogin(), "pepa");
        Assert.assertEquals(updatedUser.getPassword(), user.getPassword());
        Assert.assertEquals(updatedUser.getRole(), Role.ENGINEER);
    }

    @Test
    public void testCreateUserAndThenUpdateLoginRoleAndPassword() {
        Long id = userFacade.createUser(user, "tajneHeslo");

        user = userFacade.getUserById(id);

        UserDTO updatedUser = userFacade.update(new UserDTO(id, "pepa", "prisneTajneHeslo", Role.ENGINEER));

        Assert.assertEquals(updatedUser.getLogin(), "pepa");
        Assert.assertNotEquals(updatedUser.getPassword(), "prisneTajneHeslo");
        Assert.assertNotEquals(updatedUser.getPassword(), user.getPassword());
        Assert.assertEquals(updatedUser.getRole(), Role.ENGINEER);
    }
}
