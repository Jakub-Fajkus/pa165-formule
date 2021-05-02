package cz.muni.pa165.teamwhite.formula1.facade;

import cz.muni.pa165.teamwhite.formula1.dto.ComponentDTO;

import java.util.List;

/**
* @author Tomas Sedlacek
*/
public interface ComponentFacade {

	/**
	 * Gets all components
	 *
	 * @return list of all components
	 **/
	List<ComponentDTO> getAllComponents();

	/**
	 * creates new component
	 *
	 * @param componentDTO component to be added
	 * @return id of created component
	 */
	Long createComponent(ComponentDTO componentDTO);

	/**
	 * gets component for requested id
	 *
	 * @param componentId id of component
	 * @return component for requested id
	 */
	ComponentDTO getComponentById(Long componentId);

	/**
	 * deletes component
	 *
	 * @param componentId component to be deleted
	 */
	void deleteComponent(Long componentId);

	/**
	 * updates given component and return updated componentDTO
	 *
	 * @param componentDTO componentDTO to be modified
	 * @return modified componentDTo
	 */
	ComponentDTO update(ComponentDTO componentDTO);
}
