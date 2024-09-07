package me.albert.todo.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.List;
import me.albert.todo.controller.dto.response.BusinessErrorResponse;
import me.albert.todo.exception.BusinessException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
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
        List<String> errors = e.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        String message = String.join(", ", errors);
        return new BusinessErrorResponse(message);
    }
}
