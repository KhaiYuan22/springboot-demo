package com.example.demo.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice //when have catch exception, will come here
public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalStateException.class)//when call IllegalStateException
	@ResponseBody//return to HTTP
	public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
		Map<String, String> error = new HashMap<>();// combine the error and error message
		error.put("error", ex.getMessage());//get the error message
		return ResponseEntity.badRequest().body(ex.getMessage());//return the error message to error 400
	}

	@ExceptionHandler(Exception.class) // when call exception
	public ResponseEntity<Map<String, String>> handleAllException(Exception ex) {
		Map<String, String> error = new HashMap<>();
		error.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);// return the error message to error 500
	}
}
