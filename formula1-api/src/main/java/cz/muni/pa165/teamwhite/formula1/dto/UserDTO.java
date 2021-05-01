package cz.muni.pa165.teamwhite.formula1.dto;

import cz.muni.pa165.teamwhite.formula1.enums.Role;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author Karolina Hecova
 */
public class UserDTO {
    private Long id;

    @NotNull
    private String login;

    @NotNull
    private String password;

    @NotNull
    private Role role;

    public UserDTO() {}

    public UserDTO(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public UserDTO(Long id, String login, String password, Role role) {
        this(login, password ,role);
        this.id = id;
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
                ", login='" + login + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO user = (UserDTO) o;
        return Objects.equals(getLogin(), user.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin());
    }
}
