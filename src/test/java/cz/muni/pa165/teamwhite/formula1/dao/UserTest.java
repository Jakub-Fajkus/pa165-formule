package cz.muni.pa165.teamwhite.formula1.dao;

import cz.muni.pa165.teamwhite.formula1.PersistenceSampleApplicationContext;
import cz.muni.pa165.teamwhite.formula1.entity.User;
import cz.muni.pa165.teamwhite.formula1.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

/**
 * @author Jiří Andrlík
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserDao userDao;


    @Test
    public void testCreate() {
        User kaja = new User("kaja", "CoTeToZajima", Role.MANAGER);
        userDao.create(kaja);
        Assert.assertTrue(true);
    }

    @Test
    public void testFetchById() {
        User jiri = new User("andrlos", "1234", Role.MANAGER);
        userDao.create(jiri);
        User u = userDao.findById(jiri.getId());
        Assert.assertEquals(u.getLogin(), jiri.getLogin());
        Assert.assertEquals(u.getPassword(), jiri.getPassword());
        Assert.assertEquals(u.getRole(), jiri.getRole());
    }

    @Test
    public void testFetchByNameAndRemove() {
        User tom = new User("lime", "4321", Role.ENGINEER);
        userDao.create(tom);
        tom = userDao.findByLogin("lime");
        userDao.remove(tom);
        Assert.assertThrows(NoResultException.class, () -> userDao.findByLogin("lime"));
    }

    @Test
    public void testUpdate() {
        User jakub = new User("spasitel", "JeToBezHesla", Role.ENGINEER);
        userDao.create(jakub);
        jakub = userDao.findByLogin("spasitel");

        String newPassword = "1111";

        jakub.setPassword(newPassword);

        userDao.update(jakub);
        User jakubClone = userDao.findById(jakub.getId());

        Assert.assertSame(jakub, jakubClone);
        Assert.assertEquals(jakubClone.getPassword(), newPassword);

    }

    @Test
    public void loginNotNull() {
        User user = new User(null, "1234", Role.MANAGER);
        Assert.assertThrows(ConstraintViolationException.class, () -> userDao.create(user));
    }

    @Test
    public void passwordNotNull() {
        User user = new User("jiri", null, Role.MANAGER);
        Assert.assertThrows(ConstraintViolationException.class, () -> userDao.create(user));
    }

    @Test
    public void roleNotNull() {
        User user = new User("jiri", "1111", null);
        Assert.assertThrows(ConstraintViolationException.class, () -> userDao.create(user));
    }
}
