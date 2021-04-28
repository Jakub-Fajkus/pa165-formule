package cz.muni.pa165.teamwhite.formula1.service.facade;


import cz.muni.pa165.teamwhite.formula1.dto.ComponentDTO;
import cz.muni.pa165.teamwhite.formula1.facade.ComponentFacade;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Component;
import cz.muni.pa165.teamwhite.formula1.service.ComponentService;
import cz.muni.pa165.teamwhite.formula1.service.mapping.BeanMappingService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Tomas Sedlacek
 */


@Transactional
public class ComponentFacadeImpl implements ComponentFacade {
    @Autowired
    private ComponentService componentService;

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
}
