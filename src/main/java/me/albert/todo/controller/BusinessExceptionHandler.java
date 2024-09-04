package me.albert.todo.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import me.albert.todo.controller.dto.response.BusinessErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BusinessExceptionHandler {

    @ResponseStatus(value = BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public BusinessErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
        return new BusinessErrorResponse(e.getMessage());
    }

    @ResponseStatus(value = BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BusinessErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new BusinessErrorResponse(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

}
