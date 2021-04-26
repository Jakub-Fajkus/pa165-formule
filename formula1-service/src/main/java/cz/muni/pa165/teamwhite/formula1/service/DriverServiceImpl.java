package cz.muni.pa165.teamwhite.formula1.service;

import cz.muni.pa165.teamwhite.formula1.persistence.dao.DriverDao;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Driver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Karolina Hecova
 */
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverDao driverDao;

    @Override
    public Long createDriver(Driver driver) {
        driverDao.create(driver);
        return driver.getId();
    }

    @Override
    public Driver findById(Long id) {
        return driverDao.findById(id);
    }

    @Override
    public List<Driver> findAll() {
        return driverDao.findAll();
    }

    @Override
    public void update(Driver driver) {
        driverDao.update(driver);
    }

    @Override
    public void remove(Long id) {
        Driver dbDriver = driverDao.findById(id);

        if (dbDriver != null) {
            driverDao.remove(dbDriver);
        }
    }
}