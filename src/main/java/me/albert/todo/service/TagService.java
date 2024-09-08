package me.albert.todo.service;

import me.albert.todo.service.dto.response.IdResponse;

public interface TagService {

    /**
     * 태그를 생성한다.
     *
     * @param name 태그 이름
     * @return 생성된 태그의 ID
     * @throws me.albert.todo.exception.BusinessException 이미 존재하는 태그인 경우
     */
    IdResponse createTag(String name);

    /**
     * 태그 이름으로 태그를 조회한다.
     *
     * @param name 태그 이름
     * @return 태그 ID
     * @throws me.albert.todo.exception.BusinessException 존재하지 않는 태그인 경우
     */
    IdResponse getTagByName(String name);
}
