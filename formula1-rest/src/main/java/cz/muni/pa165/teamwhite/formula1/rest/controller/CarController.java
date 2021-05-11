package cz.muni.pa165.teamwhite.formula1.rest.controller;

import cz.muni.pa165.teamwhite.formula1.facade.CarFacade;
import cz.muni.pa165.teamwhite.formula1.rest.ApiUris;
import cz.muni.pa165.teamwhite.formula1.rest.ResponseStatuses;
import cz.muni.pa165.teamwhite.formula1.rest.RestResponse;
import cz.muni.pa165.teamwhite.formula1.rest.dto.CarAPIDTO;
import cz.muni.pa165.teamwhite.formula1.rest.dto.ComponentAPIDTO;
import cz.muni.pa165.teamwhite.formula1.service.mapping.BeanMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CarController {
    @Autowired
    private CarFacade carFacade;

    @Autowired
    BeanMappingService dozer;

    @GetMapping(ApiUris.ROOT_URI_CARS)
    public RestResponse<List<CarAPIDTO>> getAllCars() {
        return new RestResponse<>(dozer.mapTo(carFacade.getAllCars(), CarAPIDTO.class), ResponseStatuses.OK);
    }

    @GetMapping(value = ApiUris.ROOT_URI_CAR_COMPONENTS)
    public RestResponse<List<ComponentAPIDTO>> getComponentsForCar(@PathVariable Long id) {
        return new RestResponse<>(dozer.mapTo(carFacade.getCarById(id).getComponents(), ComponentAPIDTO.class), ResponseStatuses.OK);
    }

}
