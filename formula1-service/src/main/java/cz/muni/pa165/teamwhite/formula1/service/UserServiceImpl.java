package cz.muni.pa165.teamwhite.formula1.service;

import cz.muni.pa165.teamwhite.formula1.persistence.dao.UserDao;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.User;
import cz.muni.pa165.teamwhite.formula1.persistence.enums.Role;
import cz.muni.pa165.teamwhite.formula1.service.exception.Formula1ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jakub Fajkus
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder encoder;


    @Override
    public Long createUser(User user, String unencryptedPassword) {
        user.setPassword(encoder.encode(unencryptedPassword));

        try {
            userDao.create(user);
        } catch (DataAccessException e) {
            throw new Formula1ServiceException("Could not create a user: " + user, e);
        }

        return user.getId();
    }

    @Override
    public boolean authenticate(String login, String plaintextPassword) {
        User user = findByLogin(login);

        return user != null && encoder.matches(plaintextPassword, user.getPassword());
    }

    @Override
    public void changeUserPassword(User user, String plaintextPassword) {
        user.setPassword(encoder.encode(plaintextPassword));

        this.update(user);
    }

    @Override
    public boolean isManager(User user) {
        User dbUser = findById(user.getId());

        return dbUser != null && dbUser.getRole() == Role.MANAGER;
    }

    @Override
    public User findById(Long id) {
        try {
            return userDao.findById(id);
        } catch (DataAccessException e) {
            throw new Formula1ServiceException("Could not find user with id: " + id, e);
        }
    }

    @Override
    public User findByLogin(String login) {
        try {
            return userDao.findByLogin(login);
        } catch (DataAccessException e) {
            throw new Formula1ServiceException("Could not find user with login: " + login, e);
        }
    }

    @Override
    public List<User> findAll() {
        try {
            return userDao.findAll();
        } catch (DataAccessException e) {
            throw new Formula1ServiceException("Could not find all users", e);
        }
    }

    @Override
    public void update(User user) {
        try {
            userDao.update(user);
        } catch (DataAccessException e) {
            throw new Formula1ServiceException("Could not update user with login : " + user.getLogin(), e);
        }
    }

    @Override
    public void remove(Long id) {
        User dbUser = findById(id);

        try {
            if (dbUser != null) {
                userDao.remove(dbUser);
            }
        } catch (DataAccessException e) {
            throw new Formula1ServiceException("Could not delete user with id: " + id, e);
        }
    }
}
