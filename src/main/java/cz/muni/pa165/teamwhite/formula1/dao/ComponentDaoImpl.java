package cz.muni.pa165.teamwhite.formula1.dao;

import cz.muni.pa165.teamwhite.formula1.entity.Car;
import cz.muni.pa165.teamwhite.formula1.entity.Component;
import cz.muni.pa165.teamwhite.formula1.enums.ComponentType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Karolina Hecova
 */
@Repository
public class ComponentDaoImpl implements ComponentDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Component c) {
        em.persist(c);
    }

    @Override
    public Component update(Component c) {
        return em.merge(c);
    }

    @Override
    public List<Component> findAll() {
        return em.createQuery("select c from Component c", Component.class).getResultList();
    }

    @Override
    public Component findById(Long id) {
        return em.find(Component.class, id);
    }

    @Override
    public List<Component> findByCar(Car car) {
        return em.createQuery("select c from Component c where c.car = :car", Component.class)
                .setParameter("car", car)
                .getResultList();
    }

    @Override
    public List<Component> findByType(ComponentType type) {
        return em.createQuery("select c from Component c where c.type = :type", Component.class)
                .setParameter("type", type)
                .getResultList();
    }

    @Override
    public void remove(Component c) {
        em.remove(this.findById(c.getId()));
    }
}
