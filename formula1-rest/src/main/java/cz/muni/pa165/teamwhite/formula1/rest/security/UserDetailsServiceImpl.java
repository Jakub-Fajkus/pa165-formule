package cz.muni.pa165.teamwhite.formula1.rest.security;

import cz.muni.pa165.teamwhite.formula1.dto.UserDTO;
import cz.muni.pa165.teamwhite.formula1.facade.UserFacade;
import cz.muni.pa165.teamwhite.formula1.service.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


/**
 * @author Jakub Fajkus
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserFacade userFacade;
    final static Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        try {
            UserDTO user = userFacade.getUserByLogin(login);
            log.debug("Found user: " + user.getLogin());
            return new UserDetailsImpl(user);
        } catch (EntityNotFoundException e) {
            throw new UsernameNotFoundException("Username " + login + " was not found");
        }

    }
}
