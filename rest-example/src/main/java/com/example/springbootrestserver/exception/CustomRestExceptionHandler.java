package com.example.springbootrestserver.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.springbootrestserver.controller.UserController;

@RestController
@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@ExceptionHandler(BeanNotFoundException.class)
	public final ResponseEntity<Object> handleNotFoundException(BeanNotFoundException ex, WebRequest request) {
		String error = ex.getMessage();
		ApiError apiError = new ApiError(new Date(), HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error,
				request.getDescription(false));

		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler(BadCredentialsException.class)
	public final ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
		String error = "Bad Credentials";
		ApiError apiError = new ApiError(new Date(), HttpStatus.FORBIDDEN, ex.getLocalizedMessage(), error,
				request.getDescription(false));

		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		ApiError apiError = new ApiError(new Date(), HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors,
				request.getDescription(false));
		return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}

	// pas de parametre
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " parameter is missing";

		ApiError apiError = new ApiError(new Date(), HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error,
				request.getDescription(false));
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

//	violations de contrainte
	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
		List<String> errors = new ArrayList<String>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
					+ violation.getMessage());
		}

		ApiError apiError = new ApiError(new Date(), HttpStatus.BAD_REQUEST,
				"could not execute statement:attribute error ", errors, request.getDescription(false));
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	// L’argument de la méthode n’est pas le type attendu
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		String type = ex.getRequiredType().getCanonicalName().toString();
		if (type.equals("java.lang.Long"))
			type = "Integer";
		if (type.equals("java.lang.String"))
			type = "String";
		String error = ex.getParameter().getParameterName() + " should be of type " + type;
		String errormessage = "used a wrong parameter type";

		ApiError apiError = new ApiError(new Date(), HttpStatus.BAD_REQUEST, error, errormessage,
				request.getDescription(false));
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	// when the data entered is missing a field
	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleJdbcSQLException(DataIntegrityViolationException ex, WebRequest request) {

		String error = ex.getCause().getLocalizedMessage();
		ApiError apiError = new ApiError(new Date(), HttpStatus.BAD_REQUEST, error,
				"the data entered is missing a field", request.getDescription(false));
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	//
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this kind of request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
		ApiError apiError = new ApiError(new Date(), HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(),
				builder.toString(), request.getDescription(false));
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		ApiError apiError = new ApiError(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(),
				"error occurred", request.getDescription(false));
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Malformed JSON request:the request cannot be fulfilled due to bad syntax";
		StringBuilder builder = new StringBuilder();
		builder.append(error);
		ApiError apiError = new ApiError(new Date(), HttpStatus.BAD_REQUEST, "please check entered information syntax",
				builder.toString(), request.getDescription(false));
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
}