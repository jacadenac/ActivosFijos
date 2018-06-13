package asd.prueba.controller;


import asd.prueba.exception.ApiError;
import asd.prueba.exception.ApiValidationError;
import asd.prueba.exception.FieldValidationException;
import asd.prueba.exception.ModelMappingException;
import asd.prueba.utils.ApiStatus;
import java.sql.SQLException;
import java.util.Arrays;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author Alejandro Cadena
 */
@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
    
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(CustomRestExceptionHandler.class);
    
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        ApiError apiError = new ApiError(
                ApiStatus.UNSUPPORTED_MEDIA_TYPE, 
                "Media type is not supported", 
                builder.substring(0, builder.length() - 2));
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }
    
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException ex, 
      HttpHeaders headers, 
      HttpStatus status, 
      WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t).append(" "));
        ApiError apiError = new ApiError(ApiStatus.METHOD_NOT_ALLOWED, builder.toString());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }
    
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, 
            HttpHeaders headers, 
            HttpStatus status, 
            WebRequest request) {
        ApiError apiError = new ApiError(ApiStatus.UNPROCESSABLE_REQUEST_ENTITY, "The body can not be empty", ex.getLocalizedMessage());
        return new ResponseEntity(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }
    
    @ExceptionHandler({ FieldValidationException.class })
    public ResponseEntity<Object> handleAll(FieldValidationException ex, WebRequest request) {
        ApiValidationError apiValidationError = new ApiValidationError(ex.getAppStatus(), ex.getValidationFieldErrors());
        LOGGER.log(Level.ERROR, "error", ex);
        return new ResponseEntity(apiValidationError, new HttpHeaders(), apiValidationError.getHttpStatus());
    }
    
    @ExceptionHandler({ ModelMappingException.class })
    public ResponseEntity<Object> handleAll(ModelMappingException ex, WebRequest request) {
        ApiError apiError = new ApiError(ex.getAppStatus(), ex.getMessage(), this.getCause(ex)); //Arrays.toString(ex.getStackTrace())
        LOGGER.log(Level.ERROR, "error", ex);
        return new ResponseEntity(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }
    
    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<Object> handleAll(AccessDeniedException ex, WebRequest request) {
        ApiError apiError = new ApiError(ApiStatus.UNAUTHORIZED, ex.getMessage(), this.getCause(ex));
        LOGGER.log(Level.ERROR, "error", ex);
        return new ResponseEntity(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }
    
    @ExceptionHandler({ EmptyResultDataAccessException.class })
    public ResponseEntity<Object> handleAll(EmptyResultDataAccessException ex, WebRequest request) {
        ApiError apiError = new ApiError(ApiStatus.OBJECT_NOT_FOUND, ex.getMessage(), this.getCause(ex));
        return new ResponseEntity(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }
    
    @ExceptionHandler({ UnsupportedOperationException.class })
    public ResponseEntity<Object> handleAll(UnsupportedOperationException ex, WebRequest request) {
        ApiError apiError = new ApiError(ApiStatus.SERVICE_UNAVAILABLE, "Unsopported Operation");
        LOGGER.log(Level.ERROR, "error", ex);
        return new ResponseEntity(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }   
    
    @ExceptionHandler({ SQLException.class })
    public ResponseEntity<Object> handleAll(SQLException ex, WebRequest request) {
        ApiError apiError = new ApiError(ApiStatus.INTERNAL_ERROR, Arrays.toString(ex.getStackTrace()));
        apiError.putError(ex.getMessage());
        LOGGER.log(Level.ERROR, "error", ex);
        return new ResponseEntity(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }
    
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError(ApiStatus.INTERNAL_ERROR, ex.getLocalizedMessage(), Arrays.toString(ex.getStackTrace()));
        LOGGER.log(Level.ERROR, "error", ex);
        return new ResponseEntity(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }
    
    private String getCause(Exception ex){
        if(ex.getCause() != null){
            return ex.getCause().getMessage();
        }else{
            return ex.getMessage();
        }
    }

}
