package cz.muni.pa165.teamwhite.formula1.service.facade;

import cz.muni.pa165.teamwhite.formula1.dto.CarDTO;
import cz.muni.pa165.teamwhite.formula1.facade.CarFacade;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Car;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Component;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Driver;
import cz.muni.pa165.teamwhite.formula1.service.CarService;
import cz.muni.pa165.teamwhite.formula1.service.ComponentService;
import cz.muni.pa165.teamwhite.formula1.service.DriverService;
import cz.muni.pa165.teamwhite.formula1.service.exception.EntityNotFoundException;
import cz.muni.pa165.teamwhite.formula1.service.mapping.BeanMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Karolina Hecova
 */
@Transactional
@Service
public class CarFacadeImpl implements CarFacade {

    @Autowired
    private CarService carService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private ComponentService componentService;

    @Autowired
    private DriverService driverService;

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
        Car car = carService.findById(carId);

        if (car == null) {
            throw new EntityNotFoundException("Car with id " + carId + " was not found");
        }

        return beanMappingService.mapTo(car, CarDTO.class);
    }

    @Override
    public CarDTO update(@NotNull CarDTO carDTO) {
        Car dbCar = carService.findById(carDTO.getId());

        if (carDTO.getComponents() != null) {
            for (Component component: dbCar.getComponents()) {
                component.setCar(null);
                componentService.update(component);
            }
        }

        if (carDTO.getDriver() != null) {
            Driver dbDriver = driverService.findById(dbCar.getDriver().getId());

            dbDriver.setCar(null);
            driverService.update(dbDriver);
        }

        beanMappingService.mapToObject(carDTO, dbCar);
        carService.update(dbCar);

        return getCarById(carDTO.getId());
    }
}
