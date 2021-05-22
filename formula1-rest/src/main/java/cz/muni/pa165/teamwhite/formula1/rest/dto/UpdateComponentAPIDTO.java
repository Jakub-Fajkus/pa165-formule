package cz.muni.pa165.teamwhite.formula1.rest.dto;

import cz.muni.pa165.teamwhite.formula1.enums.ComponentType;

import java.util.Objects;

/**
 * @author Tomas Sedlacek
 */
public class UpdateComponentAPIDTO {
    private Long id;

    private String name;

    private Long car;

    private ComponentType type;

    public UpdateComponentAPIDTO() {}

    public UpdateComponentAPIDTO(String name, Long car, ComponentType type) {
        this.name = name;
        this.car = car;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public Long getCar() {
        return car;
    }

    public String getName() {
        return name;
    }

    public ComponentType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "UpdateComponentAPIDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", car=" + car +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdateComponentAPIDTO)) return false;
        UpdateComponentAPIDTO that = (UpdateComponentAPIDTO) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getCar(), that.getCar()) && getType() == that.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCar(), getType());
    }
}
