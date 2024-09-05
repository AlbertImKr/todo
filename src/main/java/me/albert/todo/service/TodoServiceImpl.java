package me.albert.todo.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import me.albert.todo.domain.Account;
import me.albert.todo.domain.Todo;
import me.albert.todo.domain.TodoStatus;
import me.albert.todo.repository.TodoRepository;
import me.albert.todo.service.dto.request.TodoCreateRequest;
import me.albert.todo.service.dto.response.IdResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final AccountService accountService;

    @Transactional
    @Override
    public IdResponse create(TodoCreateRequest request, String username) {
        Account owner = accountService.findByUsername(username);
        LocalDateTime now = LocalDateTime.now();

        Todo todo = new Todo(
                request.title(),
                request.description(),
                request.dueDate(),
                owner,
                now,
                now,
                TodoStatus.PENDING
        );
        Todo savedTodo = todoRepository.save(todo);
        return new IdResponse(savedTodo.getId());
    }
}
