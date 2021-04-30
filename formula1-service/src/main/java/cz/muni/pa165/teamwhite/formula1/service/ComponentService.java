package cz.muni.pa165.teamwhite.formula1.service;

import cz.muni.pa165.teamwhite.formula1.persistence.entity.Component;

import java.util.List;

/**
 * @author Tomas Sedlacek
 */

public interface ComponentService {
    /**
     * Creates new component
     * @param component new component to be created
     * @return id of this new component
     */
    Long createComponent(Component component);

    /**
     * This method updates component already stored in DB or create new one
     *
     * @param component to update
     */
    void update(Component component);

    /**
     * This method finds all components
     *
     * @return list of all components
     */
    List<Component> findAll();

    /**
     * This method finds component in database by entered id of searched component
     *
     * @param id - id of component to be found
     * @return found component
     */
    Component findById(Long id);

    /**
     * This method component with given id from DB
     *
     * @param id id of component to be removed from DB
     */
    void remove(Long id);
}
