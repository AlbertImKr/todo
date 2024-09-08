package me.albert.todo.service;

import java.time.LocalDateTime;
import java.time.Period;
import lombok.RequiredArgsConstructor;
import me.albert.todo.domain.RecurringTask;
import me.albert.todo.domain.Todo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RecurringTaskServiceImpl implements RecurringTaskService {

    private final TodoService todoService;

    @Transactional
    @Override
    public void updateRecurringTask(Long todoId, Period recurrencePattern, String username) {
        Todo todo = todoService.getTodoByIdAndUsername(todoId, username);
        LocalDateTime nextOccurrence = LocalDateTime.now().plus(recurrencePattern);
        RecurringTask recurringTask = new RecurringTask(recurrencePattern, nextOccurrence);
        todo.updateRecurringTask(recurringTask);
    }

    @Transactional
    @Override
    public void deleteRecurringTask(Long todoId, String username) {
        Todo todo = todoService.getTodoByIdAndUsername(todoId, username);
        todo.removeRecurringTask();
    }
}
