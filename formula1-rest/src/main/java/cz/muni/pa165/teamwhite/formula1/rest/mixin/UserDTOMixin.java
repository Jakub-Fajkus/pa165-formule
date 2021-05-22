package cz.muni.pa165.teamwhite.formula1.rest.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Jiri Andrlik
 */
@JsonIgnoreProperties({ "password"})
public class UserDTOMixin {
    
}
