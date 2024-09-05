package me.albert.todo.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import me.albert.todo.controller.dto.response.BusinessErrorResponse;
import me.albert.todo.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BusinessErrorResponse> handleIllegalArgumentException(BusinessException e) {
        return ResponseEntity.status(e.getHttpStatusCode()).body(new BusinessErrorResponse(e.getMessage()));
    }

    @ResponseStatus(value = BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BusinessErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new BusinessErrorResponse(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}
