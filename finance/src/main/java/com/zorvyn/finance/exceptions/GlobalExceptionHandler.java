package com.zorvyn.finance.exceptions;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
	public Map<String, Object> handleValidationException(Exception exception){
		
		Map<String, Object> errors = new HashMap<>();
		errors.put("Status", 400);
		errors.put("message", "Validation Failed");
		
		return errors;
	}
	
	@ExceptionHandler(RuntimeException.class)
	public Map<String, String> handleRuntime(RuntimeException exception){
		Map<String, String> error = new HashMap<>();
		error.put("error", exception.getMessage());
		
		return error;
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public Map<String, String> handleAccessDenied(AccessDeniedException exception){
		Map<String, String> error = new HashMap<>();
		error.put("error", exception.getMessage());
		
		return error;
	}
	
	

}
