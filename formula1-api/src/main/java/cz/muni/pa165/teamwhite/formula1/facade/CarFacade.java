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

	/**
	 * Sets a new main driver for the car.
	 *
	 * The previous driver will be removed from the car.
	 *
	 * @param car
	 * @param driver The new driver
	 */
	void setDriver(CarDTO car, DriverDTO driver);

	/**
	 * Adds a new component to the car.
	 *
	 * If the car already contains a component of of the same type, it will be replaced with the new component.
	 *
	 * @param car
	 * @param component
	 */
	void addComponent(CarDTO car, ComponentDTO component);

	/**
	 * Removes component from the car.
	 *
	 * If the component is not in the car, no action is performed.
	 *
	 * @param car
	 * @param component
	 */
	void removeComponent(CarDTO car, ComponentDTO component);

}
