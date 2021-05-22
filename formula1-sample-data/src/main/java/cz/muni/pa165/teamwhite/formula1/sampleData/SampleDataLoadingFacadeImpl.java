package cz.muni.pa165.teamwhite.formula1.sampleData;

import cz.muni.pa165.teamwhite.formula1.persistence.entity.Car;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Component;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.Driver;
import cz.muni.pa165.teamwhite.formula1.persistence.entity.User;
import cz.muni.pa165.teamwhite.formula1.persistence.enums.ComponentType;
import cz.muni.pa165.teamwhite.formula1.persistence.enums.Role;
import cz.muni.pa165.teamwhite.formula1.service.CarService;
import cz.muni.pa165.teamwhite.formula1.service.ComponentService;
import cz.muni.pa165.teamwhite.formula1.service.DriverService;
import cz.muni.pa165.teamwhite.formula1.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@org.springframework.stereotype.Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private CarService carService;

    @Autowired
    private ComponentService componentService;

    @Override
    public void loadData() {
        log.debug("Creating users");
        createUser("manager", "passmanager", Role.MANAGER);
        createUser("engineer1", "passengineer1", Role.ENGINEER);
        createUser("engineer2", "passengineer2", Role.ENGINEER);

        log.debug("Creating drivers");
        Driver lewis = createDriver(null, "Lewis", "Hamilton", "GB", true, 10, 10);
        Driver valtteri = createDriver(null, "Valtteri", "Bottas", "FI", true, 9, 9);
        createDriver(null, "Lando", "Norris", "GB", false, 9, 5);
        createDriver(null, "Daniel", "Ricciardo", "AT", true, 10, 1);
        createDriver(null, "Lance", "Stroll", "CA", false, 2, 8);

        log.debug("Creating cars");
        Car car1 = createCar("Race car #1", lewis, new HashSet<>());
        Car car2 = createCar("Race car #2", valtteri, new HashSet<>());

        log.debug("Creating components");
        this.createComponent(ComponentType.ENGINE, "V12 engine v1", car1);
        this.createComponent(ComponentType.REARSPOILER, "Carbon fibre spoiler v1", car1);
        this.createComponent(ComponentType.RIMS, "Magnesium rims v1", car1);
        this.createComponent(ComponentType.SUSPENSION, "Spaceship grade suspension v1", car1);
        this.createComponent(ComponentType.TRANSMISSION, "Superquick transmission v1", car1);

        this.createComponent(ComponentType.ENGINE, "V8 engine v1", car2);
        this.createComponent(ComponentType.REARSPOILER, "Experimental carbon fibre spoiler v1", car2);
        this.createComponent(ComponentType.RIMS, "Aluminium rims v1", car2);
        this.createComponent(ComponentType.SUSPENSION, "new F1 suspension v1", car2);
        this.createComponent(ComponentType.TRANSMISSION, "Automatic transmission v1", car2);

        createComponent(ComponentType.ENGINE, "V16 engine", null);
        createComponent(ComponentType.ENGINE, "V10 engine", null);
        createComponent(ComponentType.ENGINE, "V6 engine", null);
        createComponent(ComponentType.ENGINE, "I4 engine", null);
        createComponent(ComponentType.ENGINE, "I3 engine", null);

        createComponent(ComponentType.TRANSMISSION, "6 speed manual", null);
        createComponent(ComponentType.TRANSMISSION, "vintage automatic transmission", null);

        createComponent(ComponentType.RIMS, "steel rims", null);
        createComponent(ComponentType.RIMS, "titanium rims", null);

        createComponent(ComponentType.SUSPENSION, "air suspesion", null);
        createComponent(ComponentType.SUSPENSION, "hydraulic suspesion", null);

        createComponent(ComponentType.REARSPOILER, "ABS spoiler", null);
        createComponent(ComponentType.REARSPOILER, "steel spoiler", null);
    }

    private void createUser(String login, String password, Role engineer) {
        User user = new User(login, password, engineer);
        userService.createUser(user, password);
    }

    private Driver createDriver(Car car, String name, String surname, String nationality,
                                boolean isAggressive, int wetDriving, int reactions) {

        Driver driver = new Driver(car, name, surname, nationality, isAggressive, wetDriving, reactions);
        driverService.createDriver(driver);

        return driver;
    }

    private void createComponent(ComponentType type, String name, Car car) {
        Component component = new Component(type, name, car);
        componentService.createComponent(component);
    }

    private Car createCar(String name, Driver driver, Set<Component> components) {
        Car car = new Car(name, driver, components);
        carService.createCar(car);

        return car;
    }
}
