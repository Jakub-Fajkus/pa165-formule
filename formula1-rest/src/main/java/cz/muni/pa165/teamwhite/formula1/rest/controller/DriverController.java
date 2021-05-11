package cz.muni.pa165.teamwhite.formula1.rest.controller;

import cz.muni.pa165.teamwhite.formula1.facade.DriverFacade;
import cz.muni.pa165.teamwhite.formula1.rest.ApiUris;
import cz.muni.pa165.teamwhite.formula1.rest.ResponseStatuses;
import cz.muni.pa165.teamwhite.formula1.rest.RestResponse;
import cz.muni.pa165.teamwhite.formula1.rest.dto.DriverAPIDTO;
import cz.muni.pa165.teamwhite.formula1.service.mapping.BeanMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DriverController {
    @Autowired
    private DriverFacade driverFacade;

    @Autowired
    private BeanMappingService dozer;


    @GetMapping(ApiUris.ROOT_URI_DRIVERS)
    public RestResponse<List<DriverAPIDTO>> getAllDrivers() {
        return new RestResponse<>(dozer.mapTo(driverFacade.getAllDrivers(), DriverAPIDTO.class), ResponseStatuses.OK);
    }
}
