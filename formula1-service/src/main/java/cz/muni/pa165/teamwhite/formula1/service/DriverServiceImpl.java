package cz.muni.pa165.teamwhite.formula1.service;

import cz.muni.pa165.teamwhite.formula1.persistence.dao.DriverDao;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Driver;
import cz.muni.pa165.teamwhite.formula1.service.exception.Formula1ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @author Jiri Andrlik
 */
public class DriverServiceImpl implements DriverService{
    @Autowired
    private DriverDao driverDao;

    @Override
    public Driver findById(Long id) {
        try {
            return driverDao.findById(id);
        } catch (DataAccessException e) {
            throw new Formula1ServiceException("Could not find driver by Id " + id + ".", e);
        }
    }

    @Override
    public List<Driver> findAll() {
        try {
            return driverDao.findAll();
        } catch (DataAccessException e) {
            throw new Formula1ServiceException("Could not list all drivers.", e);
        }
    }

    @Override
    public Long createDriver(Driver driver) {
        try {
            driverDao.create(driver);
        } catch (DataAccessException e) {
            throw new Formula1ServiceException("Could not create a driver " + driver + ".", e);
        }
        return driver.getId();
    }

    @Override
    public void update(Driver driver) {
        try {
            driverDao.update(driver);
        } catch (DataAccessException e) {
            throw new Formula1ServiceException("Could not update a driver " + driver + ".", e);
        }
    }

    @Override
    public void remove(Long id) {
        Driver dbDriver = findById(id);
        if (dbDriver != null) {
            try {
                driverDao.remove(dbDriver);
            } catch (DataAccessException e) {
                throw new Formula1ServiceException("Could not remove the driver with id" + id + ".", e);
            }
        }
    }
}
