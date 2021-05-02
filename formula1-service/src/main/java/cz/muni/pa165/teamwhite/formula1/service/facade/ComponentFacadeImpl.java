package cz.muni.pa165.teamwhite.formula1.service.facade;


import cz.muni.pa165.teamwhite.formula1.dto.ComponentDTO;
import cz.muni.pa165.teamwhite.formula1.facade.ComponentFacade;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Car;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Component;
import cz.muni.pa165.teamwhite.formula1.service.CarService;
import cz.muni.pa165.teamwhite.formula1.service.ComponentService;
import cz.muni.pa165.teamwhite.formula1.service.mapping.BeanMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Tomas Sedlacek
 */


@Transactional
@Service
public class ComponentFacadeImpl implements ComponentFacade {
    @Autowired
    private ComponentService componentService;

    @Autowired
    private CarService carService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public List<ComponentDTO> getAllComponents() {
        return beanMappingService.mapTo(componentService.findAll(), ComponentDTO.class);
    }

    @Override
    public Long createComponent(ComponentDTO componentDTO) {
        return componentService.createComponent(beanMappingService.mapTo(componentDTO, Component.class));
    }

    @Override
    public ComponentDTO getComponentById(Long componentId) {
        return beanMappingService.mapTo(componentService.findById(componentId), ComponentDTO.class);
    }

    @Override
    public void deleteComponent(Long componentId) {
        componentService.remove(componentId);
    }

    @Override
    public ComponentDTO update(ComponentDTO componentDto) {
        Component dbComponent = componentService.findById(componentDto.getId());
        Car inDb = dbComponent.getCar();
        if (componentDto.getCar() != null && inDb != null && inDb.getComponents().contains(beanMappingService.mapTo(componentDto, Component.class))) {
            inDb.removeComponent(componentService.findById(componentDto.getId()));
            carService.update(inDb);
        }
        beanMappingService.mapToObject(componentDto, dbComponent);
        componentService.update(dbComponent);

        return getComponentById(componentDto.getId());
    }
}
