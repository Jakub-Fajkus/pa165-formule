package cz.muni.pa165.teamwhite.formula1.service;

import cz.muni.pa165.teamwhite.formula1.persistence.entity.User;

import java.util.List;

/**
 * @author Jakub Fajkus
 */
public interface UserService {
    /**
     * Creates new user with the given unencrypted password.
     * @param user new user to be created
     * @param unencryptedPassword unencrypted password
     * @return id of the new user
     */
    Long createUser(User user, String unencryptedPassword);


    /**
     * Try to authenticate a user. Return true only if the hashed password matches the records.
     * @param login user login to be authenticated
     * @param plaintextPassword plaintext password
     * @return true if the user was successfully authenticated, otherwise false
     */
    boolean authenticate(String login, String plaintextPassword);

    /**
     * Change user's password
     * @param user User that will have a new password
     * @param plaintextPassword The new password in plaintext
     */
    void changeUserPassword(User user, String plaintextPassword);


    /**
     * Check if the given user is manager.
     * @param user to be tested as manager
     * @return true if the given user is manager, otherwise false
     */
    boolean isManager(User user);

    /**
     * This method finds user in DB by entered id
     *
     * @param id - id of the requested user
     * @return found user
     */
    User findById(Long id);

    /**
     * This method finds user in DB by entered login
     *
     * @param login - login of the requested user
     * @return found user
     */
    User findByLogin(String login);

    /**
     * This method finds all users
     *
     * @return list of all users
     */
    List<User> findAll();

    /**
     * This method updates user already stored in DB
     *
     * @param user to update
     */
    void update(User user);

    /**
     * This method removes user from DB
     *
     * @param id user id to be removed
     */
    void remove(Long id);
}
