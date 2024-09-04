package me.albert.todo.exception;

import org.springframework.http.HttpStatusCode;

public class BusinessException extends RuntimeException {

    private final HttpStatusCode httpStatusCode;

    public BusinessException(String message, HttpStatusCode httpStatusCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }
}
