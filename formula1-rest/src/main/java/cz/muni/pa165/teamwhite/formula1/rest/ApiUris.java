package cz.muni.pa165.teamwhite.formula1.rest;

/**
 * Represents the entry points for the API
 * this list can be increased so that it contains all the
 * other URIs also for the sub-resources so that it can
 * reused globally from all the controllers
 *
 * @author brossi
 */
public abstract class ApiUris {

    public static final String ROOT_URI = "/rest";

    public static final String ROOT_URI_USERS = "/users";
    public static final String ROOT_URI_USER = "/users/{id}";

    public static final String ROOT_URI_COMPONENTS = "/components";
    public static final String ROOT_URI_COMPONENT = "/components/{id}";

    public static final String ROOT_URI_DRIVERS = "/drivers";
    public static final String ROOT_URI_DRIVER = "/drivers/{id}";
    public static final String ROOT_URI_DRIVERS_SCORE = "/drivers/score";

    public static final String ROOT_URI_CARS = "/cars";
    public static final String ROOT_URI_CAR = "/cars/{id}";
    public static final String ROOT_URI_CAR_COMPONENTS = "/cars/{id}/components";
}
