package cz.muni.pa165.teamwhite.formula1.service;

import cz.muni.pa165.teamwhite.formula1.dto.CarDTO;
import cz.muni.pa165.teamwhite.formula1.dto.ComponentDTO;
import cz.muni.pa165.teamwhite.formula1.dto.DriverDTO;
import cz.muni.pa165.teamwhite.formula1.dto.UserDTO;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Car;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Component;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Driver;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.User;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;
import org.dozer.loader.api.TypeMappingOptions;

public class DozerCustomConfig extends BeanMappingBuilder {
    @Override
    protected void configure() {
        mapping(CarDTO.class, Car.class, TypeMappingOptions.mapNull(false))
                .fields(field("id").accessible(), field("id").accessible())
                .fields(field("name").accessible(), field("name").accessible())
                .fields(field("components").accessible(), field("components").accessible(), FieldsMappingOptions.removeOrphans())
                .fields(field("driver").accessible(), field("driver").accessible())
        ;
        mapping(ComponentDTO.class, Component.class, TypeMappingOptions.mapNull(false))
                .fields(field("id").accessible(), field("id").accessible())
                .fields(field("car").accessible(), field("car").accessible())
                .fields(field("type").accessible(), field("type").accessible())
                .fields(field("name").accessible(), field("name").accessible())
        ;
        mapping(DriverDTO.class, Driver.class, TypeMappingOptions.mapNull(false))
                .fields(field("id").accessible(), field("id").accessible())
                .fields(field("name").accessible(), field("name").accessible())
                .fields(field("surname").accessible(), field("surname").accessible())
                .fields(field("nationality").accessible(), field("nationality").accessible())
                .fields(field("isAggressive").accessible(), field("isAggressive").accessible())
                .fields(field("wetDriving").accessible(), field("wetDriving").accessible())
                .fields(field("reactions").accessible(), field("reactions").accessible())
                .fields(field("car").accessible(), field("car").accessible())
        ;

        mapping(UserDTO.class, User.class, TypeMappingOptions.mapNull(false))
                .fields(field("id").accessible(), field("id").accessible())
                .fields(field("login").accessible(), field("login").accessible())
                .fields(field("password").accessible(), field("password").accessible())
                .fields(field("role").accessible(), field("role").accessible())
        ;

    }
}
