package me.albert.todo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        account = new Account(1L);
        var name = "group";
        var description = "description";
        var createdAt = LocalDateTime.now().minusDays(1);
        var updatedAt = LocalDateTime.now().minusDays(1);
        group = new Group(name, description, account, createdAt, updatedAt);
    }

    @DisplayName("프로젝트를 추가한다")
    @Test
    void add_project() {
        // given
        var project = new Project(1L);

        // when
        group.addProject(project);

        // then
        assertThat(group.getProjects()).contains(project);
    }

    @DisplayName("그룹의 멤버가 맞으면 권한이 있다")
    @Test
    void validate_permission_if_has() {
        // when
        group.validatePermission(account);
    }

    @DisplayName("그룹의 소유자 및 멤버가 아니면 권한이 없다")
    @Test
    void validate_permission_if_not_owner_and_not_member() {
        // given
        var otherAccount = new Account(2L);

        // when, then
        assertThatThrownBy(() -> group.validatePermission(otherAccount))
                .isInstanceOf(BusinessException.class);
    }

    @DisplayName("그룹의 소유자이면 권한이 있다")
    @Test
    void validate_permission_if_owner() {
        // when
        group.validatePermission(account);
    }

    @DisplayName("그룹 할일에 할당한 멤버를 제거한다")
    @Test
    void unassign_todo() {
        // given
        var todo = new Todo();
        group.assignTodos(account, List.of(todo));

        // when
        group.unassignTodoFromUsers(todo, List.of(account));

        // then
        assertThat(todo.getAssignees()).isEmpty();
    }

    @DisplayName("그룹 할일에 할당한 멤버를 제거할 때 할일이 없으면 예외가 발생한다")
    @Test
    void unassign_todo_if_not_contains_todo() {
        // given
        var todo = new Todo();

        // when, then
        assertThatThrownBy(() -> group.unassignTodoFromUsers(todo, List.of(account)))
                .isInstanceOf(BusinessException.class);
    }


    @DisplayName("그룹 할일을 그룹 멤버에게 할당한다")
    @Test
    void assign_todo() {
        // given
        var todo = new Todo();
        group.assignTodos(account, List.of(todo));

        // when
        group.assignTodoToUsers(todo, List.of(account));

        // then
        assertThat(todo.getAssignees()).contains(account);
    }

    @DisplayName("그룹에 포함하지 않은 할일을 그룹 맴버에게 할당하면 예외가 발생한다")
    @Test
    void assign_todo_if_not_contains_todo() {
        // given
        var todo = new Todo();

        // when, then
        assertThatThrownBy(() -> group.assignTodoToUsers(todo, List.of(account)))
                .isInstanceOf(BusinessException.class);
    }

    @DisplayName("그룹 할일을 그룹 맴버가 아닌 사람에게 할당하면 할당 실패한다")
    @Test
    void assign_todo_if_not_group_member() {
        // given
        var todo = new Todo();
        group.assignTodos(account, List.of(todo));
        var otherAccount = new Account(2L);
        group.addAccounts(account, List.of(otherAccount));

        // when
        group.assignTodoToUsers(todo, List.of(otherAccount));

        // then
        assertThat(todo.getAssignees()).isEmpty();
    }

    @DisplayName("그룹에 포함된 유저인지 확인한다")
    @Test
    void contains_user() {
        // given
        var account1 = new Account(2L);
        var account2 = new Account(3L);
        group.addAccounts(account, List.of(account1, account2));

        // when
        var result = group.isMember(account1);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("그룹에 포함되지 않은 유저인지 확인한다")
    @Test
    void not_contains_user() {
        // given
        var account1 = new Account(2L);
        var account2 = new Account(3L);
        group.addAccounts(account, List.of(account1, account2));

        // when
        var result = group.isMember(new Account(4L));

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("그룹에서 유저를 제거 한다")
    @Test
    void remove_user() {
        // given
        var account1 = new Account(2L);
        var account2 = new Account(3L);
        var accounts = List.of(account1, account2);
        group.addAccounts(account, accounts);

        // when
        group.removeAccounts(account, List.of(account1));

        // then
        assertThat(group.getUsers()).containsExactly(account2);
    }

    @DisplayName("그룹에서 유저를 제거할 때 권한이 없으면 예외가 발생한다")
    @Test
    void remove_user_if_not_group_owner() {
        // given
        var account1 = new Account(2L);
        var account2 = new Account(3L);
        var accounts = List.of(account1, account2);
        group.addAccounts(account, accounts);

        // when, then
        assertThatThrownBy(() -> group.removeAccounts(account2, List.of(account1)))
                .isInstanceOf(BusinessException.class);
    }

    @DisplayName("그룹에 유저를 추가한다")
    @Test
    void add_user() {
        // given
        var account1 = new Account(2L);
        var account2 = new Account(3L);
        var accounts = List.of(account1, account2);

        // when
        group.addAccounts(account, accounts);

        // then
        assertThat(group.getUsers()).contains(account1, account2);
    }

    @DisplayName("그룹에 유저를 추가할 때 권한이 없으면 예외가 발생한다")
    @Test
    void add_user_if_not_group_owner() {
        // given
        var account1 = new Account(2L);
        var account2 = new Account(3L);
        var accounts = List.of(account1, account2);

        // when, then
        assertThatThrownBy(() -> group.addAccounts(account2, accounts))
                .isInstanceOf(BusinessException.class);
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
        var otherAccount = new Account(2L);

        // when, then
        assertThatThrownBy(() -> group.assignTodos(otherAccount, List.of(todo)))
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

    @DisplayName("할 일 목록을 데이터를 조회한다")
    @Test
    void get_todo_list() {
        // given
        var now = LocalDateTime.now();
        var todo = new Todo(
                "todo", "description", now, account, now, now, TodoStatus.PENDING, TodoPriority.MEDIUM);
        group.assignTodos(account, List.of(todo));

        // when
        var todoList = group.getTodos();

        // then
        assertThat(todoList).hasSize(1);
    }
}
