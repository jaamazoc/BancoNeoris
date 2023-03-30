package com.neoris.banco.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.neoris.banco.service.ClienteServiceImpl;

@RestControllerAdvice
public class ControllerExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorMessage> resourceNotFoundException(CustomException ex, WebRequest request) {
		logger.error(ex.getMessage(), ex);
		ErrorMessage message = new ErrorMessage(
			ex.getHttpStatus().value(),
			new Date(),
			ex.getMessage(),
			request.getDescription(false));
		
		return new ResponseEntity<ErrorMessage>(message, ex.getHttpStatus());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
		logger.error(ex.getMessage(), ex);
		ErrorMessage message = new ErrorMessage(
			HttpStatus.INTERNAL_SERVER_ERROR.value(),
			new Date(),
			ex.getMessage(),
			request.getDescription(false));
			
			return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
