package cz.muni.pa165.teamwhite.formula1.dto;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Jiri Andrlik
 */
public class CarDTO {
    private Long id;

    @NotNull
    private String name;

    private DriverDTO driver;

    private Set<ComponentDTO> components = new HashSet<>();

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

    public void setName(String name) {
        this.name = name;
    }

    public DriverDTO getDriver() {
        return driver;
    }

    public void setDriver(DriverDTO driver) {
        this.driver = driver;
    }

    public Set<ComponentDTO> getComponents() {
        return Collections.unmodifiableSet(components);
    }

    public void addComponent(ComponentDTO component) {
        this.components.add(component);
    }

    public void removeComponent(ComponentDTO component) {
        if (!this.components.contains(component)) {
            throw new IllegalArgumentException("Component " + component + " is not in this car!");
        }

        this.components.remove(component);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", driver=" + driver.getName() + " " + driver.getSurname() +
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
