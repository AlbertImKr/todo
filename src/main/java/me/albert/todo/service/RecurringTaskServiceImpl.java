package me.albert.todo.service;

import java.time.LocalDateTime;
import java.time.Period;
import lombok.RequiredArgsConstructor;
import me.albert.todo.domain.RecurringTask;
import me.albert.todo.domain.Todo;
import me.albert.todo.exception.BusinessException;
import me.albert.todo.repository.RecurringTaskRepository;
import me.albert.todo.service.dto.response.IdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RecurringTaskServiceImpl implements RecurringTaskService {

    public static final String RECURRING_TASK_NOT_FOUND = "해당 반복 작업이 존재하지 않습니다";

    private final RecurringTaskRepository recurringTaskRepository;
    private final TodoService todoService;

    @Transactional
    @Override
    public IdResponse createRecurringTask(Long todoId, Period recurrencePattern, String username) {
        Todo todo = todoService.getTodoByIdAndUsername(todoId, username);
        LocalDateTime now = LocalDateTime.now();
        RecurringTask recurringTask = new RecurringTask(todo, recurrencePattern, now.plus(recurrencePattern));
        RecurringTask savedRecurringTask = recurringTaskRepository.save(recurringTask);
        return new IdResponse(savedRecurringTask.getId());
    }

    @Transactional
    @Override
    public void updateRecurringTask(Long recurringTaskId, Period recurrencePattern, String username) {
        RecurringTask recurringTask = recurringTaskRepository.findById(recurringTaskId)
                .orElseThrow(() -> new BusinessException(RECURRING_TASK_NOT_FOUND, HttpStatus.NOT_FOUND));
        LocalDateTime updatedAt = LocalDateTime.now();
        recurringTask.updatePeriod(recurrencePattern, updatedAt);
    }
}
