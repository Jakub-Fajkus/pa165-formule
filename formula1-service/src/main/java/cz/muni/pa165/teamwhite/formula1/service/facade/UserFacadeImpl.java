package cz.muni.pa165.teamwhite.formula1.service.facade;

import cz.muni.pa165.teamwhite.formula1.dto.UserAuthenticateDTO;
import cz.muni.pa165.teamwhite.formula1.dto.UserDTO;
import cz.muni.pa165.teamwhite.formula1.facade.UserFacade;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.User;
import cz.muni.pa165.teamwhite.formula1.service.UserService;
import cz.muni.pa165.teamwhite.formula1.service.exception.EntityNotFoundException;
import cz.muni.pa165.teamwhite.formula1.service.mapping.BeanMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Jakub Fajkus
 */
@Transactional
@Service
public class UserFacadeImpl implements UserFacade {
    @Autowired
    private UserService userService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public List<UserDTO> getAllUsers() {
        return beanMappingService.mapTo(userService.findAll(), UserDTO.class);
    }

    @Override
    public Long createUser(UserDTO userDTO, String unencryptedPassword) {
        return userService.createUser(beanMappingService.mapTo(userDTO, User.class), unencryptedPassword);
    }

    @Override
    public void deleteUser(Long userId) {
        userService.remove(userId);
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userService.findById(userId);

        if (user == null) {
            throw new EntityNotFoundException("User with id " + userId + " was not found");
        }

        return beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public UserDTO getUserByLogin(String login) {
        User user = userService.findByLogin(login);

        if (user == null) {
            throw new EntityNotFoundException("User with login " + login + " was not found");
        }

        return beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public boolean authenticate(UserAuthenticateDTO user) {
        return userService.authenticate(user.getLogin(), user.getPassword());
    }

    @Override
    public boolean isManager(UserDTO user) {
        return userService.isManager(beanMappingService.mapTo(user, User.class));
    }

    @Override
    public UserDTO update(@NotNull UserDTO userDTO) {
        User dbUser = userService.findById(userDTO.getId());

        beanMappingService.mapToObject(userDTO, dbUser);

        if (userDTO.getPassword() != null) {
            userService.changeUserPassword(dbUser, userDTO.getPassword());
        }

        userService.update(dbUser);

        return getUserById(userDTO.getId());
    }
}
