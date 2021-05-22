package cz.muni.pa165.teamwhite.formula1.service;

import cz.muni.pa165.teamwhite.formula1.persistence.PersistenceConfig;
import cz.muni.pa165.teamwhite.formula1.service.facade.CarFacadeImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
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
@Import(PersistenceConfig.class)
@ComponentScan(basePackageClasses = {CarServiceImpl.class, CarFacadeImpl.class}, basePackages = "cz.muni.pa165.teamwhite.formula1.service")
public class ServiceConfiguration {

    @Bean
    public Mapper dozer() {
        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.addMapping(new DozerCustomConfig());

        return dozer;
    }

    @Bean
    public PasswordEncoder argon2PasswordEncoder() {
        return new Argon2PasswordEncoder();
    }
}
