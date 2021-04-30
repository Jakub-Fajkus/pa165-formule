package cz.muni.pa165.teamwhite.formula1.service;

import cz.muni.pa165.teamwhite.formula1.persistence.dao.UserDao;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.User;
import cz.muni.pa165.teamwhite.formula1.service.exception.Formula1ServiceException;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.*;


/**
 * @author Jakub Fajkus
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserServiceImplTest extends AbstractTransactionalTestNGSpringContextTests {
    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private final UserServiceImpl userService = new UserServiceImpl();

    private final User userManager = new User("manager", "hash", cz.muni.pa165.teamwhite.formula1.persistence.enums.Role.MANAGER);
    private final User userBFU = new User("pepa", "hash", cz.muni.pa165.teamwhite.formula1.persistence.enums.Role.ENGINEER);

    @BeforeMethod
    public void setup() throws ServiceException
    {
        userManager.setId(42L);
        userBFU.setId(71L);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUserCreatesUserAndHashesThePassword() {
        when(encoder.encode("pass")).thenReturn("hashed");

        userService.createUser(userManager, "pass");

        Assert.assertEquals(userManager.getPassword(), "hashed");
        verify(userDao).create(userManager);
    }

    @Test(expectedExceptions = Formula1ServiceException.class)
    public void testCreateRethrowsDataAccessExceptionOnError() {
        doThrow(new DuplicateKeyException("failed")).when(userDao).create(userManager);

        userService.createUser(userManager, "pass");
    }

    @Test
    public void testAuthenticateReturnsTrueIfTheUserExistsAndHasTheSamePassword() {
        String login = userBFU.getLogin();
        String plaintextPassword = "correct";

        when(userDao.findByLogin(userBFU.getLogin())).thenReturn(userManager);
        when(encoder.matches(plaintextPassword, userManager.getPassword())).thenReturn(true);

        Assert.assertTrue(userService.authenticate(login, plaintextPassword));
    }

    @Test
    public void testAuthenticateReturnsFalseIfTheUserExistsButHasDifferentPassword() {
        String login = userBFU.getLogin();
        String plaintextPassword = "incorrect";

        when(userDao.findByLogin(login)).thenReturn(userManager);
        when(encoder.matches(plaintextPassword, userManager.getPassword())).thenReturn(false);

        Assert.assertFalse(userService.authenticate(login, plaintextPassword));
    }

    @Test
    public void testAuthenticateReturnsFalseIfTheUserDoesNotExist() {
        String login = "doesNotExist";
        String plaintextPassword = "correct";

        when(userDao.findByLogin(login)).thenReturn(null);

        Assert.assertFalse(userService.authenticate(login, plaintextPassword));
    }

    @Test
    public void testIsManagerReturnsTrueIfTheUserIsManager() {
        when(userDao.findById(userManager.getId())).thenReturn(userManager);

        Assert.assertTrue(userService.isManager(userManager));
    }

    @Test
    public void testIsManagerReturnsFalseIfTheUserIsNotManager() {
        when(userDao.findById(userBFU.getId())).thenReturn(userBFU);

        Assert.assertFalse(userService.isManager(userBFU));
    }

    @Test
    public void testIsManagerReturnsFalseIfTheUserIsNotFound() {
        when(userDao.findById(666L)).thenReturn(null);

        Assert.assertFalse(userService.isManager(new User(666L)));
    }

    @Test
    public void testFindByIdReturnsUserIfItExists() {
        when(userDao.findById(userManager.getId())).thenReturn(userManager);

        Assert.assertSame(userService.findById(userManager.getId()), userManager);
    }

    @Test
    public void testFindByIdReturnsNullIfUserNotFound() {
        when(userDao.findById(666L)).thenReturn(null);

        Assert.assertNull(userService.findById(666L));
    }


    @Test
    public void testFindByLoginReturnsUserIfItExists() {
        when(userDao.findByLogin(userBFU.getLogin())).thenReturn(userBFU);

        Assert.assertSame(userService.findByLogin(userBFU.getLogin()), userBFU);
    }


    @Test
    public void testFindByLoginReturnsNullIfUserNotFound() {
        when(userDao.findByLogin("doesNotExist")).thenReturn(null);

        Assert.assertNull(userService.findByLogin("doesNotExist"));
    }

    @Test
    public void testFindAllReturnsListOfUsers() {
        ArrayList<User> list = new ArrayList<>();

        when(userDao.findAll()).thenReturn(list);

        Assert.assertEquals(userService.findAll(), list);

    }

    @Test
    public void testUpdate() {
        userService.update(userBFU);

        verify(userDao).update(userBFU);
    }

    @Test
    public void testRemoveWhenUserExists() {
        when(userDao.findById(userBFU.getId())).thenReturn(userBFU);
        doNothing().when(userDao).remove(userBFU);

        userService.remove(userBFU.getId());

        verify(userDao).remove(userBFU);
    }


    @Test
    public void testRemoveWhenUserDoesNotExist() {
        when(userDao.findById(userBFU.getId())).thenReturn(null);

        userService.remove(userBFU.getId());

        verify(userDao, times(0)).remove(userBFU);
    }
}