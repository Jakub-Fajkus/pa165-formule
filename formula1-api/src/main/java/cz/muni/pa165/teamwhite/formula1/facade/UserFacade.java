package cz.muni.pa165.teamwhite.formula1.facade;

import cz.muni.pa165.teamwhite.formula1.dto.CarDTO;
import cz.muni.pa165.teamwhite.formula1.dto.UserDTO;
import cz.muni.pa165.teamwhite.formula1.dto.UserAuthenticateDTO;

import java.util.List;

/**
 * @author Karolina Hecova
 */
public interface UserFacade {

	/**
	 * Gets list of all users.
	 *
	 * @return list of all users
	 */
	List<UserDTO> getAllUsers();

	/**
	 * Creates new user with the given unencrypted password.
	 *
	 * @param userDTO new user to be created
	 * @param unencryptedPassword unencrypted password
	 * @return id of the new user
	 */
	Long createUser(UserDTO userDTO, String unencryptedPassword);

	/**
	 * Deletes user by given id.
	 * @param userId id of the user to be deleted
	 */
	void deleteUser(Long userId);

	/**
	 * Gets user by given id.
	 *
	 * @param userId id of the wanted user
	 * @return user
	 */
	UserDTO getUserById(Long userId);

	/**
	 * Try to authenticate a user. Return true only if the hashed password matches the records.
	 *
	 * @param user to be authenticated
	 * @return true if the user was successfully authenticated, otherwise false
	 */
	boolean authenticate(UserAuthenticateDTO user);

	/**
	 * Check if the given user is manager.
	 *
	 * @param user to be tested as manager
	 * @return true if the given user is manager, otherwise false
	 */
	boolean isManager(UserDTO user);

	/**
	 * Updates given user and returns a new dto with updated user data.
	 *
	 * @param userDTO containing only fields that we want to change
	 * @return A new DTO with all fields
	 */
	UserDTO update(UserDTO userDTO);
}
