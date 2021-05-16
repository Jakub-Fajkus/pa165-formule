package cz.muni.pa165.teamwhite.formula1.rest.controller;

import cz.muni.pa165.teamwhite.formula1.facade.CarFacade;
import cz.muni.pa165.teamwhite.formula1.rest.ApiUris;
import cz.muni.pa165.teamwhite.formula1.rest.ResponseStatuses;
import cz.muni.pa165.teamwhite.formula1.rest.RestResponse;
import cz.muni.pa165.teamwhite.formula1.rest.dto.CarAPIDTO;
import cz.muni.pa165.teamwhite.formula1.rest.dto.ComponentAPIDTO;
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

@Api(value = ApiUris.ROOT_URI_CARS)
@RequestMapping(ApiUris.ROOT_URI)
@RestController
@RolesAllowed(Role.ROLE_MANAGER)
public class CarController {
    @Autowired
    private CarFacade carFacade;

    @Autowired
    private BeanMappingService dozer;

    @ApiOperation(value = "Get information about all cars")
    @GetMapping(value = ApiUris.ROOT_URI_CARS, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<List<CarAPIDTO>> getAllCars() {
        return new RestResponse<>(dozer.mapTo(carFacade.getAllCars(), CarAPIDTO.class), ResponseStatuses.OK);
    }

    @ApiOperation(value = "Get information about a car with given id")
    @GetMapping(value = ApiUris.ROOT_URI_CAR, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<CarAPIDTO> getCar(@ApiParam(value = "The id of a car") @PathVariable Long id) {
        return new RestResponse<>(dozer.mapTo(carFacade.getCarById(id), CarAPIDTO.class), ResponseStatuses.OK);
    }


    @ApiOperation(value = "Get information about all components of a car with given id")
    @GetMapping(value = ApiUris.ROOT_URI_CAR_COMPONENTS, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<List<ComponentAPIDTO>> getComponentsForCar(@ApiParam(value = "The id of a car") @PathVariable Long id) {
        return new RestResponse<>(dozer.mapTo(carFacade.getCarById(id).getComponents(), ComponentAPIDTO.class), ResponseStatuses.OK);
    }

}
