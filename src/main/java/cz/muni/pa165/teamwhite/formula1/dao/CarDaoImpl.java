package cz.muni.pa165.teamwhite.formula1.dao;

import cz.muni.pa165.teamwhite.formula1.entity.Car;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Jiří Andrlík
 */
@Repository
public class CarDaoImpl implements CarDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Car car) {
        em.persist(car);
    }

    @Override
    public Car findById(Long id) {
        return em.find(Car.class, id);
    }

    @Override
    public Car findByName(String name) {
        Car car = em.createQuery("select c from Car c where c.name = :name", Car.class).setParameter("name", name).getSingleResult();
        return car;
    }

    @Override
    public List<Car> findAll() {
        return em.createQuery("select c from Car c", Car.class).getResultList();
    }

    @Override
    public void update(Car car) {
        em.merge(car);
    }

    @Override
    public void remove(Car car) {
        car = this.findById(car.getId());
        em.remove(car);
    }
}
