package cz.muni.pa165.teamwhite.formula1.dao;

import cz.muni.pa165.teamwhite.formula1.entity.Car;
import cz.muni.pa165.teamwhite.formula1.entity.Component;
import cz.muni.pa165.teamwhite.formula1.enums.ComponentType;

import java.util.List;

/**
 * @author Karolina Hecova
 */
public interface ComponentDao {

    /**
    * Saves the given component into the database.
    * @param c, component to be saved
    */
    void create(Component c);

    /**
     * Updates the given component in the database
     * @param c, component to be updated
     * @return updated component
     */
    Component update(Component c);

    /**
     * Finds all components in the database.
     * @return list of components
     */
    List<Component> findAll();

    /**
     * Finds component in the database with given id.
     * @param id of the component to be found
     * @return component with the given id
     */
    Component findById(Long id);

    /**
     * Finds all components in database related with given car.
     * @param car for which we want to find all component
     * @return list of components of given car
     */
    List<Component> findByCar(Car car);

    /**
     * Finds all components in database with given type.
     * @param type of the component to be found
     * @return list of components with given type
     */
    List<Component> findByType(ComponentType type);

    /**
     * Removes the given component in the database.
     * @param c, component to be removed
     */
    void remove(Component c);
}
