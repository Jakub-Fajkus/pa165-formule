package cz.muni.pa165.teamwhite.formula1.service.facade;

import cz.muni.pa165.teamwhite.formula1.dto.DriverDTO;
import cz.muni.pa165.teamwhite.formula1.facade.DriverFacade;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Driver;
import cz.muni.pa165.teamwhite.formula1.service.CarService;
import cz.muni.pa165.teamwhite.formula1.service.DriverService;
import cz.muni.pa165.teamwhite.formula1.service.mapping.BeanMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Jiri Andrlik
 */
@Transactional
@Service
public class DriverFacadeImpl implements DriverFacade {
    @Autowired
    private DriverService driverService;

    @Autowired
    private CarService carService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public List<DriverDTO> getAllDrivers() {
        return beanMappingService.mapTo(driverService.findAll(), DriverDTO.class);
    }

    @Override
    public Long createDriver(DriverDTO driverDTO) {
        return driverService.createDriver(beanMappingService.mapTo(driverDTO, Driver.class));
    }

    @Override
    public void deleteDriver(Long driverId) {
        driverService.remove(driverId);
    }

    @Override
    public DriverDTO getDriverById(Long driverId) {
        return beanMappingService.mapTo(driverService.findById(driverId), DriverDTO.class);
    }

    @Override
    public DriverDTO update(@NotNull DriverDTO driverDTO) {
        Driver dbDriver = driverService.findById(driverDTO.getId());

        beanMappingService.mapToObject(driverDTO, dbDriver);
        driverService.update(dbDriver);

        return getDriverById(driverDTO.getId());
    }
}
