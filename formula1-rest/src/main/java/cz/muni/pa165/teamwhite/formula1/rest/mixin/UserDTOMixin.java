package cz.muni.pa165.teamwhite.formula1.rest.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "password"})
public class UserDTOMixin {
    
}
