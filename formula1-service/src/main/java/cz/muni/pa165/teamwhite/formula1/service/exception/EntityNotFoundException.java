package cz.muni.pa165.teamwhite.formula1.service.exception;

public class EntityNotFoundException extends Formula1ServiceException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
