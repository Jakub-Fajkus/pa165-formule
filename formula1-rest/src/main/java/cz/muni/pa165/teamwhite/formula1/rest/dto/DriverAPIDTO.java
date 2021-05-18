package cz.muni.pa165.teamwhite.formula1.rest.dto;

import java.util.Objects;

public class DriverAPIDTO {
    private Long id;

    private Long car;

    private String name;

    private String surname;

    private String nationality;

    private Boolean isAggressive;

    private Integer wetDriving;

    private Integer reactions;

    public DriverAPIDTO() {}

    public DriverAPIDTO(Long car, String name, String surname, String nationality,
                     Boolean isAggressive, Integer wetDriving, Integer reactions) {
        this.car = car;
        this.name = name;
        this.surname = surname;
        this.nationality = nationality;
        this.isAggressive = isAggressive;
        this.wetDriving = wetDriving;
        this.reactions = reactions;
    }

    public DriverAPIDTO(Long id, Long car, String name, String surname, String nationality,
                     Boolean isAggressive, Integer wetDriving, Integer reactions) {

        this(car, name, surname, nationality, isAggressive, wetDriving, reactions);

        this.id = id;
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
        return "DriverAPIDTO{" +
                "id=" + id +
                ", car=" + ((Objects.isNull(car)) ? "none" : car) +
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
        if (!(o instanceof DriverAPIDTO)) return false;
        DriverAPIDTO driver = (DriverAPIDTO) o;
        return getName().equals(driver.getName()) && getSurname().equals(driver.getSurname()) && getNationality().equals(driver.getNationality());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getNationality());
    }
}
