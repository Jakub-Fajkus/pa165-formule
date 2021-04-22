package cz.muni.pa165.teamwhite.formula1.persistence.dao;

import cz.muni.pa165.teamwhite.formula1.persistence.entity.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Karolina Hecova
 */
@Repository
public class ComponentDaoImpl implements ComponentDao {

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
    public void remove(Component c) {
        em.remove(this.findById(c.getId()));
    }
}
