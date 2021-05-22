package cz.muni.pa165.teamwhite.formula1.rest.dto;

import cz.muni.pa165.teamwhite.formula1.enums.Role;

import java.util.Objects;

public class UserAPIDTO {
    private Long id;

    private String login;

    private String password;

    private Role role;

    public UserAPIDTO() {

    }

    public UserAPIDTO(Long id, String login, String password, Role role) {
        this.id = id;
        this.login = login;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                "login=" + login +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAPIDTO that = (UserAPIDTO) o;
        return getId().equals(that.getId()) && Objects.equals(getLogin(), that.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogin());
    }
}
