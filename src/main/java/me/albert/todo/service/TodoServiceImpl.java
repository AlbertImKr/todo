package me.albert.todo.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.albert.todo.domain.Account;
import me.albert.todo.domain.Tag;
import me.albert.todo.domain.Todo;
import me.albert.todo.domain.TodoPriority;
import me.albert.todo.domain.TodoStatus;
import me.albert.todo.exception.BusinessException;
import me.albert.todo.repository.TodoRepository;
import me.albert.todo.service.dto.request.TodoCreateRequest;
import me.albert.todo.service.dto.request.TodoUpdateRequest;
import me.albert.todo.service.dto.response.GroupTodoDetailResponse;
import me.albert.todo.service.dto.response.IdResponse;
import me.albert.todo.service.dto.response.TodoDetailResponse;
import me.albert.todo.service.dto.response.TodoResponse;
import me.albert.todo.utils.ErrorMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                TodoStatus.PENDING,
                TodoPriority.MEDIUM
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
    public TodoDetailResponse get(Long id, String username) {
        Account owner = accountService.findByUsername(username);
        Todo todo = todoRepository.findWithAllByIdAndOwnerAndGroupNull(id, owner)
                .orElseThrow(() -> new BusinessException(ErrorMessages.TODO_NOT_FOUND, HttpStatus.NOT_FOUND));
        return TodoDetailResponse.from(todo);
    }

    @Transactional
    @Override
    public void updateStatus(Long id, TodoStatus status, String username) {
        Account owner = accountService.findByUsername(username);
        Todo todo = getTodoById(id);
        LocalDateTime updatedAt = LocalDateTime.now();
        todo.updateStatus(status, updatedAt, owner);
    }

    @Transactional(readOnly = true)
    @Override
    public Todo getTodoByIdAndUsername(Long todoId, String username) {
        Account owner = accountService.findByUsername(username);
        Todo todo = getTodoById(todoId);
        if (!todo.isOwner(owner)) {
            throw new BusinessException(ErrorMessages.TODO_UPDATE_NOT_ALLOWED, HttpStatus.FORBIDDEN);
        }
        return todo;
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

    @Transactional
    @Override
    public void unassignTag(Long todoId, Long tagId, String currentUsername) {
        Account owner = accountService.findByUsername(currentUsername);
        Todo todo = todoRepository.findByIdAndOwnerAndGroupNull(todoId, owner)
                .orElseThrow(() -> new BusinessException(ErrorMessages.TODO_NOT_FOUND, HttpStatus.NOT_FOUND));
        Tag tag = tagService.findById(tagId);
        todo.unassignTag(tag);
    }

    @Transactional
    @Override
    public void updatePriority(Long id, TodoPriority priority, String currentUsername) {
        Account owner = accountService.findByUsername(currentUsername);
        Todo todo = getTodoById(id);
        todo.updatePriority(priority, owner);
    }

    @Transactional
    @Override
    public void updateNotificationSettings(Long id, List<Duration> durations, String currentUsername) {
        Account owner = accountService.findByUsername(currentUsername);
        Todo todo = getTodoById(id);
        todo.updateNotificationSettings(durations, owner);
    }

    @Transactional
    @Override
    public void deleteNotificationSettings(Long id, String currentUsername) {
        Account owner = accountService.findByUsername(currentUsername);
        Todo todo = getTodoById(id);
        todo.deleteNotificationSettings(owner);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TodoResponse> list(String username, Pageable pageable) {
        Account owner = accountService.findByUsername(username);
        Page<Todo> todos = todoRepository.findAllWithTagsByOwnerAndGroupNull(owner, pageable);
        return todos.map(TodoResponse::from);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TodoResponse> listByTag(String tagName, String username, Pageable pageable) {
        Account owner = accountService.findByUsername(username);
        Tag tag = tagService.findByName(tagName);
        Page<Todo> todos = todoRepository.findAllByTagsContainingAndOwnerAndGroupNull(tag, owner, pageable);
        return todos.map(TodoResponse::from);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TodoResponse> listByProject(Long projectId, String username, Pageable pageable) {
        Account owner = accountService.findByUsername(username);
        Page<Todo> todos = todoRepository.findAllWithTagsByProjectIdAndOwner(projectId, owner, pageable);
        return todos.map(TodoResponse::from);
    }

    @Transactional(readOnly = true)
    @Override
    public Todo findByIdAndGroupId(Long todoId, Long groupId) {
        return todoRepository.findByIdAndGroupId(todoId, groupId)
                .orElseThrow(() -> new BusinessException(ErrorMessages.TODO_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Transactional
    @Override
    public void updateGroupTodo(Long groupId, Long todoId, TodoUpdateRequest request) {
        Todo todo = getTodoById(todoId);
        todo.update(request.title(), request.description(), request.dueDate(), LocalDateTime.now(), request.status(),
                    groupId
        );
    }

    @Transactional
    @Override
    public void updateGroupTodoStatus(Long groupId, Long todoId, TodoStatus status) {
        Todo todo = getTodoById(todoId);
        todo.updateStatus(status, LocalDateTime.now(), groupId);
    }

    @Transactional
    @Override
    public void updateGroupTodoPriority(Long groupId, Long todoId, TodoPriority priority) {
        Todo todo = getTodoById(todoId);
        todo.updatePriority(priority, groupId);
    }

    @Transactional
    @Override
    public void assignGroupTodoTag(Long groupId, Long todoId, Long tagId) {
        Todo todo = getTodoById(todoId);
        Tag tag = tagService.findById(tagId);
        todo.assignTag(tag, groupId);
    }

    @Transactional
    @Override
    public void unassignGroupTodoTag(Long groupId, Long todoId, Long tagId) {
        Todo todo = getTodoById(todoId);
        Tag tag = tagService.findById(tagId);
        todo.unassignTag(tag, groupId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Todo> getAllByIdInAndGroupId(List<Long> todoIds, Long groupId) {
        return todoRepository.findAllByIdInAndGroupId(todoIds, groupId);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<GroupTodoDetailResponse> getAllWithTagsByGroupIdAndProjectId(Long groupId, Long projectId, Pageable pageable) {
        return todoRepository.findAllWithTagsByGroupIdAndProjectId(groupId, projectId, pageable)
                .map(GroupTodoDetailResponse::from);
    }

    public Todo getTodoById(Long todoId) {
        return todoRepository.findById(todoId)
                .orElseThrow(() -> new BusinessException(ErrorMessages.TODO_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}
