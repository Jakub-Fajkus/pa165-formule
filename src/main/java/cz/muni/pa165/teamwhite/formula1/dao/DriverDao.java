package cz.muni.pa165.teamwhite.formula1.dao;

import cz.muni.pa165.teamwhite.formula1.entity.Driver;

import java.util.List;


/**
 * @author Jakub Fajkus
 */
public interface DriverDao {

    /**
     * Create a driver in the storage
     *
     * @param d Driver entity to be created in the storage
     */
    void create(Driver d);

    /**
     * Update the driver entity in the storage
     *
     * @param d Driver entity to be updated in the storage
     * @return The updated driver entity
     */
    Driver update(Driver d);

    /**
     * Get all Driver entities from the storage.
     *
     * @return All Driver entities
     */
    List<Driver> findAll();

    /**
     * Find one driver entity from the storage
     *
     * @param id Primary key of the Driver entity.
     * @return Driver entity with given primary key
     */
    Driver findById(Long id);

    /**
     * Remove given driver entity from the storage
     *
     * @param d Driver entity to be removed
     */
    void remove(Driver d);
}
