package cz.muni.pa165.teamwhite.formula1.rest.controller;

import cz.muni.pa165.teamwhite.formula1.facade.DriverFacade;
import cz.muni.pa165.teamwhite.formula1.rest.ApiUris;
import cz.muni.pa165.teamwhite.formula1.rest.ResponseStatuses;
import cz.muni.pa165.teamwhite.formula1.rest.RestResponse;
import cz.muni.pa165.teamwhite.formula1.rest.dto.DriverAPIDTO;
import cz.muni.pa165.teamwhite.formula1.rest.security.Role;
import cz.muni.pa165.teamwhite.formula1.service.mapping.BeanMappingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private BeanMappingService dozer;

    @ApiOperation(value = "Get information about all drivers")
    @GetMapping(value = ApiUris.ROOT_URI_DRIVERS, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<List<DriverAPIDTO>> getAllDrivers() {
        return new RestResponse<>(dozer.mapTo(driverFacade.getAllDrivers(), DriverAPIDTO.class), ResponseStatuses.OK);
    }

    @ApiOperation(value = "Get information about a driver with given id")
    @GetMapping(value = ApiUris.ROOT_URI_DRIVER, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<DriverAPIDTO> getComponent(@ApiParam(value = "The id of a driver") @PathVariable Long id) {
        return new RestResponse<>(dozer.mapTo(driverFacade.getDriverById(id), DriverAPIDTO.class), ResponseStatuses.OK);
    }
}
