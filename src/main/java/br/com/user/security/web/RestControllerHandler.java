package br.com.user.security.web;

import br.com.user.security.controller.response.ErrorResponse;
import br.com.user.security.exception.UserAlreadyExistsException;
import br.com.user.security.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.util.stream.Collectors.joining;

@Slf4j
@RestControllerAdvice
public class RestControllerHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(UserNotFoundException userNotFoundException) {
        log.error(userNotFoundException.getMessage(), userNotFoundException);
        return new ResponseEntity<>(
                new ErrorResponse(userNotFoundException.getExternalErrorCode(), HttpStatus.NOT_FOUND.value(), userNotFoundException.getErrorCode(), userNotFoundException.getErrorMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCustomerAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException) {
        log.error(userAlreadyExistsException.getMessage(), userAlreadyExistsException);
        return new ResponseEntity<>(
                new ErrorResponse(userAlreadyExistsException.getExternalErrorCode(), HttpStatus.CONFLICT.value(), userAlreadyExistsException.getErrorCode(), userAlreadyExistsException.getErrorMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        log.error(methodArgumentNotValidException.getMessage(), methodArgumentNotValidException);

        String invalidFields = methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(joining(","));

        return new ResponseEntity<>(
                new ErrorResponse("INVALID_REQUEST", HttpStatus.BAD_REQUEST.value(), "INVALID_REQUEST", invalidFields), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleExceptionBadCredentials(Exception exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(
                new ErrorResponse("BAD_CREDENTIALS", HttpStatus.UNAUTHORIZED.value(), "BAD_CREDENTIALS", exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(
                new ErrorResponse("INTERNAL_ERROR", HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_ERROR", exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
