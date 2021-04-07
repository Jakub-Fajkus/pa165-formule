package cz.muni.pa165.teamwhite.formula1.dao;

import cz.muni.pa165.teamwhite.formula1.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Tomas Sedlacek
 */
@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(User user) {
        em.persist(user);
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public User findByLogin(String userLogin) {
        return em.createQuery("select u from User_table u where u.login = :login", User.class)
                .setParameter("login", userLogin).getSingleResult();
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select u from User_table u", User.class).getResultList();
    }

    @Override
    public void update(User user) {
        em.merge(user);
    }

    @Override
    public void remove(User user) {
        user = this.findById(user.getId());
        em.remove(user);
    }
}
