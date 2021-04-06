package cz.muni.pa165.teamwhite.formula1.dao;

import cz.muni.pa165.teamwhite.formula1.entity.Car;
import cz.muni.pa165.teamwhite.formula1.entity.Driver;

import java.util.List;

/**
 * @author Jiří Andrlík
 */
public interface CarDao {
    /**
     * This method create new car in database
     * @param car - car to be added to DB
     */
    void create(Car car);

    /**
     * This method finds car in DB by entered id
     * @param id - id of the requested car
     * @return found car
     */
    Car findById(Long id);

    /**
     * This method finds car in DB by entered name
     * @param name - name of the requested car
     * @return found car
     */
    Car findByName(String name);

    /**
     * This method return all the cars from the storage
     * @return list of all cars
     */
    List<Car> findAll();

    /**
     * This method updates car already stored in DB
     * @param  car to update
     */
    void update(Car car);

    /**
     * This method removes car from DB
     * @param  car to be removed
     */
    void remove(Car car);
}
