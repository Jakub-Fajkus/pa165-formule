package cz.muni.pa165.teamwhite.formula1.rest.dto;

import cz.muni.pa165.teamwhite.formula1.enums.ComponentType;

import java.util.Objects;

/**
 * @author Tomas Sedlacek
 */
public class ComponentAPIDTO {
    private Long id;

    private String name;

    private Long car;

    private ComponentType type;

    public ComponentAPIDTO() {}

    public ComponentAPIDTO(ComponentType ctype, String name, Long car) {
        this.type = ctype;
        this.name = name;
        this.car = car;
    }

    public ComponentAPIDTO(Long id, ComponentType ctype, String name, Long car) {
        this(ctype, name, car);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getCar() {
        return car;
    }

    public ComponentType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ComponentAPIDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", car=" + ((Objects.isNull(car)) ? "null" : car) +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComponentAPIDTO)) return false;
        ComponentAPIDTO component = (ComponentAPIDTO) o;
        return getName().equals(component.getName()) && getType() == component.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType());
    }
}
