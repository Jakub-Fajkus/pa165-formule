package cz.muni.pa165.teamwhite.formula1.dto;

import javax.validation.constraints.NotNull;

public class ScoredDriverDTO  {
    @NotNull
    private Integer score;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    public ScoredDriverDTO(Integer score, String firstName, String lastName) {
        this.score = score;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
