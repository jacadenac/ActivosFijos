package asd.prueba.exception;

import asd.prueba.utils.ApiStatus;


/**
 *
 * @author Alejandro Cadena
 */
public class EmptyResultException extends ServiceException {
    
    public EmptyResultException() {
        super("Emtpy result");
    }
    
    public EmptyResultException(String message) {
        super(message);
    }

    public EmptyResultException(Throwable cause) {
        super("Emtpy result", cause);
    }
    
    public EmptyResultException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public ApiStatus getAppStatus() {
        return ApiStatus.EMPTY;
    }
    
}
