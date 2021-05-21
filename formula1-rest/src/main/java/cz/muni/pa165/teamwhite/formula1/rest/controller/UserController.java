package cz.muni.pa165.teamwhite.formula1.rest.controller;

import cz.muni.pa165.teamwhite.formula1.dto.UserDTO;
import cz.muni.pa165.teamwhite.formula1.facade.UserFacade;
import cz.muni.pa165.teamwhite.formula1.rest.ApiUris;
import cz.muni.pa165.teamwhite.formula1.rest.ResponseStatuses;
import cz.muni.pa165.teamwhite.formula1.rest.RestResponse;
import cz.muni.pa165.teamwhite.formula1.rest.dto.UpdateUserAPIDTO;
import cz.muni.pa165.teamwhite.formula1.rest.dto.UserAPIDTO;
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

@Api(value = ApiUris.ROOT_URI_USERS)
@RequestMapping(ApiUris.ROOT_URI)
@RestController
@RolesAllowed(Role.ROLE_MANAGER)
public class UserController {
    @Autowired
    private UserFacade userFacade;

    @Autowired
    private BeanMappingService dozer;

    @ApiOperation(value = "Get information about all users")
    @GetMapping(value = ApiUris.ROOT_URI_USERS, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<List<UserDTO>> getAllUsers() {
        return new RestResponse<>(userFacade.getAllUsers(), ResponseStatuses.OK);
    }

    @ApiOperation(value = "Get information about a user with given id")
    @GetMapping(value = ApiUris.ROOT_URI_USER, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<UserAPIDTO> getUser(@ApiParam(value = "The id of a user") @PathVariable Long id) {
        return new RestResponse<>(dozer.mapTo(userFacade.getUserById(id), UserAPIDTO.class), ResponseStatuses.OK);
    }

    @ApiOperation(value = "Update information about given user")
    @PatchMapping(value = ApiUris.ROOT_URI_USER, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<UserAPIDTO> updateUser(@ApiParam(value = "The id of a user") @PathVariable Long id, @RequestBody UpdateUserAPIDTO user) {
        userFacade.update(new UserDTO(id, user.getLogin(), null, user.getRole()));
        return getUser(id);
    }

    @ApiOperation(value = "Change a password for the given user")
    @PatchMapping(value = ApiUris.ROOT_URI_PASSWORD, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<UserAPIDTO> changeUserPassword(@ApiParam(value = "The id of a user") @PathVariable Long id, @RequestBody UpdateUserAPIDTO user) {
        userFacade.update(new UserDTO(id, null, user.getPassword(), null));
        return getUser(id);
    }

    //todo remove
}
