package me.albert.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import me.albert.todo.domain.Account;
import me.albert.todo.domain.Todo;
import me.albert.todo.domain.TodoStatus;
import me.albert.todo.exception.BusinessException;
import me.albert.todo.repository.TodoRepository;
import me.albert.todo.service.dto.request.TodoCreateRequest;
import me.albert.todo.service.dto.request.TodoUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void createTodo() {
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
        when(todoRepository.findByIdAndOwner(1L, account)).thenReturn(Optional.of(todo));

        // when
        todoService.update(request, 1L, "username");
    }

    @DisplayName("할 일 수정 시 할 일을 찾을 수 없는 경우 예외가 발생한다.")
    @Test
    void update_todo_not_found() {
        // given
        var request = new TodoUpdateRequest(
                "title", "description", LocalDateTime.now().plusDays(1), TodoStatus.COMPLETED);
        var account = new Account();
        when(accountService.findByUsername("username")).thenReturn(account);
        when(todoRepository.findByIdAndOwner(1L, account)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> todoService.update(request, 1L, "username"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(TodoServiceImpl.TODO_NOT_FOUND);
    }
}
