package com.casestudy.skilltracker.employee.exception;

import com.casestudy.skilltracker.employee.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = {ValidationException.class,UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> validationException(Exception e) {
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            response.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        log.error(e.getMessage());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> unhandedException(Exception e) {
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
