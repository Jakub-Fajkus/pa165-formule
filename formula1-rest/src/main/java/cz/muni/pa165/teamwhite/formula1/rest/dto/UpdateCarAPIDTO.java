package cz.muni.pa165.teamwhite.formula1.rest.dto;

import java.util.Objects;

import static java.util.Objects.isNull;

public class UpdateCarAPIDTO {
    private String name;

    private Long driver;


    public UpdateCarAPIDTO() {
    }

    public UpdateCarAPIDTO(String name, Long driver) {
        this.name = name;
        this.driver = driver;
    }

    public String getName() {
        return name;
    }

    public Long getDriver() {
        return driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdateCarAPIDTO)) return false;
        UpdateCarAPIDTO car = (UpdateCarAPIDTO) o;
        return getName().equals(car.getName());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
