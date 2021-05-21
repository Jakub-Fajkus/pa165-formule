package cz.muni.pa165.teamwhite.formula1.facade;


import cz.muni.pa165.teamwhite.formula1.dto.DriverDTO;
import cz.muni.pa165.teamwhite.formula1.dto.ScoredDriverDTO;

import java.util.List;

/**
 * @author Jiri Andrlik
 */
public interface DriverFacade {

	/**
	 * @return list of all drivers
	 */
	List<DriverDTO> getAllDrivers();

	/**
	 * @return list of all drivers with score
	 */
	List<ScoredDriverDTO> getAllDriversWithScore();

	/**
	 *
	 * @param driverDTO driver to be added to the database
	 * @return id of created driver
	 */
	Long createDriver(DriverDTO driverDTO);

	/**
	 * deletes a driver from the database
	 * @param driverId - id of the driver to be deleted
	 */
	void deleteDriver(Long driverId);

	/**
	 * returns single driver from the database
	 * @param driverId - id of the driver
	 * @return DriverDTO of the driver
	 */
	DriverDTO getDriverById(Long driverId);

	/**
	 * updates given driver and returns driver DTO with updated data
	 *
	 * @param driverDTO containing only fields that you want to set
	 * @return a new DTO with all fields
	 */
	DriverDTO update(DriverDTO driverDTO);
}
