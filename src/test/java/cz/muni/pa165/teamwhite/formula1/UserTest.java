package cz.muni.pa165.teamwhite.formula1;

import cz.muni.pa165.teamwhite.formula1.dao.UserDao;
import cz.muni.pa165.teamwhite.formula1.entity.User;
import cz.muni.pa165.teamwhite.formula1.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Jiří Andrlík
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    EntityManagerFactory emf;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserDao userDao;


    /**@BeforeClass
    public void setup(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User tom = new User("lime", "4321", Role.ENGINEER);
        User jakub = new User("spasitel", "JeToBezHesla", Role.ENGINEER);
        userDao.create(tom);
        userDao.create(jakub);
    }*/

    @AfterClass
    public void cleanDB(){
        List<User> all = userDao.findAll();

        Assert.assertNotEquals(all.size(), 0);

        em.createQuery("delete from User").executeUpdate();
        em.getTransaction().commit();
        all = userDao.findAll();
        Assert.assertEquals(all.size(), 0);

    }

    @Test
    public void testCreate(){
        User kaja = new User("kaja", "CoTeToZajima", Role.MANAGER);
        userDao.create(kaja);
        Assert.assertTrue(true);
    }

    @Test
    public void testFetchById(){
        User jiri = new User("andrlos", "1234", Role.MANAGER);
        userDao.create(jiri);
        User u = userDao.findById(jiri.getId());
        Assert.assertEquals(u.getLogin(), jiri.getLogin());
        Assert.assertEquals(u.getPassword(), jiri.getPassword());
        Assert.assertEquals(u.getRole(), jiri.getRole());
    }

    @Test
    public void testFetchByNameAndRemove(){
        User tom = userDao.findByLogin("lime");
        userDao.remove(tom);
        tom = userDao.findByLogin("lime");
        Assert.assertNull(tom);
    }

    @Test
    public void testUpdate(){
        User jakub = userDao.findByLogin("spasitel");
        String oldPassword = jakub.getPassword();
        String newPassword = "1111";

        jakub.setPassword(newPassword);
        Assert.assertEquals(userDao.findById(jakub.getId()).getPassword(), oldPassword);

        userDao.update(jakub);
        User jakubClone = userDao.findById(jakub.getId());

        Assert.assertSame(jakub, jakubClone);
        Assert.assertEquals(jakubClone.getPassword(), newPassword);

    }
    
}
