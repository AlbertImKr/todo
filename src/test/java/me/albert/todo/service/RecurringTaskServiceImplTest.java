package me.albert.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Period;
import me.albert.todo.domain.RecurringTask;
import me.albert.todo.domain.Todo;
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

}
