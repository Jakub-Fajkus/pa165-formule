package cz.muni.pa165.teamwhite.formula1.facade;


import cz.muni.pa165.teamwhite.formula1.dto.CarDTO;

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

	/**
	 * Updates given car and returns a new dto with updated car data
	 *
	 * @param carDTO carDTO containing only fields that we want to change
	 * @return A new DTO with all fields
	 */
	CarDTO update(CarDTO carDTO);

}
