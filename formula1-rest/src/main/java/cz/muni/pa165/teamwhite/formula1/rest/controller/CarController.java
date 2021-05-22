package cz.muni.pa165.teamwhite.formula1.rest.controller;

import cz.muni.pa165.teamwhite.formula1.dto.CarDTO;
import cz.muni.pa165.teamwhite.formula1.dto.ComponentDTO;
import cz.muni.pa165.teamwhite.formula1.dto.DriverDTO;
import cz.muni.pa165.teamwhite.formula1.facade.CarFacade;
import cz.muni.pa165.teamwhite.formula1.facade.ComponentFacade;
import cz.muni.pa165.teamwhite.formula1.facade.DriverFacade;
import cz.muni.pa165.teamwhite.formula1.rest.ApiUris;
import cz.muni.pa165.teamwhite.formula1.rest.ResponseStatuses;
import cz.muni.pa165.teamwhite.formula1.rest.RestResponse;
import cz.muni.pa165.teamwhite.formula1.rest.dto.CarAPIDTO;
import cz.muni.pa165.teamwhite.formula1.rest.dto.ComponentAPIDTO;
import cz.muni.pa165.teamwhite.formula1.rest.dto.UpdateCarAPIDTO;
import cz.muni.pa165.teamwhite.formula1.rest.security.Role;
import cz.muni.pa165.teamwhite.formula1.service.mapping.BeanMappingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.RolesAllowed;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Api(value = ApiUris.ROOT_URI_CARS)
@RequestMapping(ApiUris.ROOT_URI)
@RestController
@RolesAllowed(Role.ROLE_MANAGER)
public class CarController {
    @Autowired
    private CarFacade carFacade;

    @Autowired
    private DriverFacade driverFacade;

    @Autowired
    private ComponentFacade componentFacade;

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

    @ApiOperation(value = "Update information about given car")
    @PatchMapping(value = ApiUris.ROOT_URI_CAR, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<CarAPIDTO> updateCar(@ApiParam(value = "The id of a car") @PathVariable Long id, @RequestBody UpdateCarAPIDTO car) {
        DriverDTO driverDTO = car.getDriver() == null ? null : driverFacade.getDriverById(car.getDriver());

        carFacade.update(new CarDTO(id, car.getName(), driverDTO, null));

        return getCar(id);
    }

    @ApiOperation(value = "Replace components of car with given id")
    @PutMapping(value = ApiUris.ROOT_URI_CAR_COMPONENTS, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<List<ComponentAPIDTO>> replaceComponents(@ApiParam(value = "The id of a car") @PathVariable Long id, @RequestBody Long[] components) {
        Set<ComponentDTO> componentsList = new HashSet<>();
        for(Long componentId : components) {
            ComponentDTO foundComponent = componentFacade.getComponentById(componentId);

            if (foundComponent == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Component with id " + componentId + " not found");
            }

            componentsList.add(foundComponent);
        }

        carFacade.update(new CarDTO(id, null, null, componentsList));

        return getComponentsForCar(id);
    }
}
