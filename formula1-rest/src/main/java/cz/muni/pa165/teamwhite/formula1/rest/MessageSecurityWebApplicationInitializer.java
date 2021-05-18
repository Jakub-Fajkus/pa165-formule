package cz.muni.pa165.teamwhite.formula1.rest;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Without this class, spring boot security does not work...
 *
 * @author Jakub Fajkus
 */
public class MessageSecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
}
