package me.albert.todo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.util.List;
import me.albert.todo.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("그룹 도메인 테스트")
class GroupTest {

    Group group;
    Account account;

    @BeforeEach
    void setUp() {
        account = mock(Account.class);
        var name = "group";
        var description = "description";
        var createdAt = LocalDateTime.now().minusDays(1);
        var updatedAt = LocalDateTime.now().minusDays(1);
        group = new Group(name, description, account, createdAt, updatedAt);
    }

    @DisplayName("그룹의 소유자이면 true를 반환한다")
    @Test
    void is_owner_if_group_owner() {
        // when
        var result = group.isOwner(account);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("그룹의 소유자가 아니면 false를 반환한다")
    @Test
    void is_not_owner_if_not_group_owner() {
        // given
        var otherAccount = new Account();

        // when
        var result = group.isOwner(otherAccount);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("그룹의 소유자가 아닌데 그룹을 수정하려고 하면 예외가 발생한다")
    @Test
    void update_group_if_not_group_owner() {
        // given
        var otherAccount = new Account();
        var name = "new group";
        var description = "new description";

        // when, then
        assertThatThrownBy(() -> group.update(otherAccount, name, description, LocalDateTime.now()))
                .isInstanceOf(BusinessException.class);
    }

    @DisplayName("그룹의 소유자인데 그룹을 수정하면 그룹이 수정된다")
    @Test
    void update_group_if_group_owner() {
        // given
        var name = "new group";
        var description = "new description";

        // when
        group.update(account, name, description, LocalDateTime.now());

        // then
        assertThat(group.getName()).isEqualTo(name);
        assertThat(group.getDescription()).isEqualTo(description);
    }

    @DisplayName("할 일을 할당할 권한이 없으면 예외가 발생한다")
    @Test
    void assign_todos_if_not_group_owner() {
        // given
        var todo = new Todo();

        // when, then
        assertThatThrownBy(() -> group.assignTodos(mock(Account.class), List.of(todo)))
                .isInstanceOf(BusinessException.class);
    }

    @DisplayName("할 일을 할당할 권한이 있으면 할 일이 할당된다")
    @Test
    void assign_todos_if_group_owner() {
        // given
        var todo = new Todo();

        // when
        group.assignTodos(account, List.of(todo));

        // then
        assertThat(group.contains(todo)).isTrue();
    }

    @DisplayName("할 일을 해제할 권한이 없으면 예외가 발생한다")
    @Test
    void unassign_todos_if_not_group_owner() {
        // given
        var todo = new Todo();
        var otherAccount = new Account();

        // when, then
        assertThatThrownBy(() -> group.unassignTodos(otherAccount, List.of(todo)))
                .isInstanceOf(BusinessException.class);
    }

    @DisplayName("할 일을 해제할 권한이 있으면 할 일이 해제된다")
    @Test
    void unassign_todos_if_group_owner() {
        // given
        var todo = new Todo();
        group.assignTodos(account, List.of(todo));

        // when
        group.unassignTodos(account, List.of(todo));

        // then
        assertThat(group.contains(todo)).isFalse();
    }
}
