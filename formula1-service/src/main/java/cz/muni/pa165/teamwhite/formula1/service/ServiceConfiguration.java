package cz.muni.pa165.teamwhite.formula1.service;

import cz.muni.pa165.teamwhite.formula1.dto.CarDTO;
import cz.muni.pa165.teamwhite.formula1.dto.ComponentDTO;
import cz.muni.pa165.teamwhite.formula1.dto.DriverDTO;
import cz.muni.pa165.teamwhite.formula1.dto.UserDTO;
import cz.muni.pa165.teamwhite.formula1.persistence.PersistenceConfig;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Car;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Component;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Driver;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.User;
import cz.muni.pa165.teamwhite.formula1.service.facade.CarFacadeImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;
import org.dozer.loader.api.TypeMappingOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Taken from the Seminar05 code
 */
@Configuration
//@EnableTransactionManagement
@Import(PersistenceConfig.class)
@ComponentScan(basePackageClasses = {CarServiceImpl.class, CarFacadeImpl.class}, basePackages = "cz.muni.pa165.teamwhite.formula1.service")
public class ServiceConfiguration {

    @Bean
    public Mapper dozer() {
        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.addMapping(new DozerCustomConfig());

//        dozer.setCustomFieldMapper((source, destination, sourceFieldValue, classMap, fieldMapping) ->
//                sourceFieldValue == null);

        return dozer;
    }

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
            ;

            mapping(UserDTO.class, User.class, TypeMappingOptions.mapNull(false))
                    .fields(field("id").accessible(), field("id").accessible())
                    .fields(field("login").accessible(), field("login").accessible())
                    .fields(field("password").accessible(), field("password").accessible())
                    .fields(field("role").accessible(), field("role").accessible())
            ;

        }
    }

    @Bean
    public PasswordEncoder argon2PasswordEncoder() {
        return new Argon2PasswordEncoder();
    }
}
