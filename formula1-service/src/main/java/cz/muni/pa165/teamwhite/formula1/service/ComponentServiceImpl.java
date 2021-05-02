package cz.muni.pa165.teamwhite.formula1.service;

import cz.muni.pa165.teamwhite.formula1.persistence.dao.ComponentDao;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Component;
import cz.muni.pa165.teamwhite.formula1.service.exception.Formula1ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Tomas Sedlacek
 */
@Service
public class ComponentServiceImpl implements ComponentService {
    @Autowired
    private ComponentDao componentDao;

    @Autowired
    private CarService carService;

    @Override
    public Long createComponent(Component component) {
        try {
            if (component.getCar() != null) {
                component.getCar().addComponent(component);
                if (component.getCar().getId() != null) {
                    carService.update(component.getCar());
                } else {
                    carService.createCar(component.getCar());
                }
            }

            componentDao.create(component);
        } catch (DataAccessException e) {
            throw new Formula1ServiceException("Could not create component: " + component, e);
        }

        return component.getId();
    }

    @Override
    public void update(Component component) {
        try {
            if (component.getCar() != null) {
                component.getCar().addComponent(component);
                carService.update(component.getCar());
            }
            componentDao.update(component);
        } catch (DataAccessException e) {
            throw new Formula1ServiceException("Could not update component: " + component, e);
        }
    }

    @Override
    public List<Component> findAll() {
        List<Component> result;

        try {
            result = componentDao.findAll();
        } catch (DataAccessException e) {
            throw new Formula1ServiceException("Could not return all components", e);
        }
        return result;
    }

    @Override
    public Component findById(Long id) {
        Component found;

        try {
            found = componentDao.findById(id);
        } catch (DataAccessException e) {
            throw new Formula1ServiceException("Could not find component with ID: " + id, e);
        }

        return found;
    }

    @Override
    public void remove(Long id) {
        Component toDelete = findById(id);

        try {
            if (toDelete != null) componentDao.remove(toDelete);
        } catch (DataAccessException e) {
            throw new Formula1ServiceException("Could not remove component with ID: " + id, e);
        }
    }
}
