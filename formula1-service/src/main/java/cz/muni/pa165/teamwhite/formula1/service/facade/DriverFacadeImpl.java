package cz.muni.pa165.teamwhite.formula1.service.facade;

import cz.muni.pa165.teamwhite.formula1.dto.DriverDTO;
import cz.muni.pa165.teamwhite.formula1.facade.DriverFacade;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Driver;
import cz.muni.pa165.teamwhite.formula1.service.DriverService;
import cz.muni.pa165.teamwhite.formula1.service.mapping.BeanMappingService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Karolina Hecova
 */
@Transactional
public class DriverFacadeImpl implements DriverFacade {

    @Autowired
    private DriverService driverService;

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
}
