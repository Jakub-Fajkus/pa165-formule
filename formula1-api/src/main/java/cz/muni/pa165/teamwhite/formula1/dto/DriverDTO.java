package cz.muni.pa165.teamwhite.formula1.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author Jakub Fajkus
 */
public class DriverDTO {
    private Long id;

    private CarDTO car;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String nationality;

    @NotNull
    private Boolean isAggressive;

    @NotNull
    private Integer wetDriving;

    @NotNull
    private Integer reactions;

    public DriverDTO() {}

    public DriverDTO(CarDTO car, String name, String surname, String nationality,
                     Boolean isAggressive, Integer wetDriving, Integer reactions) {
        this.car = car;
        this.name = name;
        this.surname = surname;
        this.nationality = nationality;
        this.isAggressive = isAggressive;
        this.wetDriving = wetDriving;
        this.reactions = reactions;
    }

    public DriverDTO(Long id, CarDTO car, String name, String surname, String nationality,
                     Boolean isAggressive, Integer wetDriving, Integer reactions) {

        this(car, name, surname, nationality, isAggressive, wetDriving, reactions);

        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public CarDTO getCar() {
        return car;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getNationality() {
        return nationality;
    }

    public Boolean isAggressive() {
        return isAggressive;
    }

    public Integer getWetDriving() {
        return wetDriving;
    }

    public Integer getReactions() {
        return reactions;
    }

    @Override
    public String toString() {
        return "DriverDTO{" +
                "id=" + id +
                ", car=" + ((Objects.isNull(car)) ? "none" : car.getName()) +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", nationality='" + nationality + '\'' +
                ", isAggressive=" + (isAggressive ? "yes" : "no") +
                ", wetDriving=" + wetDriving +
                ", reactions=" + reactions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DriverDTO)) return false;
        DriverDTO driver = (DriverDTO) o;
        return getName().equals(driver.getName()) && getSurname().equals(driver.getSurname()) && getNationality().equals(driver.getNationality());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getNationality());
    }
}
