package cz.muni.pa165.teamwhite.formula1.service;


import cz.muni.pa165.teamwhite.formula1.persistence.entity.Car;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Driver;

import java.util.List;

/**
 * @author Jiri Andrlik
 */
public interface DriverService {

    /**
     *
     * @param id
     * @return
     */
    Driver findById(Long id);

    /**
     *
     * @return
     */
    List<Driver> findAll();

    /**
     *
     * @param driver
     * @return
     */
    Long createDriver(Driver driver);

    /**
     *
     * @param driver
     * @param car
     */
    void setCar(Driver driver, Car car);

    /**
     *
     * @param driver
     * @param aggresivity
     */
    void setIsAggresive(Driver driver, boolean aggresivity);

    /**
     *
     * @param driver
     * @param wetDriving
     */
    void setWetDriving(Driver driver, int wetDriving);

    /**
     *
     * @param driver
     * @param reactions
     */
    void setReactions(Driver driver, int reactions);

    /**
     *
     * @param id
     */
    void remove(Long id);
}
