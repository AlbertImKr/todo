package me.albert.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import me.albert.todo.domain.Account;
import me.albert.todo.domain.Todo;
import me.albert.todo.domain.TodoStatus;
import me.albert.todo.exception.BusinessException;
import me.albert.todo.repository.TodoRepository;
import me.albert.todo.service.dto.request.TodoCreateRequest;
import me.albert.todo.service.dto.request.TodoUpdateRequest;
import me.albert.todo.utils.ErrorMessages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("할 일 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class TodoServiceImplTest {

    @InjectMocks
    private TodoServiceImpl todoService;

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private AccountService accountService;

    @DisplayName("할 일 생성 성공 시 할 일의 ID를 반환한다.")
    @Test
    void create_todo() {
        // given
        var request = new TodoCreateRequest("title", "description", LocalDateTime.now().plusDays(1));
        var todo = mock(Todo.class);
        when(accountService.findByUsername("username")).thenReturn(new Account());
        when(todoRepository.save(any())).thenReturn(todo);
        when(todo.getId()).thenReturn(1L);

        // when
        var response = todoService.create(request, "username");

        // then
        assertThat(response.id()).isEqualTo(1L);
    }

    @DisplayName("할 일 수정 성공 시 예외가 발생하지 않는다.")
    @Test
    void update_todo() {
        // given
        var request = new TodoUpdateRequest(
                "title", "description", LocalDateTime.now().plusDays(1), TodoStatus.COMPLETED);
        var todo = new Todo(
                "title", "description", LocalDateTime.now(), new Account(), LocalDateTime.now(),
                LocalDateTime.now(), null
        );
        var account = new Account();
        when(accountService.findByUsername("username")).thenReturn(account);
        var todoId = 1L;
        when(todoRepository.findByIdAndGroupNull(todoId)).thenReturn(Optional.of(todo));

        // when
        todoService.update(request, todoId, "username");
    }

    @DisplayName("할 일 수정 시 할 일을 찾을 수 없는 경우 예외가 발생한다.")
    @Test
    void update_todo_not_found() {
        // given
        var request = new TodoUpdateRequest(
                "title", "description", LocalDateTime.now().plusDays(1), TodoStatus.COMPLETED);
        var account = new Account();
        when(accountService.findByUsername("username")).thenReturn(account);
        var todoId = 1L;
        when(todoRepository.findByIdAndGroupNull(todoId)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> todoService.update(request, todoId, "username"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.TODO_NOT_FOUND);
    }

    @DisplayName("할 일 삭제 성공 시 예외가 발생하지 않는다.")
    @Test
    void delete_todo_if_success() {
        // given
        var todoId = 1L;
        var account = new Account(1L);
        var todo = new Todo(
                "title", "description", LocalDateTime.now(), account, LocalDateTime.now(),
                LocalDateTime.now(), null
        );
        when(accountService.findByUsername("username")).thenReturn(account);
        when(todoRepository.findByIdAndGroupNull(todoId)).thenReturn(Optional.of(todo));

        // when
        todoService.delete(todoId, "username");
    }

    @DisplayName("할 일 삭제 시 할 일을 찾을 수 없는 경우 예외가 발생한다.")
    @Test
    void delete_todo_if_not_found() {
        // given
        var todoId = 1L;
        when(todoRepository.findByIdAndGroupNull(todoId)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> todoService.delete(todoId, "username"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.TODO_NOT_FOUND);
    }

    @DisplayName("할 일 삭제 시 권한이 없는 경우 예외가 발생한다.")
    @Test
    void delete_todo_if_not_allowed() {
        // given
        var todoId = 1L;
        var account = new Account(1L);
        var otherAccount = new Account(2L);
        var todo = new Todo(
                "title", "description", LocalDateTime.now(), otherAccount, LocalDateTime.now(),
                LocalDateTime.now(), null
        );
        when(accountService.findByUsername("username")).thenReturn(account);
        when(todoRepository.findByIdAndGroupNull(todoId)).thenReturn(Optional.of(todo));

        // when, then
        assertThatThrownBy(() -> todoService.delete(todoId, "username"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.TODO_DELETE_NOT_ALLOWED);
    }

    @DisplayName("할 일 조회 성공 시 할 일 정보를 반환한다.")
    @Test
    void get_todo() {
        // given
        var todo = new Todo(
                "title", "description", LocalDateTime.now(), new Account(), LocalDateTime.now(),
                LocalDateTime.now(), TodoStatus.PENDING
        );
        var account = new Account();
        when(accountService.findByUsername("username")).thenReturn(account);
        when(todoRepository.findByIdAndOwner(1L, account)).thenReturn(Optional.of(todo));

        // when
        var response = todoService.get(1L, "username");

        // then
        assertThat(response.title()).isEqualTo("title");
        assertThat(response.description()).isEqualTo("description");
    }

    @DisplayName("할 일 조회 시 할 일을 찾을 수 없는 경우 예외가 발생한다.")
    @Test
    void get_todo_not_found() {
        // given
        var account = new Account();
        when(accountService.findByUsername("username")).thenReturn(account);
        when(todoRepository.findByIdAndOwner(1L, account)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> todoService.get(1L, "username"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.TODO_NOT_FOUND);
    }

    @DisplayName("할 일 상태 변경 성공 시 예외가 발생하지 않는다.")
    @Test
    void update_todo_status() {
        // given
        var todo = new Todo(
                "title", "description", LocalDateTime.now(), new Account(), LocalDateTime.now(),
                LocalDateTime.now(), TodoStatus.PENDING
        );
        var account = new Account();
        when(accountService.findByUsername("username")).thenReturn(account);
        when(todoRepository.findByIdAndOwner(1L, account)).thenReturn(Optional.of(todo));

        // when
        todoService.updateStatus(1L, TodoStatus.COMPLETED, "username");
    }

    @DisplayName("할 일 상태 변경 시 할 일을 찾을 수 없는 경우 예외가 발생한다.")
    @Test
    void update_todo_status_not_found() {
        // given
        var account = new Account();
        when(accountService.findByUsername("username")).thenReturn(account);
        when(todoRepository.findByIdAndOwner(1L, account)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> todoService.updateStatus(1L, TodoStatus.COMPLETED, "username"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.TODO_NOT_FOUND);
    }

    @DisplayName("할 일 ID로 조회 성공 시 할 일 정보를 반환한다.")
    @Test
    void get_todo_by_id() {
        // given
        var todo = new Todo(
                "title", "description", LocalDateTime.now(), new Account(), LocalDateTime.now(),
                LocalDateTime.now(), TodoStatus.PENDING
        );
        var username = "username";
        var account = new Account();
        when(accountService.findByUsername(username)).thenReturn(account);
        when(todoRepository.findByIdAndOwner(1L, account)).thenReturn(Optional.of(todo));

        // when
        var response = todoService.getTodoByIdAndUsername(1L, username);

        // then
        assertThat(response).isEqualTo(todo);
    }

    @DisplayName("할 일 ID로 조회 시 할 일을 찾을 수 없는 경우 예외가 발생한다.")
    @Test
    void get_todo_by_id_not_found() {
        // given
        var username = "username";
        var account = new Account();
        when(accountService.findByUsername(username)).thenReturn(account);
        when(todoRepository.findByIdAndOwner(1L, account)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> todoService.getTodoByIdAndUsername(1L, username))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.TODO_NOT_FOUND);
    }

    @DisplayName("할 일에 사용자를 할당 성공 시 예외가 발생하지 않는다.")
    @Test
    void assign_user() {
        // given
        var todo = new Todo(
                "title", "description", LocalDateTime.now(), new Account(), LocalDateTime.now(),
                LocalDateTime.now(), TodoStatus.PENDING
        );
        var account = new Account();
        when(accountService.findByUsername("username")).thenReturn(account);
        when(accountService.findByUsername("currentUsername")).thenReturn(account);
        when(todoRepository.findByIdAndOwner(1L, account)).thenReturn(Optional.of(todo));

        // when
        todoService.assignUser(1L, "username", "currentUsername");
    }

    @DisplayName("할 일에 사용자를 할당 시 할 일을 찾을 수 없는 경우 예외가 발생한다.")
    @Test
    void assign_user_not_found() {
        // given
        var account = new Account();
        when(accountService.findByUsername("currentUsername")).thenReturn(account);
        when(todoRepository.findByIdAndOwner(1L, account)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> todoService.assignUser(1L, "username", "currentUsername"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.TODO_NOT_FOUND);
    }

    @DisplayName("할 일에 사용자를 해제 성공 시 예외가 발생하지 않는다.")
    @Test
    void unassign_user() {
        // given
        var todo = new Todo(
                "title", "description", LocalDateTime.now(), new Account(), LocalDateTime.now(),
                LocalDateTime.now(), TodoStatus.PENDING
        );
        var account = new Account();
        when(accountService.findByUsername("username")).thenReturn(account);
        when(accountService.findByUsername("currentUsername")).thenReturn(account);
        when(todoRepository.findByIdAndOwner(1L, account)).thenReturn(Optional.of(todo));

        // when
        todoService.unassignUser(1L, "username", "currentUsername");
    }

    @DisplayName("할 일에 사용자를 해제 시 할 일을 찾을 수 없는 경우 예외가 발생한다.")
    @Test
    void unassign_user_not_found() {
        // given
        var account = new Account();
        when(accountService.findByUsername("currentUsername")).thenReturn(account);
        when(todoRepository.findByIdAndOwner(1L, account)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> todoService.unassignUser(1L, "username", "currentUsername"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.TODO_NOT_FOUND);
    }

    @DisplayName("할 일 아이디 목록으로 사용자가 소유한 할 일 목록을 조회한다.")
    @Test
    void find_all_by_ids_and_owner() {
        // given
        var todo1 = new Todo(
                "title1", "description1", LocalDateTime.now(), new Account(), LocalDateTime.now(),
                LocalDateTime.now(), TodoStatus.PENDING
        );
        var todo2 = new Todo(
                "title2", "description2", LocalDateTime.now(), new Account(), LocalDateTime.now(),
                LocalDateTime.now(), TodoStatus.PENDING
        );
        var account = new Account();
        when(accountService.findByUsername("username")).thenReturn(account);
        when(todoRepository.findAllByIdInAndOwner(List.of(1L, 2L), account)).thenReturn(List.of(todo1, todo2));

        // when
        var response = todoService.findAllByIdInAndOwner(List.of(1L, 2L), "username");

        // then
        assertThat(response).containsExactly(todo1, todo2);
    }
}
