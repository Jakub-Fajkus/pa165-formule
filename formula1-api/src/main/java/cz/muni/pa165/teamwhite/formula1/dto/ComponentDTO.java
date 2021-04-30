package cz.muni.pa165.teamwhite.formula1.dto;

import cz.muni.pa165.teamwhite.formula1.enums.ComponentType;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author Tomas Sedlacek
 */

public class ComponentDTO {
    private Long id;

    @NotNull
    private String name;

    private CarDTO car;

    @NotNull
    private ComponentType type;

    public ComponentDTO() {}

    public ComponentDTO(ComponentType ctype, String name, CarDTO car) {
        this.type = ctype;
        this.name = name;
        this.car = car;
    }

    public ComponentDTO(Long id, ComponentType ctype, String name, CarDTO car) {
        this(ctype, name, car);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public CarDTO getCar() {
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
        return "ComponentDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", car=" + ((Objects.isNull(car)) ? "none" : car.getName()) +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComponentDTO)) return false;
        ComponentDTO component = (ComponentDTO) o;
        return getName().equals(component.getName()) && getType() == component.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType());
    }
}
