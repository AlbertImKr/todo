package me.albert.todo.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.albert.todo.domain.Account;
import me.albert.todo.domain.Tag;
import me.albert.todo.domain.Todo;
import me.albert.todo.domain.TodoStatus;
import me.albert.todo.exception.BusinessException;
import me.albert.todo.repository.TodoRepository;
import me.albert.todo.service.dto.request.TodoCreateRequest;
import me.albert.todo.service.dto.request.TodoUpdateRequest;
import me.albert.todo.service.dto.response.IdResponse;
import me.albert.todo.service.dto.response.TodoResponse;
import me.albert.todo.utils.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final AccountService accountService;
    private final TagService tagService;

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

    @Transactional
    @Override
    public void update(TodoUpdateRequest request, Long id, String username) {
        Account owner = accountService.findByUsername(username);
        Todo todo = todoRepository.findByIdAndGroupNull(id)
                .orElseThrow(() -> new BusinessException(ErrorMessages.TODO_NOT_FOUND, HttpStatus.NOT_FOUND));
        LocalDateTime updatedAt = LocalDateTime.now();
        todo.update(
                request.title(),
                request.description(),
                request.dueDate(),
                updatedAt,
                request.status(),
                owner
        );
    }

    @Transactional
    @Override
    public void delete(Long id, String username) {
        Todo todo = todoRepository.findByIdAndGroupNull(id)
                .orElseThrow(() -> new BusinessException(ErrorMessages.TODO_NOT_FOUND, HttpStatus.NOT_FOUND));
        Account owner = accountService.findByUsername(username);
        if (!todo.isOwner(owner)) {
            throw new BusinessException(ErrorMessages.TODO_DELETE_NOT_ALLOWED, HttpStatus.FORBIDDEN);
        }
        todoRepository.delete(todo);
    }

    @Transactional(readOnly = true)
    @Override
    public TodoResponse get(Long id, String username) {
        Account owner = accountService.findByUsername(username);
        Todo todo = todoRepository.findByIdAndOwner(id, owner)
                .orElseThrow(() -> new BusinessException(ErrorMessages.TODO_NOT_FOUND, HttpStatus.NOT_FOUND));
        return TodoResponse.from(todo);
    }

    @Transactional
    @Override
    public void updateStatus(Long id, TodoStatus status, String username) {
        Account owner = accountService.findByUsername(username);
        Todo todo = todoRepository.findByIdAndOwner(id, owner)
                .orElseThrow(() -> new BusinessException(ErrorMessages.TODO_NOT_FOUND, HttpStatus.NOT_FOUND));
        LocalDateTime updatedAt = LocalDateTime.now();
        todo.updateStatus(status, updatedAt);
    }

    @Transactional(readOnly = true)
    @Override
    public Todo getTodoByIdAndUsername(Long todoId, String username) {
        Account owner = accountService.findByUsername(username);
        return todoRepository.findByIdAndOwner(todoId, owner)
                .orElseThrow(() -> new BusinessException(ErrorMessages.TODO_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Transactional
    @Override
    public void assignUser(Long todoId, String username, String currentUsername) {
        Account owner = accountService.findByUsername(currentUsername);
        Todo todo = todoRepository.findByIdAndOwner(todoId, owner)
                .orElseThrow(() -> new BusinessException(ErrorMessages.TODO_NOT_FOUND, HttpStatus.NOT_FOUND));
        Account assignee = accountService.findByUsername(username);
        todo.assignUser(assignee);
    }

    @Transactional
    @Override
    public void unassignUser(Long todoId, String username, String currentUsername) {
        Account owner = accountService.findByUsername(currentUsername);
        Todo todo = todoRepository.findByIdAndOwner(todoId, owner)
                .orElseThrow(() -> new BusinessException(ErrorMessages.TODO_NOT_FOUND, HttpStatus.NOT_FOUND));
        Account assignee = accountService.findByUsername(username);
        todo.unassignUser(assignee);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Todo> findAllByIdInAndOwner(List<Long> todoIds, String username) {
        Account owner = accountService.findByUsername(username);
        return todoRepository.findAllByIdInAndOwner(todoIds, owner);
    }

    @Transactional
    @Override
    public void assignTag(Long todoId, Long tagId, String currentUsername) {
        Account owner = accountService.findByUsername(currentUsername);
        Todo todo = todoRepository.findByIdAndOwner(todoId, owner)
                .orElseThrow(() -> new BusinessException(ErrorMessages.TODO_NOT_FOUND, HttpStatus.NOT_FOUND));
        Tag tag = tagService.findById(tagId);
        todo.assignTag(tag);
    }
}
