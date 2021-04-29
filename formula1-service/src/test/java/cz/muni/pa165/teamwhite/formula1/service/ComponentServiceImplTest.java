package cz.muni.pa165.teamwhite.formula1.service;

import cz.muni.pa165.teamwhite.formula1.persistence.dao.ComponentDao;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Component;
import cz.muni.pa165.teamwhite.formula1.persistence.enums.ComponentType;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

/**
 *
 * @author Tomas Sedlacek
 */


@ContextConfiguration(classes = ServiceConfiguration.class)
public class ComponentServiceImplTest extends AbstractTransactionalTestNGSpringContextTests  {
    @Mock
    private ComponentDao componentDao;

    @InjectMocks
    private final ComponentServiceImpl componentService = new ComponentServiceImpl();

    private final Component engine = new Component(ComponentType.ENGINE, "v12 monster");
    private final Component spoiler = new Component(ComponentType.REARSPOILER, "ultra low pressure");

    @BeforeMethod
    public void setup() throws ServiceException
    {
        engine.setId(100L);
        spoiler.setId(10L);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUComponent() {
        componentService.createComponent(engine);
        verify(componentDao).create(engine);

    }

    @Test
    public void testUpdateComponent() {
        componentService.update(spoiler);
        verify(componentDao).update(spoiler);
    }

    @Test
    public void testFindAllComponents() {
        ArrayList<Component> list = new ArrayList<>();
        when(componentDao.findAll()).thenReturn(list);
        Assert.assertEquals(componentService.findAll(), list);
    }

    @Test
    public void testFindComponentByIdComponentExists() {
        when(componentDao.findById(10L)).thenReturn(spoiler);
        Assert.assertSame(componentService.findById(spoiler.getId()), spoiler);
    }

    @Test
    public void testFindComponentByIdComponentNotExists() {
        when(componentDao.findById(1L)).thenReturn(null);
        Assert.assertNull(componentService.findById(1L));
    }

    @Test
    public void testRemoveComponentIfExists() {
        when(componentDao.findById(engine.getId())).thenReturn(engine);
        doNothing().when(componentDao).remove(engine);
        componentService.remove(engine.getId());
        verify(componentDao).remove(engine);
    }

    @Test
    public void testRemoveWhenUserDoesNotExist() {
        when(componentDao.findById(engine.getId())).thenReturn(null);
        componentService.remove(engine.getId());
        verify(componentDao, times(0)).remove(engine);
    }
}
