package cz.muni.pa165.teamwhite.formula1.dao;

import cz.muni.pa165.teamwhite.formula1.entity.User;
import java.util.List;


/**
 * @author Tomas Sedlacek
 */
public interface UserDao {

    /**
     * This method create new user in database
     * @param user - user to be added to DB
     */
    void create(User user);

    /**
     * This method finds user in DB by entered id
     * @param id - id of the requested user
     * @return found user
     */
    User findById(Long id);

    /**
     * This method finds user in DB by entered login
     * @param login - login of the requested user
     * @return found yser
     */
    User findByLogin(String login);

    /**
     * This method finds user in DB by entered login
     * @return list of all users
     */
    List<User> findAll();

    /**
     * This method updates user already stored in DB
     * @param  user to update
     */
    void update(User user);

    /**
     * This method removes user from DB
     * @param  user to be removed
     */
    void remove(User user);

}
