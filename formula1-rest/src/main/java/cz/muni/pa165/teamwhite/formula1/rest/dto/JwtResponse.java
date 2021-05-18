package cz.muni.pa165.teamwhite.formula1.rest.dto;

/**
 * @author Jakub Fajkus
 */
public class JwtResponse {
    private final String jwt;
    private final String role;
    private final String username;

    public JwtResponse(String jwt, String role, String username) {
        this.jwt = jwt;
        this.role = role;
        this.username = username;
    }

    public String getJwt() {
        return jwt;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }
}
