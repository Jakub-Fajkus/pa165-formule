package cz.muni.pa165.teamwhite.formula1.service;

import cz.muni.pa165.teamwhite.formula1.persistence.entity.Car;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Component;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Driver;

import java.util.List;

/**
 * @author Karolina Hecova
 */
public interface CarService {
    /**
     * Creates new car.
     * @param car - new car to be created
     * @return id of the new car
     */
    Long createCar(Car car);

    /**
     * Finds car in the database by the given id.
     *
     * @param id - id of the requested car
     * @return found car
     */
    Car findById(Long id);

    /**
     * Finds all cars in the database.
     *
     * @return list of all cars
     */
    List<Car> findAll();

    /**
     * Updates car already stored in the database.
     *
     * @param car to be updated
     */
    void update(Car car);

    /**
     * Removes given car from the database.
     *
     * @param id car id to be removed
     */
    void remove(Long id);

    /**
     * Sets given driver to the given car.
     * @param car which we want to set driver
     * @param driver which we want to set car
     */
    void setDriver(Car car, Driver driver);

    /**
     * Sets given component to the car.
     * @param car to which we want to set component
     * @param component to be set to the car
     */
    void addComponent(Car car, Component component);

    /**
     * Removes given component from the car.
     * @param car from which we want to remove component
     * @param component to be removed from the car
     */
    void removeComponent(Car car, Component component);
}
