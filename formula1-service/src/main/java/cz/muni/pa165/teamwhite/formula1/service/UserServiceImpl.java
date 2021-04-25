package cz.muni.pa165.teamwhite.formula1.service;

import cz.muni.pa165.teamwhite.formula1.persistence.dao.UserDao;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.User;
import cz.muni.pa165.teamwhite.formula1.persistence.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * @author Jakub Fajkus
 */
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder encoder;


    @Override
    public Long createUser(User user, String unencryptedPassword) {
        user.setPassword(encoder.encode(unencryptedPassword));

        userDao.create(user);

        return user.getId();
    }

    @Override
    public boolean authenticate(String login, String plaintextPassword) {
        User user = userDao.findByLogin(login);

        return user != null && encoder.matches(plaintextPassword, user.getPassword());
    }

    @Override
    public boolean isManager(User user) {
        User dbUser = userDao.findById(user.getId());

        return dbUser != null && dbUser.getRole() == Role.MANAGER;
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void remove(Long id) {
        User dbUser = userDao.findById(id);

        if (dbUser != null) {
            userDao.remove(dbUser);
        }
    }
}
