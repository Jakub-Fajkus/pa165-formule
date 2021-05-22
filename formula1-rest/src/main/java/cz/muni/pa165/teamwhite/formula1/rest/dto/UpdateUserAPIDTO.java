package cz.muni.pa165.teamwhite.formula1.rest.dto;

import cz.muni.pa165.teamwhite.formula1.enums.Role;

import java.util.Objects;

public class UpdateUserAPIDTO {

    private String login;

    private String password;

    private Role role;

    public UpdateUserAPIDTO() {
    }

    public UpdateUserAPIDTO(String login, String password, Role role){
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public String getLogin() {return login;}

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateUserAPIDTO that = (UpdateUserAPIDTO) o;
        return Objects.equals(getLogin(), that.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin());
    }

}
