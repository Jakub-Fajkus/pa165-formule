package cz.muni.pa165.teamwhite.formula1.service;

import cz.muni.pa165.teamwhite.formula1.persistence.dao.CarDao;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Car;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Component;
import cz.muni.pa165.teamwhite.formula1.service.exception.EntityNotFoundException;
import cz.muni.pa165.teamwhite.formula1.service.exception.Formula1ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Karolina Hecova
 */
@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarDao carDao;

    @Autowired
    private DriverService driverService;

    @Override
    public Long createCar(Car car) {
        try {
            for (Component component: car.getComponents()) {
                component.setCar(car);
            }

            if (car.getDriver() != null) {
                car.getDriver().setCar(car);
                if (car.getDriver().getId() != null) {
                    driverService.update(car.getDriver());
                } else {
                    driverService.createDriver(car.getDriver());
                }
            }

            carDao.create(car);
        } catch (DataAccessException e) {
            throw new Formula1ServiceException("Could not create a car: " + car, e);
        }
        return car.getId();
    }

    @Override
    public List<Car> findAll() {
        try {
            return carDao.findAll();
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("Could not find a car", e);
        }
    }

    @Override
    public Car findById(Long id) {
        try {
            return carDao.findById(id);
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("Could not find a car with id: " + id, e);
        }
    }

    @Override
    public void update(Car car) {
        try {
            for (Component component: car.getComponents()) {
                component.setCar(car);
            }

            carDao.update(car);
        } catch (DataAccessException e) {
            throw new Formula1ServiceException("Could not update a car: " + car, e);
        }
    }

    @Override
    public void remove(Long id) {
        Car dbCar = findById(id);
        try {
            if (dbCar != null) {
                carDao.remove(dbCar);
            }
        } catch (DataAccessException e) {
            throw new Formula1ServiceException("Could not delete a car with id: " + id, e);
        }
    }
}
