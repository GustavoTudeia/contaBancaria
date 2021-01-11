package br.com.gustavo.contaBancaria.service.exceptions.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import br.com.gustavo.contaBancaria.service.exceptions.DataIntegrityException;
import br.com.gustavo.contaBancaria.service.exceptions.ExceptionResponse;
import br.com.gustavo.contaBancaria.service.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class CustomizedResposeEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleAllException(Exception ex, WebRequest request){
		ExceptionResponse exceptionresponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(exceptionresponse, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleBadRequestException(Exception ex, WebRequest request){
		ExceptionResponse exceptionresponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(exceptionresponse, HttpStatus.NOT_FOUND);
		
	}
	
	
	@ExceptionHandler(DataIntegrityException.class)
	public final ResponseEntity<ExceptionResponse> invalidJwtAuthenticationException(Exception ex, WebRequest request){
		ExceptionResponse exceptionresponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(exceptionresponse, HttpStatus.BAD_REQUEST);
		
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<ExceptionResponse>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<ExceptionResponse> exceptionresponse = new ArrayList<ExceptionResponse>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	    	exceptionresponse.add(new ExceptionResponse(new Date(), error.getDefaultMessage(), ((FieldError) error).getField()));
	    });
	    
	    return new ResponseEntity<>(exceptionresponse, HttpStatus.BAD_REQUEST);
	}

}
