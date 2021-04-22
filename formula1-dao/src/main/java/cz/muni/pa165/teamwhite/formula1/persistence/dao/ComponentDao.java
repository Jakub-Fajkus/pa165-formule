package cz.muni.pa165.teamwhite.formula1.persistence.dao;

import cz.muni.pa165.teamwhite.formula1.persistence.entity.Component;

import java.util.List;

/**
 * @author Karolina Hecova
 */
public interface ComponentDao {

    /**
     * Saves the given component into the database.
     *
     * @param c, component to be saved
     */
    void create(Component c);

    /**
     * Updates the given component in the database
     *
     * @param c, component to be updated
     * @return updated component
     */
    Component update(Component c);

    /**
     * Finds all components in the database.
     *
     * @return list of components
     */
    List<Component> findAll();

    /**
     * Finds component in the database with given id.
     *
     * @param id of the component to be found
     * @return component with the given id
     */
    Component findById(Long id);

    /**
     * Removes the given component in the database.
     *
     * @param c, component to be removed
     */
    void remove(Component c);
}
