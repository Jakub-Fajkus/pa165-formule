package cz.muni.pa165.teamwhite.formula1.service.facade;

import cz.muni.pa165.teamwhite.formula1.dto.CarDTO;
import cz.muni.pa165.teamwhite.formula1.dto.ComponentDTO;
import cz.muni.pa165.teamwhite.formula1.dto.DriverDTO;
import cz.muni.pa165.teamwhite.formula1.facade.CarFacade;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Car;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Component;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Driver;
import cz.muni.pa165.teamwhite.formula1.service.CarService;
import cz.muni.pa165.teamwhite.formula1.service.mapping.BeanMappingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Karolina Hecova
 */
public class CarFacadeImpl implements CarFacade {

    @Autowired
    private CarService carService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public List<CarDTO> getAllCars() {
        return beanMappingService.mapTo(carService.findAll(), CarDTO.class);
    }

    @Override
    public Long createCar(CarDTO carDTO) {
        return carService.createCar(beanMappingService.mapTo(carDTO, Car.class));
    }

    @Override
    public void deleteCar(Long carId) {
        carService.remove(carId);
    }

    @Override
    public CarDTO getCarById(Long carId) {
        return beanMappingService.mapTo(carService.findById(carId), CarDTO.class);
    }

    @Override
    public void setDriver(CarDTO car, DriverDTO driver) {
        carService.setDriver(beanMappingService.mapTo(car, Car.class), beanMappingService.mapTo(driver, Driver.class));
    }

    @Override
    public void addComponent(CarDTO car, ComponentDTO component) {
        carService.addComponent(beanMappingService.mapTo(car, Car.class), beanMappingService.mapTo(component, Component.class));
    }

    @Override
    public void removeComponent(CarDTO car, ComponentDTO component) {
        carService.removeComponent(beanMappingService.mapTo(car, Car.class), beanMappingService.mapTo(component, Component.class));
    }
}
