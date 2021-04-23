package cz.muni.pa165.teamwhite.formula1.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.Objects;


/**
 * @author Tomas Sedlacek
 */

@Entity
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Car car;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(nullable = false)
    @NotNull
    private String surname;

    @Column(nullable = false)
    @NotNull
    private String nationality;

    @Column(nullable = false)
    @NotNull
    private boolean isAggressive;

    @Column(nullable = false)
    @NotNull
    private int wetDriving;

    @Column(nullable = false)
    @NotNull
    private int reactions;


    public Driver() {
    }

    public Driver(Long id) {
        this.id = id;
    }

    public Driver(Car car, String name, String surname, String nationality,
                  boolean isAggressive, int wetDriving, int reactions) {
        this.car = car;
        this.name = name;
        this.surname = surname;
        this.nationality = nationality;
        this.isAggressive = isAggressive;
        this.wetDriving = wetDriving;
        this.reactions = reactions;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public boolean isAggressive() {
        return isAggressive;
    }

    public int getWetDriving() {
        return wetDriving;
    }

    public void setWetDriving(int wetDriving) {
        this.wetDriving = wetDriving;
    }

    public int getReactions() {
        return reactions;
    }

    public void setReactions(int reactions) {
        this.reactions = reactions;
    }

    public void setIsAggressive(boolean isAggressive) {
        this.isAggressive = isAggressive;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", car=" + car.getName() +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", nationality='" + nationality + '\'' +
                ", isAggressive=" + isAggressive +
                ", wetDriving=" + wetDriving +
                ", reactions=" + reactions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Driver)) return false;
        Driver driver = (Driver) o;
        return getName().equals(driver.getName()) && getSurname().equals(driver.getSurname()) && getNationality().equals(driver.getNationality());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getNationality());
    }
}
