package com.example.project.handle.response;

import com.example.project.handle.request.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;


@RestController
@RestControllerAdvice
public class CustomizedResponseEntityException {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> allHandlerException(Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                new ExceptionResponse(ex.getMessage(), request.getDescription(false), LocalDateTime.now()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> allHandlerMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                            WebRequest request) {
        return new ResponseEntity<Object>(new Errorresponse(LocalDateTime.now(), ex.getBindingResult().getAllErrors()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> allHandlerBadRequestException(BadRequestException ex, WebRequest request) {
        return new ResponseEntity<Object>(
                new ExceptionResponse(ex.getMessage(), request.getDescription(false), LocalDateTime.now()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> handlerFuncionarioNotFoundException(ObjectNotFoundException ex, WebRequest request) {
        return new ResponseEntity<Object>(
                new ExceptionResponse(ex.getMessage(), request.getDescription(false), LocalDateTime.now()),
                HttpStatus.NOT_FOUND);
    }

}
