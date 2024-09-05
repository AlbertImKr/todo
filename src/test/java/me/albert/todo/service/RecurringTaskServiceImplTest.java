package me.albert.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Period;
import java.util.Optional;
import me.albert.todo.domain.RecurringTask;
import me.albert.todo.domain.Todo;
import me.albert.todo.exception.BusinessException;
import me.albert.todo.repository.RecurringTaskRepository;
import me.albert.todo.service.dto.response.IdResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("반복 할 일 관련 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class RecurringTaskServiceImplTest {

    @InjectMocks
    private RecurringTaskServiceImpl recurringTaskService;
    @Mock
    private RecurringTaskRepository recurringTaskRepository;
    @Mock
    private TodoService todoService;

    @DisplayName("반복 할 일 생성 성공 시 IdResponse를 반환한다.")
    @Test
    void create_recurring_task_success() {
        // given
        Long todoId = 1L;
        Period period = Period.ofDays(1);
        String username = "username";
        Todo todo = mock(Todo.class);
        var expectId = 1L;
        var expect = new IdResponse(expectId);
        RecurringTask recurringTask = mock(RecurringTask.class);
        when(todoService.getTodoByIdAndUsername(todoId, username)).thenReturn(todo);
        when(recurringTaskRepository.save(any())).thenReturn(recurringTask);
        when(recurringTask.getId()).thenReturn(expectId);

        // when
        IdResponse idResponse = recurringTaskService.createRecurringTask(todoId, period, username);

        // then
        assertThat(idResponse).isEqualTo(expect);
    }

    @DisplayName("반복 할 일 수정 성공 시 예외가 발생하지 않는다.")
    @Test
    void update_recurring_task_success() {
        // given
        Long recurringTaskId = 1L;
        var todoId = 1L;
        Period period = Period.ofDays(1);
        String username = "username";
        RecurringTask recurringTask = mock(RecurringTask.class);
        var todo = mock(Todo.class);
        when(todoService.getTodoByIdAndUsername(todoId, username)).thenReturn(todo);
        when(recurringTaskRepository.findByIdAndTask(recurringTaskId, todo)).thenReturn(Optional.of(recurringTask));

        // when, then
        recurringTaskService.updateRecurringTask(recurringTaskId, period, todoId, username);
    }

    @DisplayName("반복 할 일 수정 실패 시 예외가 발생한다.")
    @Test
    void update_recurring_task_fail() {
        // given
        Long recurringTaskId = 1L;
        var todoId = 1L;
        Period period = Period.ofDays(1);
        String username = "username";
        var todo = mock(Todo.class);
        when(todoService.getTodoByIdAndUsername(todoId, username)).thenReturn(todo);
        when(recurringTaskRepository.findByIdAndTask(recurringTaskId, todo)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> recurringTaskService.updateRecurringTask(recurringTaskId, period, todoId, username))
                .isInstanceOf(BusinessException.class);
    }

    @DisplayName("반복 할 일 삭제 성공 시 예외가 발생하지 않는다.")
    @Test
    void delete_recurring_task_success() {
        // given
        Long recurringTaskId = 1L;
        String username = "username";
        Long todoId = 1L;
        RecurringTask recurringTask = mock(RecurringTask.class);
        var todo = mock(Todo.class);
        when(todoService.getTodoByIdAndUsername(todoId, username)).thenReturn(todo);
        when(recurringTaskRepository.findByIdAndTask(recurringTaskId, todo)).thenReturn(Optional.of(recurringTask));

        // when
        recurringTaskService.deleteRecurringTask(recurringTaskId, todoId, username);

        // then
        verify(recurringTaskRepository).delete(recurringTask);
    }

    @DisplayName("반복 할 일 삭제 실패 시 예외가 발생한다.")
    @Test
    void delete_recurring_task_fail() {
        // given
        Long recurringTaskId = 1L;
        String username = "username";
        Long todoId = 1L;
        var todo = mock(Todo.class);
        when(todoService.getTodoByIdAndUsername(todoId, username)).thenReturn(todo);
        when(recurringTaskRepository.findByIdAndTask(recurringTaskId, todo)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> recurringTaskService.deleteRecurringTask(recurringTaskId, todoId, username))
                .isInstanceOf(BusinessException.class);
    }
}
