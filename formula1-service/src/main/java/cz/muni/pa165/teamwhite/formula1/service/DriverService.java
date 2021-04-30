package cz.muni.pa165.teamwhite.formula1.service;


import cz.muni.pa165.teamwhite.formula1.persistence.entity.Car;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Driver;

import java.util.List;

/**
 * @author Jiri Andrlik
 */
public interface DriverService {

    /**
     * This method finds driver in the database by entered id.
     *
     * @param id - id of the requested user
     * @return found driver
     */
    Driver findById(Long id);

    /**
     * This method finds all drivers in the database.
     *
     * @return list of all users
     */
    List<Driver> findAll();

    /**
     * Creates new driver in the database.
     *
     * @param driver new driver to be created
     * @return id of the new user
     */
    Long createDriver(Driver driver);

    /**
     * Sets car to the driver
     * @param driver to be updated
     * @param car to be assigned
     */
    void setCar(Driver driver, Car car);

    /**
     * Sets aggresivity of the driver
     * @param driver to be updated
     * @param aggressive level that is assigned to the driver
     */
    void setIsAggressive(Driver driver, boolean aggressive);

    /**
     * Sets wet driving skill level to the driver
     * @param driver to be updated
     * @param wetDriving skills level that is assigned to the driver
     */
    void setWetDriving(Driver driver, int wetDriving);

    /**
     * Sets reactions level to the driver
     * @param driver to be updated
     * @param reactions level that is assigned to the driver
     */
    void setReactions(Driver driver, int reactions);

    /**
     * This method updates driver already stored in the database.
     *
     * @param driver to update
     */
    void update(Driver driver);

    /**
     * This method removes driver from the database.
     *
     * @param id user id to be removed
     */
    void remove(Long id);
}
