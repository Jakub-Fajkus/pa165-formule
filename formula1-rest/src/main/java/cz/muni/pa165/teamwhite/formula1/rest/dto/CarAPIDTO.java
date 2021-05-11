package cz.muni.pa165.teamwhite.formula1.rest.dto;

import java.util.Objects;

import static java.util.Objects.isNull;

public class CarAPIDTO {
    private Long id;

    private String name;

    private Long driver;


    public CarAPIDTO() {
    }

    public CarAPIDTO(Long id, String name, Long driver) {
        this.id = id;
        this.name = name;
        this.driver = driver;
    }

    public CarAPIDTO(String name, Long driver) {
        this.name = name;
        this.driver = driver;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getDriver() {
        return driver;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", driver=" + (isNull(driver) ? "None" : driver) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarAPIDTO)) return false;
        CarAPIDTO car = (CarAPIDTO) o;
        return getName().equals(car.getName());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
