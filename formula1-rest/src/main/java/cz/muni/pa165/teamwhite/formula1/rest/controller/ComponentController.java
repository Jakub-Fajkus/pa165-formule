package cz.muni.pa165.teamwhite.formula1.rest.controller;

import cz.muni.pa165.teamwhite.formula1.facade.ComponentFacade;
import cz.muni.pa165.teamwhite.formula1.rest.ApiUris;
import cz.muni.pa165.teamwhite.formula1.rest.ResponseStatuses;
import cz.muni.pa165.teamwhite.formula1.rest.RestResponse;
import cz.muni.pa165.teamwhite.formula1.rest.dto.CarAPIDTO;
import cz.muni.pa165.teamwhite.formula1.rest.dto.ComponentAPIDTO;
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

import java.util.List;

@Api(value = ApiUris.ROOT_URI_COMPONENTS)
@RequestMapping(ApiUris.ROOT_URI)
@RestController
public class ComponentController {
    @Autowired
    private ComponentFacade componentFacade;

    @Autowired
    private BeanMappingService dozer;

    @ApiOperation(value = "Get information about all components")
    @GetMapping(value = ApiUris.ROOT_URI_COMPONENTS, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<List<ComponentAPIDTO>> getAllComponents() {
        return new RestResponse<>(dozer.mapTo(componentFacade.getAllComponents(), ComponentAPIDTO.class), ResponseStatuses.OK);
    }

    @ApiOperation(value = "Get information about a component with given id")
    @GetMapping(value = ApiUris.ROOT_URI_COMPONENT, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<ComponentAPIDTO> getComponent(@ApiParam(value = "The id of a component") @PathVariable Long id) {
        return new RestResponse<>(dozer.mapTo(componentFacade.getComponentById(id), ComponentAPIDTO.class), ResponseStatuses.OK);
    }

}
