package me.albert.todo.service;

import java.util.List;
import me.albert.todo.service.dto.response.GroupResponse;
import me.albert.todo.service.dto.response.IdResponse;
import org.springframework.data.domain.Pageable;

public interface GroupService {

    /**
     * 그룹을 생성합니다.
     *
     * @param name        그룹 이름
     * @param description 그룹 설명
     * @param username    사용자 이름
     * @return 생성된 그룹의 ID
     * @throws me.albert.todo.exception.BusinessException 사용자 이름이 존재하지 않을 경우 발생
     */
    IdResponse create(String name, String description, String username);

    /**
     * 그룹을 수정합니다.
     *
     * @param id          그룹 ID
     * @param name        그룹 이름
     * @param description 그룹 설명
     * @param username    사용자 이름
     * @throws me.albert.todo.exception.BusinessException 그룹이 존재하지 않거나 그룹의 소유자가 아닐 경우 발생
     * @throws me.albert.todo.exception.BusinessException 사용자 이름이 존재하지 않을 경우 발생
     */
    void update(long id, String name, String description, String username);

    /**
     * 사용자의 그룹 목록을 조회합니다.
     *
     * @param username 사용자 이름
     * @param pageable 페이지 정보
     */
    List<GroupResponse> list(String username, Pageable pageable);
}
