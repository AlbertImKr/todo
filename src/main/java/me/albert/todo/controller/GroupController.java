package me.albert.todo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.albert.todo.controller.dto.request.GroupRequest;
import me.albert.todo.service.GroupService;
import me.albert.todo.service.dto.response.IdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class GroupController {

    private final GroupService groupService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/groups")
    public IdResponse create(@Valid @RequestBody GroupRequest request, @CurrentUsername String username) {
        return groupService.create(request.name(), request.description(), username);
    }
}
