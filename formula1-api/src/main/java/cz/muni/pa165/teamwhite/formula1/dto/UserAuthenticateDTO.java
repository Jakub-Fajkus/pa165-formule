package cz.muni.pa165.teamwhite.formula1.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Karolina Hecova
 */
public class UserAuthenticateDTO
{
    @NotNull
    private String login;

    @NotNull
    private String password;

    public UserAuthenticateDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin()
    {
        return login;
    }

    public void setUserId(String login)
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
