package me.albert.todo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.albert.todo.controller.dto.request.SearchTagRequest;
import me.albert.todo.controller.dto.request.TagCreateRequest;
import me.albert.todo.service.TagService;
import me.albert.todo.service.dto.response.IdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TagController {

    private final TagService tagService;

    /**
     * 태그를 생성 API
     *
     * @param request 태그 생성 요청
     * @return 생성된 태그의 ID
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/tags")
    public IdResponse createTag(@Valid @RequestBody TagCreateRequest request) {
        return tagService.createTag(request.name());
    }

    /**
     * 태그 이름으로 태그를 조회하는 API
     *
     * @param request 태그 이름
     * @return 태그 ID
     */
    @GetMapping("/tags")
    public IdResponse getTagByName(@Valid @ModelAttribute SearchTagRequest request) {
        return tagService.getTagByName(request.name());
    }
}
