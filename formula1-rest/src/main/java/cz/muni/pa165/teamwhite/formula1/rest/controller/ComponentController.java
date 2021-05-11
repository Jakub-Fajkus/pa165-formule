package cz.muni.pa165.teamwhite.formula1.rest.controller;

import cz.muni.pa165.teamwhite.formula1.facade.ComponentFacade;
import cz.muni.pa165.teamwhite.formula1.rest.ApiUris;
import cz.muni.pa165.teamwhite.formula1.rest.ResponseStatuses;
import cz.muni.pa165.teamwhite.formula1.rest.RestResponse;
import cz.muni.pa165.teamwhite.formula1.rest.dto.ComponentAPIDTO;
import cz.muni.pa165.teamwhite.formula1.service.mapping.BeanMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ComponentController {
    @Autowired
    private ComponentFacade componentFacade;

    @Autowired
    private BeanMappingService dozer;

    @GetMapping(ApiUris.ROOT_URI_COMPONENTS)
    public RestResponse<List<ComponentAPIDTO>> getAllComponents() {
        return new RestResponse<>(dozer.mapTo(componentFacade.getAllComponents(), ComponentAPIDTO.class), ResponseStatuses.OK);
    }
}
