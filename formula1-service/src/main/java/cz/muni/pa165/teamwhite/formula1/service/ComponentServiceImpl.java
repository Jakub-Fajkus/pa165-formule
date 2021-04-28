package cz.muni.pa165.teamwhite.formula1.service;

import cz.muni.pa165.teamwhite.formula1.persistence.dao.ComponentDao;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ComponentServiceImpl implements ComponentService{
    @Autowired
    private ComponentDao componentDao;

    @Override
    public Long createComponent(Component component) {
        componentDao.create(component);

        return component.getId();
    }

    @Override
    public void update(Component component) {
        componentDao.update(component);
    }

    @Override
    public List<Component> findAll() {
        return componentDao.findAll();
    }

    @Override
    public Component findById(Long id) {
        return componentDao.findById(id);
    }

    @Override
    public void remove(Long id) {
        //TODO maybe check if is something for deletion
        componentDao.remove(componentDao.findById(id));
    }
}
