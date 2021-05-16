package cz.muni.pa165.teamwhite.formula1.rest.controller;

import cz.muni.pa165.teamwhite.formula1.rest.ResponseStatuses;
import cz.muni.pa165.teamwhite.formula1.rest.RestResponse;
import cz.muni.pa165.teamwhite.formula1.service.exception.EntityNotFoundException;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionController {
    final static Logger log = LoggerFactory.getLogger(GlobalExceptionController.class);

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    RestResponse<List<String>> handleException(EntityNotFoundException ex) {
        log.debug("handleException", ex);
        return new RestResponse<>(Arrays.asList("Entity was not found", ex.getMessage()), ResponseStatuses.ENTITY_NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    RestResponse<List<String>> handleException(ServiceException ex) {
        log.error("handleException", ex);
        return new RestResponse<>(Arrays.asList("User service exception occurred", ex.getMessage()), ResponseStatuses.ERROR);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    RestResponse<List<String>> handleException(Throwable ex) {
        log.debug("handleException - internal", ex);

        return new RestResponse<>(Arrays.asList("Internal server error", ex.getMessage()), ResponseStatuses.ERROR);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    RestResponse<List<String>> handleException(ConstraintViolationException ex) {
        log.debug("handleException - validation", ex);

        return new RestResponse<>(Arrays.asList("Validation error", ex.getMessage()), ResponseStatuses.ERROR);
    }
}
