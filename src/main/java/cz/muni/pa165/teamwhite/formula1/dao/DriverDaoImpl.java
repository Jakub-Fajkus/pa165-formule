package cz.muni.pa165.teamwhite.formula1.dao;

import cz.muni.pa165.teamwhite.formula1.entity.Driver;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Jakub Fajkus
 */
@Repository
public class DriverDaoImpl implements DriverDao {
    @PersistenceContext
    private EntityManager em;


    @Override
    public void create(Driver d) {
        em.persist(d);
    }

    @Override
    public Driver update(Driver d) {
        return em.merge(d);
    }

    @Override
    public List<Driver> findAll() {
        return em.createQuery("select d from Driver d", Driver.class).getResultList();
    }

    @Override
    public Driver findById(Long id) {
        return em.find(Driver.class, id);
    }

    @Override
    public void remove(Driver d) {
        em.remove(this.findById(d.getId()));
    }

}
