package me.albert.todo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("할 일 관련 도메인 테스트")
class TodoTest {

    Todo todo;

    @BeforeEach
    void setUp() {
        todo = new Todo(
                "title", "description", LocalDateTime.now(), new Account(), LocalDateTime.now(),
                LocalDateTime.now(), TodoStatus.PENDING
        );
    }

    @DisplayName("할일 수정이 정상적으로 이루어진다.")
    @Test
    void update_todo() {
        // given
        var title = "updated title";
        var description = "updated description";
        var dueDate = LocalDateTime.now();
        var status = TodoStatus.COMPLETED;
        var updatedAt = LocalDateTime.now();

        // when
        todo.update(title, description, dueDate, updatedAt, status);

        // then
        Assertions.assertAll(
                () -> assertThat(todo.getTitle()).isEqualTo(title),
                () -> assertThat(todo.getDescription()).isEqualTo(description),
                () -> assertThat(todo.getDueDate()).isEqualTo(dueDate),
                () -> assertThat(todo.getStatus()).isEqualTo(status),
                () -> assertThat(todo.getUpdatedAt()).isEqualTo(updatedAt)
        );
    }

    @DisplayName("할일 수정 시 상태가 변경되면 상태 변경일이 업데이트된다.")
    @Test
    void update_todo_status() {
        // given
        var status = TodoStatus.COMPLETED;
        var updatedAt = LocalDateTime.now();

        // when
        todo.updateStatus(status, updatedAt);

        // then
        Assertions.assertAll(
                () -> assertThat(todo.getStatus()).isEqualTo(status),
                () -> assertThat(todo.getUpdatedAt()).isEqualTo(updatedAt)
        );
    }

    @DisplayName("할일에 사용자를 할당한다.")
    @Test
    void assign_user() {
        // given
        var assignee = new Account();

        // when
        todo.assignUser(assignee);

        // then
        assertThat(todo.containsAssignee(assignee)).isTrue();
    }

    @DisplayName("할일에 사용자를 중복해서 할당하면 중복으로 할당되지 않는다.")
    @Test
    void assign_user_duplicate() {
        // given
        var assignee = new Account();
        todo.assignUser(assignee);

        // when
        todo.assignUser(assignee);

        // then
        assertThat(todo.getAssignees()).hasSize(1);
    }

    @DisplayName("할일에 여러 사용자를 할당한다.")
    @Test
    void assign_multiple_users() {
        // given
        var assignee1 = new Account();
        var assignee2 = new Account();

        // when
        todo.assignUser(assignee1);
        todo.assignUser(assignee2);

        // then
        assertThat(todo.getAssignees()).hasSize(2);
    }

    @DisplayName("할일의 ID가 같으면 같은 할일로 판단한다.")
    @Test
    void equals_todo() {
        // given
        var todo1 = new Todo();
        var todo2 = new Todo();

        // then
        assertThat(todo1).isEqualTo(todo2);
    }
}
