package cz.muni.pa165.teamwhite.formula1.facade;


import cz.muni.pa165.teamwhite.formula1.dto.CarDTO;
import cz.muni.pa165.teamwhite.formula1.dto.ComponentDTO;
import cz.muni.pa165.teamwhite.formula1.dto.DriverDTO;

import java.util.List;


/**
 * @author Jakub Fajkus
 */
public interface CarFacade {

	/**
	 * Gets all cars
	 *
	 * @return list of all cars
	 */
	List<CarDTO> getAllCars();

	/**
	 * Creates a new car
	 *
	 * @param carDTO new car
	 * @return Id of the new car
	 */
	Long createCar(CarDTO carDTO);

	/**
	 * Deletes a car by its id
	 *
	 * @param carId Car id
	 */
	void deleteCar(Long carId);

	/**
	 * Gets car by its id
	 *
	 * @param carId Car id
	 * @return Car with given id
	 */
	CarDTO getCarById(Long carId);

}
