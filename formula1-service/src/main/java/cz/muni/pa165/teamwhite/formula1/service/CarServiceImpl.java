package cz.muni.pa165.teamwhite.formula1.service;

import cz.muni.pa165.teamwhite.formula1.persistence.dao.CarDao;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Car;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Component;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Driver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Karolina Hecova
 */
public class CarServiceImpl implements CarService {
    @Autowired
    private CarDao carDao;

    @Override
    public Long createCar(Car car) {
        carDao.create(car);
        return car.getId();
    }

    @Override
    public List<Car> findAll() {
        return carDao.findAll();
    }

    @Override
    public Car findById(Long id) {
        return carDao.findById(id);
    }

    @Override
    public void update(Car car) {
        carDao.update(car);
    }

    @Override
    public void remove(Long id) {
        Car dbCar = carDao.findById(id);

        if (dbCar != null) {
            carDao.remove(dbCar);
        }
    }

    @Override
    public void setDriver(Car car, Driver driver) {
        car.setDriver(driver);
        carDao.update(car);
    }

    @Override
    public void addComponent(Car car, Component component) {
        car.addComponent(component);
        carDao.update(car);
    }

    @Override
    public void removeComponent(Car car, Component component) {
        car.removeComponent(component);
        carDao.update(car);
    }
}
