package cz.muni.pa165.teamwhite.formula1.service.exception;

public class Formula1ServiceException extends RuntimeException {
    public Formula1ServiceException(String message) {
        super(message);
    }

    public Formula1ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
