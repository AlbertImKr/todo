package me.albert.todo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import me.albert.todo.exception.BusinessException;
import me.albert.todo.utils.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("프로젝트 도메인 테스트")
class ProjectTest {

    Project project;
    Account account;

    @BeforeEach
    void setUp() {
        account = new Account(1L);
        project = new Project(1L, account);
    }

    @DisplayName("그룹 검증 시 성공하면 아무것도 반환하지 않는다")
    @Test
    void validate_group() {
        // given
        var group = new Group(1L);
        project.assignGroup(group);

        // when
        project.validateGroup(group);
    }

    @DisplayName("그룹 검증 시 그룹이 null이면 예외를 발생한다")
    @Test
    void validate_group_fail_when_group_is_null() {
        // given
        var group = new Group(2L);

        // when & then
        assertThatThrownBy(() -> project.validateGroup(group))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.PROJECT_GROUP_NOT_MATCHED);
    }

    @DisplayName("그룹 검증 시 그룹이 다르면 예외를 발생한다")
    @Test
    void validate_group_fail_when_group_is_different() {
        // given
        var group = new Group(1L);
        project.assignGroup(group);
        var anotherGroup = new Group(2L);

        // when & then
        assertThatThrownBy(() -> project.validateGroup(anotherGroup))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.PROJECT_GROUP_NOT_MATCHED);
    }

    @DisplayName("프로젝트에 할당한 할 일을 해제한다")
    @Test
    void unassign_todos() {
        // given
        var todo1 = new Todo(1L);
        var todo2 = new Todo(2L);
        project.assignTodos(List.of(todo1, todo2));

        // when
        project.unassignTodos(List.of(todo1));

        // then
        assertThat(project.getTodos()).containsExactly(todo2);
    }

    @DisplayName("프로젝트에 할 일들을 할당한다")
    @Test
    void assign_todos() {
        // given
        var todo = new Todo();

        // when
        project.assignTodos(List.of(todo));

        // then
        assertThat(project.getTodos()).contains(todo);
    }

    @DisplayName("프로젝트를 할당 받은 할 일은 다시 할당하지 않는다")
    @Test
    void assign_todos_twice() {
        // given
        var todo = new Todo();
        project.assignTodos(List.of(todo));

        // when
        project.assignTodos(List.of(todo));

        // then
        assertThat(project.getTodos()).size().isEqualTo(1);
    }

    @DisplayName("프로젝트를 수정한다")
    @Test
    void update_project() {
        // given
        var name = "프로젝트";

        // when
        project.update(name, account);

        // then
        assertThat(project.getName()).isEqualTo(name);
    }

    @DisplayName("프로젝트를 수정 시 계정이 다르면 예외를 발생한다")
    @Test
    void update_project_with_different_account() {
        // given
        var name = "프로젝트";
        var anotherAccount = new Account(2L);

        // when & then
        assertThatThrownBy(() -> project.update(name, anotherAccount))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.PROJECT_UPDATE_NOT_ALLOWED);
    }

    @DisplayName("프로젝트 소유자이면 true를 반환한다")
    @Test
    void is_owner() {
        // when
        var result = project.isOwner(account);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("프로젝트 소유자가 아니면 false를 반환한다")
    @Test
    void is_not_owner() {
        // given
        var otherAccount = new Account(2L);

        // when
        var result = project.isOwner(otherAccount);

        // then
        assertThat(result).isFalse();
    }

}
