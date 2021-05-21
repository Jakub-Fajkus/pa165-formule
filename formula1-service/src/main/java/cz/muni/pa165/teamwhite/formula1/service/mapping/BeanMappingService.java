package cz.muni.pa165.teamwhite.formula1.service.mapping;

import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;


public interface BeanMappingService {
	
    <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    void mapToObject(Object u, Object v);

    <T> T mapTo(Object u, Class<T> mapToClass);

    Mapper getMapper();
}
