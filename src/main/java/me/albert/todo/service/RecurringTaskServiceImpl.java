package me.albert.todo.service;

import java.time.Duration;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import me.albert.todo.domain.RecurringTask;
import me.albert.todo.domain.Todo;
import me.albert.todo.repository.RecurringTaskRepository;
import me.albert.todo.service.dto.response.IdResponse;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecurringTaskServiceImpl implements RecurringTaskService {

    private final RecurringTaskRepository recurringTaskRepository;
    private final TodoService todoService;

    @Override
    public IdResponse createRecurringTask(Long todoId, Duration duration, String username) {
        Todo todo = todoService.getTodoByIdAndUsername(todoId, username);
        LocalDateTime now = LocalDateTime.now();
        RecurringTask recurringTask = new RecurringTask(todo, duration, now.plus(duration));
        RecurringTask savedRecurringTask = recurringTaskRepository.save(recurringTask);
        return new IdResponse(savedRecurringTask.getId());
    }
}
