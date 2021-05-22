package cz.muni.pa165.teamwhite.formula1.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import cz.muni.pa165.teamwhite.formula1.dto.CarDTO;
import cz.muni.pa165.teamwhite.formula1.dto.ComponentDTO;
import cz.muni.pa165.teamwhite.formula1.dto.DriverDTO;
import cz.muni.pa165.teamwhite.formula1.dto.UserDTO;
import cz.muni.pa165.teamwhite.formula1.rest.dto.CarAPIDTO;
import cz.muni.pa165.teamwhite.formula1.rest.dto.ComponentAPIDTO;
import cz.muni.pa165.teamwhite.formula1.rest.dto.DriverAPIDTO;
import cz.muni.pa165.teamwhite.formula1.rest.mixin.UserDTOMixin;
import cz.muni.pa165.teamwhite.formula1.rest.security.SecurityConfiguration;
import cz.muni.pa165.teamwhite.formula1.sampleData.SampleDataConfiguration;
import cz.muni.pa165.teamwhite.formula1.service.DozerCustomConfig;
import cz.muni.pa165.teamwhite.formula1.service.ServiceConfiguration;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@EnableWebMvc
@EnableSwagger2
@Configuration
@Import({ServiceConfiguration.class, SampleDataConfiguration.class, SecurityConfiguration.class})
@ComponentScan(basePackageClasses = {RestResponse.class}, basePackages = {"cz.muni.pa165.teamwhite.formula1.rest", "cz.muni.pa165.teamwhite.formula1.rest.controller"})
public class RestConfiguration implements WebMvcConfigurer {
    private final String baseUrl = "";

    @Autowired
    SecurityConfiguration securityConfiguration;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AllowOriginInterceptor());
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(Collections.singletonList(securityConfiguration.securityContext()))
                .securitySchemes(Collections.singletonList(securityConfiguration.apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    @Primary
    public Mapper dozer() {
        DozerBeanMapper dozer = new DozerBeanMapper();

        dozer.addMapping(new DozerCustomConfig());
        dozer.addMapping(new RESTDozerCustomConfig());

        return dozer;
    }

    public class RESTDozerCustomConfig extends BeanMappingBuilder {
        @Override
        protected void configure() {
            mapping(ComponentDTO.class, ComponentAPIDTO.class)
                    .fields(field("id").accessible(), field("id").accessible())
                    .fields(field("car.id").accessible(), field("car").accessible())
                    .fields(field("type").accessible(), field("type").accessible())
                    .fields(field("name").accessible(), field("name").accessible())
            ;

            mapping(DriverDTO.class, DriverAPIDTO.class)
                    .fields(field("id").accessible(), field("id").accessible())
                    .fields(field("name").accessible(), field("name").accessible())
                    .fields(field("surname").accessible(), field("surname").accessible())
                    .fields(field("nationality").accessible(), field("nationality").accessible())
                    .fields(field("isAggressive").accessible(), field("aggressive").accessible())
                    .fields(field("wetDriving").accessible(), field("wetDriving").accessible())
                    .fields(field("reactions").accessible(), field("reactions").accessible())
                    .fields(field("car.id").accessible(), field("car").accessible())
            ;

            mapping(CarDTO.class, CarAPIDTO.class)
                    .fields(field("id").accessible(), field("id").accessible())
                    .fields(field("name").accessible(), field("name").accessible())
                    .fields(field("driver.id").accessible(), field("driver").accessible())
            ;
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String baseUrl = StringUtils.trimTrailingCharacter(this.baseUrl, '/');

        registry.
                addResourceHandler(baseUrl + "/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);

        registry.addResourceHandler(baseUrl + "swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(baseUrl + "/swagger-ui/")
                .setViewName("forward:" + baseUrl + "/swagger-ui/index.html");
    }


    @Bean
    @Primary
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH));

        objectMapper.addMixIn(UserDTO.class, UserDTOMixin.class);

        objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

        jsonConverter.setObjectMapper(objectMapper);
        return jsonConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customJackson2HttpMessageConverter());
    }
}
