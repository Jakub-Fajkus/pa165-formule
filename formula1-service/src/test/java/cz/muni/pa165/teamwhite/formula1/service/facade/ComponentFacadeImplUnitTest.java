package cz.muni.pa165.teamwhite.formula1.service.facade;


import cz.muni.pa165.teamwhite.formula1.dto.ComponentDTO;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Component;
import cz.muni.pa165.teamwhite.formula1.persistence.enums.ComponentType;
import cz.muni.pa165.teamwhite.formula1.service.ComponentService;
import cz.muni.pa165.teamwhite.formula1.service.ServiceConfiguration;
import cz.muni.pa165.teamwhite.formula1.service.mapping.BeanMappingService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Tomas Sedlacek
 */


@ContextConfiguration(classes = ServiceConfiguration.class)
public class ComponentFacadeImplUnitTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private ComponentService componentService;

    @Mock
    private BeanMappingService beanMappingService;

    @InjectMocks
    private final ComponentFacadeImpl componentFacade = new ComponentFacadeImpl();

    private final Component engine = new Component(ComponentType.ENGINE, "v12 monster");
    private final Component spoiler = new Component(ComponentType.REARSPOILER, "ultra low pressure");

    private final ComponentDTO engineDTO = new ComponentDTO(cz.muni.pa165.teamwhite.formula1.enums.ComponentType.ENGINE, "v12 monster", null);
    private final ComponentDTO spoilerDTO = new ComponentDTO(cz.muni.pa165.teamwhite.formula1.enums.ComponentType.REARSPOILER, "ultra low pressure", null);

    List<Component> components = List.of(engine, spoiler);
    List<ComponentDTO> componentsDTO = List.of(engineDTO, spoilerDTO);

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testGetAllComponents() {
        when(componentService.findAll()).thenReturn(components);
        when(beanMappingService.mapTo(components, ComponentDTO.class)).thenReturn(componentsDTO);

        Assert.assertSame(componentFacade.getAllComponents(), componentsDTO);
    }

    @Test
    public void testCreateNewComponet() {
        when(beanMappingService.mapTo(engineDTO, Component.class)).thenReturn(engine);
        when(componentService.createComponent(engine)).thenReturn(100L);

        Assert.assertEquals((long) componentFacade.createComponent(engineDTO), 100L);
    }

    @Test
    public void testGetComponentById() {
        when(componentService.findById(spoiler.getId())).thenReturn(spoiler);
        when(beanMappingService.mapTo(spoiler, ComponentDTO.class)).thenReturn(spoilerDTO);

        Assert.assertEquals(componentFacade.getComponentById(spoilerDTO.getId()), spoilerDTO);
    }

    @Test
    public void testDeleteComponentById() {
        componentFacade.deleteComponent(10L);

        verify(componentService).remove(10L);
    }
}
