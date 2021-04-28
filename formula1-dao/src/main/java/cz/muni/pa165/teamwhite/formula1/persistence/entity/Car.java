package cz.muni.pa165.teamwhite.formula1.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.isNull;

/**
 * @author Jakub Fajkus
 */
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotNull
    private String name;

    @OneToOne
    private Driver driver;

    @OneToMany
    private Set<Component> components = new HashSet<>();

    public Car() {
    }

    public Car(Long id) {
        this.id = id;
    }

    public Car(String name, Driver driver, Set<Component> components) {
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

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Set<Component> getComponents() {
        return Collections.unmodifiableSet(components);
    }

    public void addComponent(Component component) {
        this.components.add(component);
    }

    public void removeComponent(Component component) {
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
                ", driver=" + (isNull(driver)?"None":driver.getName() + " " + driver.getSurname()) +
                ", components=" + components +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;
        return getName().equals(car.getName());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
