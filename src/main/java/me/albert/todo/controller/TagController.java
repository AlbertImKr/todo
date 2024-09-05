package me.albert.todo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.albert.todo.controller.dto.request.TagCreateRequest;
import me.albert.todo.service.TagService;
import me.albert.todo.service.dto.response.IdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TagController {

    private final TagService tagService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/tags")
    public IdResponse createTag(@Valid @RequestBody TagCreateRequest request) {
        return tagService.createTag(request.name());
    }
}
