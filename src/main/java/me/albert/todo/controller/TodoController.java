package me.albert.todo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.albert.todo.service.TodoService;
import me.albert.todo.service.dto.request.TodoCreateRequest;
import me.albert.todo.service.dto.response.IdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TodoController {

    private final TodoService todoService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/todos")
    public IdResponse create(@Valid @RequestBody TodoCreateRequest request, @CurrentUsername String username) {
        return todoService.create(request, username);
    }
}
