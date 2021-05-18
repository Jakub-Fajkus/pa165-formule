package cz.muni.pa165.teamwhite.formula1.dto;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.isNull;

/**
 * @author Jiri Andrlik
 */
public class CarDTO {
    private Long id;

    @NotNull
    private String name;

    private DriverDTO driver;

    private Set<ComponentDTO> components = new HashSet<>();

    public CarDTO() {}

    public CarDTO(Long id, String name, DriverDTO driver, Set<ComponentDTO> components) {
        this.id = id;
        this.name = name;
        this.driver = driver;
        this.components = components;
    }

    public CarDTO(String name, DriverDTO driver, Set<ComponentDTO> components) {
        this.name = name;
        this.driver = driver;
        this.components = components;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DriverDTO getDriver() {
        return driver;
    }

    public Set<ComponentDTO> getComponents() {
        return (components != null) ? Collections.unmodifiableSet(components) : null;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", driver=" + (isNull(driver) ? "None" : driver.getName() + " " + driver.getSurname()) +
                ", components=" + components +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarDTO)) return false;
        CarDTO car = (CarDTO) o;
        return getName().equals(car.getName());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
