package cz.muni.pa165.teamwhite.formula1.entity;

import cz.muni.pa165.teamwhite.formula1.enums.ComponentType;

import javax.persistence.Enumerated;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.EnumType;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import java.util.Objects;

/**
 * @author Jiří Andrlík
 */
@Entity
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private Car car;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ComponentType type;

    public Component(){}

    public Component(Long componentId){
        this.id = componentId;
    }

    public Component(ComponentType ctype, Car car){
        this.type = ctype;
        this.car = car;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public ComponentType getType() {
        return type;
    }

    public void setType(ComponentType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Component)) return false;
        Component component = (Component) o;
        return Objects.equals(getCar(), component.getCar()) && getType() == component.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCar(), getType());
    }
}
