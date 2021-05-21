package cz.muni.pa165.teamwhite.formula1.rest.controller;

import cz.muni.pa165.teamwhite.formula1.dto.CarDTO;
import cz.muni.pa165.teamwhite.formula1.dto.DriverDTO;
import cz.muni.pa165.teamwhite.formula1.facade.CarFacade;
import cz.muni.pa165.teamwhite.formula1.facade.DriverFacade;
import cz.muni.pa165.teamwhite.formula1.rest.ApiUris;
import cz.muni.pa165.teamwhite.formula1.rest.ResponseStatuses;
import cz.muni.pa165.teamwhite.formula1.rest.RestResponse;
import cz.muni.pa165.teamwhite.formula1.rest.dto.DriverAPIDTO;
import cz.muni.pa165.teamwhite.formula1.rest.dto.UpdateDriverAPIDTO;
import cz.muni.pa165.teamwhite.formula1.rest.security.Role;
import cz.muni.pa165.teamwhite.formula1.service.mapping.BeanMappingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Api(value = ApiUris.ROOT_URI_DRIVERS)
@RequestMapping(ApiUris.ROOT_URI)
@RestController
@RolesAllowed(Role.ROLE_MANAGER)
public class DriverController {
    @Autowired
    private DriverFacade driverFacade;

    @Autowired
    private CarFacade carFacade;

    @Autowired
    private BeanMappingService dozer;

    @ApiOperation(value = "Creates new driver.")
    @RequestMapping(value = ApiUris.ROOT_URI_DRIVERS, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<DriverAPIDTO> createDriver(@RequestBody DriverAPIDTO driver) {
        Long id = driverFacade.createDriver(new DriverDTO(null, driver.getName(), driver.getSurname(), driver.getNationality(), driver.isAggressive(), driver.getWetDriving(), driver.getReactions()));
        return getDriver(id);
    }

    @ApiOperation(value = "Get information about all drivers")
    @GetMapping(value = ApiUris.ROOT_URI_DRIVERS, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<List<DriverAPIDTO>> getAllDrivers() {
        return new RestResponse<>(dozer.mapTo(driverFacade.getAllDrivers(), DriverAPIDTO.class), ResponseStatuses.OK);
    }

    @ApiOperation(value = "Get information about a driver with given id")
    @GetMapping(value = ApiUris.ROOT_URI_DRIVER, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<DriverAPIDTO> getDriver(@ApiParam(value = "The id of a driver") @PathVariable Long id) {
        return new RestResponse<>(dozer.mapTo(driverFacade.getDriverById(id), DriverAPIDTO.class), ResponseStatuses.OK);
    }

    @ApiOperation(value = "Update information about given driver")
    @PatchMapping(value = ApiUris.ROOT_URI_DRIVER, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<DriverAPIDTO> updateDriver(@ApiParam(value = "The id of a driver") @PathVariable Long id, @RequestBody UpdateDriverAPIDTO driver) {
        CarDTO carDTO = driver.getCar() == null ? null : carFacade.getCarById(driver.getCar());

        driverFacade.update(new DriverDTO(id, carDTO, driver.getName(), driver.getSurname(), driver.getNationality(), driver.getAggressive(), driver.getWetDriving(), driver.getReactions()));

        return getDriver(id);
    }

    @RequestMapping(value = ApiUris.ROOT_URI_DRIVER, method = RequestMethod.DELETE)
    public void removeDriver(@ApiParam(value = "The id of a driver") @PathVariable Long id) {
        driverFacade.deleteDriver(id);
    }
}
