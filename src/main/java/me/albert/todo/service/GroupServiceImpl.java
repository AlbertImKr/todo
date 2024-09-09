package me.albert.todo.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.albert.todo.domain.Account;
import me.albert.todo.domain.Group;
import me.albert.todo.domain.Todo;
import me.albert.todo.domain.TodoPriority;
import me.albert.todo.domain.TodoStatus;
import me.albert.todo.exception.BusinessException;
import me.albert.todo.repository.GroupRepository;
import me.albert.todo.service.dto.request.TodoUpdateRequest;
import me.albert.todo.service.dto.response.AccountResponse;
import me.albert.todo.service.dto.response.GroupResponse;
import me.albert.todo.service.dto.response.IdResponse;
import me.albert.todo.service.dto.response.TodoResponse;
import me.albert.todo.utils.ErrorMessages;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final AccountService accountService;
    private final TodoService todoService;

    @Transactional
    @Override
    public IdResponse create(String name, String description, String username) {
        Account account = accountService.findByUsername(username);
        LocalDateTime now = LocalDateTime.now();
        if (groupRepository.existsByName(name)) {
            throw new BusinessException(ErrorMessages.GROUP_NAME_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
        try {
            Group group = groupRepository.save(new Group(name, description, account, now, now));
            return new IdResponse(group.getId());
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(ErrorMessages.GROUP_NAME_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @Override
    public void update(long groupId, String name, String description, String username) {
        Account account = accountService.findByUsername(username);
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new BusinessException(ErrorMessages.GROUP_NOT_FOUND, HttpStatus.NOT_FOUND));
        LocalDateTime updatedAt = LocalDateTime.now();
        group.update(account, name, description, updatedAt);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GroupResponse> list(String username, Pageable pageable) {
        Account account = accountService.findByUsername(username);
        Page<Group> groups = groupRepository.findByOwner(account, pageable);
        return groups.map(GroupResponse::from).getContent();
    }

    @Transactional
    @Override
    public void assignTodos(Long groupId, List<Long> todoIds, String username) {
        Account account = accountService.findByUsername(username);
        Group group = validateGroupMembership(groupId, username);
        List<Todo> todos = todoService.findAllByIdInAndOwner(todoIds, username);
        group.assignTodos(account, todos);
    }

    @Transactional
    @Override
    public void unassignTodos(Long groupId, List<Long> todoIds, String username) {
        Account account = accountService.findByUsername(username);
        Group group = validateGroupMembership(groupId, username);
        List<Todo> todos = todoService.findAllByIdInAndOwner(todoIds, username);
        group.unassignTodos(account, todos);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TodoResponse> listTodos(Long id, String username) {
        Group group = groupRepository.findByIdAndOwnerUsername(id, username)
                .orElseThrow(() -> new BusinessException(ErrorMessages.GROUP_NOT_FOUND, HttpStatus.NOT_FOUND));
        return group.getTodos().stream()
                .map(TodoResponse::from)
                .toList();
    }

    @Transactional
    @Override
    public void delete(Long id, String username) {
        Account account = accountService.findByUsername(username);
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorMessages.GROUP_NOT_FOUND, HttpStatus.NOT_FOUND));
        if (!group.isOwner(account)) {
            throw new BusinessException(ErrorMessages.GROUP_NOT_OWNER, HttpStatus.FORBIDDEN);
        }
        groupRepository.delete(group);
    }

    @Transactional
    @Override
    public void addAccounts(Long id, List<Long> accountIds, String username) {
        Account account = accountService.findByUsername(username);
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorMessages.GROUP_NOT_FOUND, HttpStatus.NOT_FOUND));
        List<Account> accountsToAdd = accountService.findAllById(accountIds);
        group.addAccounts(account, accountsToAdd);
    }

    @Transactional
    @Override
    public void removeAccounts(Long id, List<Long> accountIds, String username) {
        Account account = accountService.findByUsername(username);
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorMessages.GROUP_NOT_FOUND, HttpStatus.NOT_FOUND));
        List<Account> accountsToRemove = accountService.findAllById(accountIds);
        group.removeAccounts(account, accountsToRemove);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AccountResponse> listAccounts(Long id, String username) {
        Group group = validateGroupMembership(id, username);
        return group.getUsers().stream()
                .map(AccountResponse::from)
                .toList();
    }

    @Transactional
    @Override
    public void assignTodoToUsers(Long groupId, Long todoId, List<Long> accountIds, String username) {
        Group group = validateGroupMembership(groupId, username);
        Todo todo = todoService.findByIdAndGroupId(todoId, group.getId());
        List<Account> accounts = accountService.findAllById(accountIds);
        group.assignTodoToUsers(todo, accounts);
    }

    @Transactional
    @Override
    public void unassignTodoFromUsers(Long groupId, Long todoId, List<Long> accountIds, String username) {
        Group group = validateGroupMembership(groupId, username);
        Todo todo = todoService.findByIdAndGroupId(todoId, group.getId());
        List<Account> accounts = accountService.findAllById(accountIds);
        group.unassignTodoFromUsers(todo, accounts);
    }

    @Transactional
    @Override
    public void editTodo(Long groupId, Long todoId, TodoUpdateRequest request, String username) {
        validateGroupMembership(groupId, username);
        todoService.updateGroupTodo(groupId, todoId, request);
    }

    @Transactional
    @Override
    public void updateTodoStatus(Long groupId, Long todoId, TodoStatus status, String username) {
        validateGroupMembership(groupId, username);
        todoService.updateGroupTodoStatus(groupId, todoId, status);
    }

    @Transactional
    @Override
    public void updateTodoPriority(Long groupId, Long todoId, TodoPriority priority, String username) {
        validateGroupMembership(groupId, username);
        todoService.updateGroupTodoPriority(groupId, todoId, priority);
    }

    @Transactional
    @Override
    public void assignTag(Long groupId, Long todoId, Long tagId, String username) {
        validateGroupMembership(groupId, username);
        todoService.assignGroupTodoTag(groupId, todoId, tagId);
    }

    @Transactional
    @Override
    public void unassignTag(Long groupId, Long todoId, Long tagId, String username) {
        validateGroupMembership(groupId, username);
        todoService.unassignGroupTodoTag(groupId, todoId, tagId);
    }

    private Group validateGroupMembership(Long groupId, String username) {
        Account account = accountService.findByUsername(username);
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new BusinessException(ErrorMessages.GROUP_NOT_FOUND, HttpStatus.NOT_FOUND));
        group.validatePermission(account);
        return group;
    }
}
