package com.example.pruebatecnica.exception;

import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.pruebatecnica.response.BaseResponse;

@EnableWebMvc
@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {	
	
	public GlobalExceptionHandler() {
		super();
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
		
		List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());
	
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString(), details));	
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<Object> handleNullPointerException(NullPointerException ex) {
		
		List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());
	
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString(), details));	
	}
	
	@ExceptionHandler(value = { Unauthorized.class })
	public ResponseEntity<Object> handleUnauthorizedException(Unauthorized ex, HttpHeaders headers, HttpStatus status, WebRequest request) {	
		
		List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());
	
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.error(HttpStatus.BAD_REQUEST, "Unauthorized", details));	
	}

	// handleHttpMediaTypeNotSupported : se dispara cuando el json es inválido
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
		
		List<String> details = new ArrayList<String>();
		
		
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        
        details.add(builder.toString()); 
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.error(HttpStatus.BAD_REQUEST, "Invalid JSON", details));	
	}
	
	// handleHttpMessageNotReadable : se dispara cuando el json está mal formado	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());
        		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.error(HttpStatus.BAD_REQUEST, "Malformed JSON request", details));	
	}
	
	// handleMethodArgumentNotValid : se dispara cuando el @valid falla	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> details = new ArrayList<String>();
		
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
		      details.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.error(HttpStatus.BAD_REQUEST, "Validation Errors", details));	
	}
	
	// handleMissingServletRequestParameter : se dispara cuando faltan parámetros
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
		
		List<String> details = new ArrayList<String>();
		details.add(ex.getParameterName() + " parameter is missing");
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.error(HttpStatus.BAD_REQUEST, "Missing Parameters", details));	
    }
	
	// handleMethodArgumentTypeMismatch : se dispara cuando los tipos de parámetros no encajan
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());
      		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.error(HttpStatus.BAD_REQUEST, "Mismatch Type", details));	
    }
	
	// handleConstraintViolationException : se dispara cuando el @validated falla
	@ExceptionHandler({ConstraintViolationException.class})
	public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
		
		List<String> details = new ArrayList<String>();		
		details.add(ex.getMessage());
		for (ConstraintViolation<?> cViolation : ex.getConstraintViolations()) {
			details.add(cViolation.getPropertyPath().toString() + ": " + cViolation.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.error(HttpStatus.BAD_REQUEST, "Constraint Violation", details));	
	}		
	
	// handleResourceNotFoundException : se dispara cuando el id no existe en la bd
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
       
		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());		
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString(), details));
	}
	
	// handleNoHandlerFoundException : se dispara cuando el handler método es inválido
	protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> details = new ArrayList<String>();
		details.add(String.format("No se encuentra el método para la url %s", ex.getHttpMethod(), ex.getRequestURL()));
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString(), details));        
    }
	
	// Exception general
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		
		List<String> details = new ArrayList<String>();
		details.add(ex.getLocalizedMessage());
				
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString(), details));	
	}	
}
