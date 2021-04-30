package cz.muni.pa165.teamwhite.formula1.service.mapping;

import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;


public interface BeanMappingService {
	
    public  <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    public  void mapToObject(Object u, Object v);

    public  <T> T mapTo(Object u, Class<T> mapToClass);

    public Mapper getMapper();
}
