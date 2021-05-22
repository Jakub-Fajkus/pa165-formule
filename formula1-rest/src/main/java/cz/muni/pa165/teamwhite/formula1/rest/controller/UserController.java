package cz.muni.pa165.teamwhite.formula1.rest.controller;

import cz.muni.pa165.teamwhite.formula1.dto.UserDTO;
import cz.muni.pa165.teamwhite.formula1.facade.UserFacade;
import cz.muni.pa165.teamwhite.formula1.rest.ApiUris;
import cz.muni.pa165.teamwhite.formula1.rest.ResponseStatuses;
import cz.muni.pa165.teamwhite.formula1.rest.RestResponse;
import cz.muni.pa165.teamwhite.formula1.rest.security.Role;
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

/**
 * @author Jiri Andrlik
 */
@Api(value = ApiUris.ROOT_URI_USERS)
@RequestMapping(ApiUris.ROOT_URI)
@RestController
@RolesAllowed(Role.ROLE_MANAGER)
public class UserController {
    @Autowired
    private UserFacade userFacade;

    @ApiOperation(value = "Get information about all users")
    @GetMapping(value = ApiUris.ROOT_URI_USERS, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<List<UserDTO>> getAllUsers() {
        return new RestResponse<>(userFacade.getAllUsers(), ResponseStatuses.OK);
    }

    @ApiOperation(value = "Get information about a user with given id")
    @GetMapping(value = ApiUris.ROOT_URI_USER, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<UserDTO> getComponent(@ApiParam(value = "The id of a user") @PathVariable Long id) {
        return new RestResponse<>(userFacade.getUserById(id), ResponseStatuses.OK);
    }

}
