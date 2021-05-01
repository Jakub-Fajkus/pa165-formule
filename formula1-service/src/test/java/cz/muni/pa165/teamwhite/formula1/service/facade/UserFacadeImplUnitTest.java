package cz.muni.pa165.teamwhite.formula1.service.facade;

import cz.muni.pa165.teamwhite.formula1.dto.UserAuthenticateDTO;
import cz.muni.pa165.teamwhite.formula1.dto.UserDTO;
import cz.muni.pa165.teamwhite.formula1.enums.Role;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.User;
import cz.muni.pa165.teamwhite.formula1.service.ServiceConfiguration;
import cz.muni.pa165.teamwhite.formula1.service.UserService;
import cz.muni.pa165.teamwhite.formula1.service.mapping.BeanMappingService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jakub Fajkus
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserFacadeImplUnitTest extends AbstractTransactionalTestNGSpringContextTests {
    @Mock
    private UserService userService;

    @Mock
    private BeanMappingService beanMappingService;

    @InjectMocks
    private final UserFacadeImpl userFacade = new UserFacadeImpl();

    private final User userManager = new User("manager", "hash", cz.muni.pa165.teamwhite.formula1.persistence.enums.Role.MANAGER);
    private final User userBFU = new User("pepa", "hash", cz.muni.pa165.teamwhite.formula1.persistence.enums.Role.ENGINEER);

    private final UserDTO userDTOManager = new UserDTO("manager", "hash", Role.MANAGER);
    private final UserDTO userDTOBFU = new UserDTO("pepa", "hash", Role.ENGINEER);

    List<User> userList = List.of(userManager, userBFU);
    List<UserDTO> userDTOList = List.of(userDTOManager, userDTOBFU);

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testGetAllUsers() {
        when(userService.findAll()).thenReturn(userList);
        when(beanMappingService.mapTo(userList, UserDTO.class)).thenReturn(userDTOList);

        Assert.assertSame(userFacade.getAllUsers(), userDTOList);
    }

    @Test
    public void testCreateUser() {
        when(beanMappingService.mapTo(userDTOManager, User.class)).thenReturn(userManager);
        when(userService.createUser(userManager, "pass")).thenReturn(42L);

        Assert.assertEquals((long) userFacade.createUser(userDTOManager, "pass"), 42L);
    }

    @Test
    public void testDeleteUser() {

        userFacade.deleteUser(42L);

        verify(userService).remove(42L);
    }

    @Test
    public void testGetUserById() {
        when(userService.findById(userManager.getId())).thenReturn(userManager);
        when(beanMappingService.mapTo(userManager, UserDTO.class)).thenReturn(userDTOManager);

        Assert.assertEquals(userFacade.getUserById(userManager.getId()), userDTOManager);
    }

    @Test
    public void testAuthenticate() {
        UserAuthenticateDTO authenticateDTO = new UserAuthenticateDTO(userManager.getLogin(), "pass");
        when(userService.authenticate(authenticateDTO.getLogin(), authenticateDTO.getPassword())).thenReturn(true);

        Assert.assertTrue(userFacade.authenticate(authenticateDTO));
    }

    @Test
    public void testIsManager() {
        when(beanMappingService.mapTo(userDTOManager, User.class)).thenReturn(userManager);

        when(userService.isManager(userManager)).thenReturn(true);

        Assert.assertTrue(userFacade.isManager(userDTOManager));
    }
}