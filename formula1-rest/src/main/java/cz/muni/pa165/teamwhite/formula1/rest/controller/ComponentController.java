package cz.muni.pa165.teamwhite.formula1.rest.controller;

import cz.muni.pa165.teamwhite.formula1.dto.CarDTO;
import cz.muni.pa165.teamwhite.formula1.dto.ComponentDTO;
import cz.muni.pa165.teamwhite.formula1.facade.CarFacade;
import cz.muni.pa165.teamwhite.formula1.facade.ComponentFacade;
import cz.muni.pa165.teamwhite.formula1.rest.ApiUris;
import cz.muni.pa165.teamwhite.formula1.rest.ResponseStatuses;
import cz.muni.pa165.teamwhite.formula1.rest.RestResponse;
import cz.muni.pa165.teamwhite.formula1.rest.dto.ComponentAPIDTO;
import cz.muni.pa165.teamwhite.formula1.rest.dto.UpdateComponentAPIDTO;
import cz.muni.pa165.teamwhite.formula1.rest.security.Role;
import cz.muni.pa165.teamwhite.formula1.service.mapping.BeanMappingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Api(value = ApiUris.ROOT_URI_COMPONENTS)
@RequestMapping(ApiUris.ROOT_URI)
@RestController
public class ComponentController {
    @Autowired
    private ComponentFacade componentFacade;

    @Autowired
    private CarFacade carFacade;

    @Autowired
    private BeanMappingService dozer;

    @ApiOperation(value = "Get information about all components")
    @GetMapping(value = ApiUris.ROOT_URI_COMPONENTS, produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed({Role.ROLE_MANAGER, Role.ROLE_ENGINEER})
    public RestResponse<List<ComponentAPIDTO>> getAllComponents() {
        return new RestResponse<>(dozer.mapTo(componentFacade.getAllComponents(), ComponentAPIDTO.class), ResponseStatuses.OK);
    }

    @ApiOperation(value = "Get information about a component with given id")
    @GetMapping(value = ApiUris.ROOT_URI_COMPONENT, produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed({Role.ROLE_MANAGER, Role.ROLE_ENGINEER})
    public RestResponse<ComponentAPIDTO> getComponent(@ApiParam(value = "The id of a component") @PathVariable Long id) {
        return new RestResponse<>(dozer.mapTo(componentFacade.getComponentById(id), ComponentAPIDTO.class), ResponseStatuses.OK);
    }

    @ApiOperation(value = "Create new component")
    @PostMapping(value = ApiUris.ROOT_URI_COMPONENTS, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed({Role.ROLE_MANAGER, Role.ROLE_ENGINEER})
    public RestResponse<ComponentAPIDTO> createComponent(@RequestBody ComponentAPIDTO component) {
        Long id = componentFacade.createComponent(new ComponentDTO(component.getType(), component.getName(), null));

        return getComponent(id);
    }

    @ApiOperation(value = "Update information about given component")
    @PatchMapping(value = ApiUris.ROOT_URI_COMPONENT, produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed({Role.ROLE_MANAGER, Role.ROLE_ENGINEER})
    public RestResponse<ComponentAPIDTO> updateComponent(@ApiParam(value = "Id of a component for edit") @PathVariable Long id, @RequestBody UpdateComponentAPIDTO component) {
        CarDTO carDTO = component.getCar() == null ? null : carFacade.getCarById(component.getCar());

        componentFacade.update(new ComponentDTO(id, component.getType(), component.getName(), carDTO));

        return getComponent(id);
    }
}
