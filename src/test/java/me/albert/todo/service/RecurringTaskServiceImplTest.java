package me.albert.todo.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Period;
import me.albert.todo.domain.RecurringTask;
import me.albert.todo.domain.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("반복 작업 관련 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class RecurringTaskServiceImplTest {

    @InjectMocks
    private RecurringTaskServiceImpl recurringTaskService;
    @Mock
    private TodoService todoService;

    @DisplayName("할일에 반복 작업을 업데이트한다.")
    @Test
    void updateRecurringTask() {
        // given
        Long todoId = 1L;
        Period recurrencePattern = Period.ofDays(1);
        String username = "username";
        Todo todo = mock(Todo.class);
        when(todoService.getTodoByIdAndUsername(todoId, username)).thenReturn(todo);

        // when
        recurringTaskService.updateRecurringTask(todoId, recurrencePattern, username);

        // then
        verify(todoService).getTodoByIdAndUsername(todoId, username);
        verify(todo).updateRecurringTask(any(RecurringTask.class));
    }

    @DisplayName("할일에 반복 작업을 삭제한다.")
    @Test
    void deleteRecurringTask() {
        // given
        Long todoId = 1L;
        String username = "username";
        Todo todo = mock(Todo.class);
        when(todoService.getTodoByIdAndUsername(todoId, username)).thenReturn(todo);

        // when
        recurringTaskService.deleteRecurringTask(todoId, username);

        // then
        verify(todoService).getTodoByIdAndUsername(todoId, username);
        verify(todo).removeRecurringTask();
    }
}
