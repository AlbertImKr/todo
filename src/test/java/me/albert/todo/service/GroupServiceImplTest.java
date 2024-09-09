package me.albert.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import me.albert.todo.domain.Account;
import me.albert.todo.domain.Group;
import me.albert.todo.domain.Todo;
import me.albert.todo.exception.BusinessException;
import me.albert.todo.repository.GroupRepository;
import me.albert.todo.utils.ErrorMessages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

@DisplayName("그룹 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class GroupServiceImplTest {

    @InjectMocks
    private GroupServiceImpl groupService;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private AccountService accountService;

    @Mock
    private TodoService todoService;

    @DisplayName("그룹의 유저 목록을 조회하면 예외가 발생하지 않아야 한다.")
    @Test
    void list_users_if_success() {
        // given
        var id = 1L;
        var username = "test";
        var account = new Account(1L);
        var group = new Group("group", "description", account, LocalDateTime.now(), LocalDateTime.now());
        when(groupRepository.findById(id)).thenReturn(Optional.of(group));
        when(accountService.findByUsername(username)).thenReturn(account);

        // when, then
        assertThatCode(() -> groupService.listAccounts(id, username)).doesNotThrowAnyException();
    }

    @DisplayName("그룹의 유저 목록을 조회할 때 그룹이 존재하지 않으면 예외가 발생해야 한다.")
    @Test
    void list_users_if_group_not_found() {
        // given
        var id = 1L;
        var username = "test";
        when(groupRepository.findById(id)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> groupService.listAccounts(id, username))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.GROUP_NOT_FOUND);
    }

    @DisplayName("그룹에 유저를 삭제하면 예외가 발생하지 않아야 한다.")
    @Test
    void remove_user_if_success() {
        // given
        var groupId = 1L;
        var username = "test";
        var account = new Account(1L);
        var accountsIds = List.of(2L, 3L);
        var accountsToRemove = List.of(new Account(2L), new Account(3L));
        var now = LocalDateTime.now();
        var groupEntity = new Group("group", "description", account, now, now);
        when(accountService.findByUsername(username)).thenReturn(account);
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(groupEntity));
        when(accountService.findAllById(accountsIds)).thenReturn(accountsToRemove);

        // when, then
        assertThatCode(() -> groupService.removeAccounts(groupId, accountsIds, username)).doesNotThrowAnyException();
    }

    @DisplayName("그룹에 유저를 삭제할 때 그룹이 존재하지 않으면 예외가 발생해야 한다.")
    @Test
    void remove_user_if_group_not_found() {
        // given
        var groupId = 1L;
        var username = "test";
        var accountsIds = List.of(2L, 3L);
        when(accountService.findByUsername(username)).thenReturn(new Account());
        when(groupRepository.findById(groupId)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> groupService.removeAccounts(groupId, accountsIds, username))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.GROUP_NOT_FOUND);
    }

    @DisplayName("그룹에 계정을 추가하면 예외가 발생하지 않아야 한다.")
    @Test
    void add_user_if_success() {
        // given
        var groupId = 1L;
        var username = "test";
        var account = new Account(1L);
        var accountsIds = List.of(2L, 3L);
        var accountsToAdd = List.of(new Account(2L), new Account(3L));
        var now = LocalDateTime.now();
        var groupEntity = new Group("group", "description", account, now, now);
        when(accountService.findByUsername(username)).thenReturn(account);
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(groupEntity));
        when(accountService.findAllById(accountsIds)).thenReturn(accountsToAdd);

        // when, then
        assertThatCode(() -> groupService.addAccounts(groupId, accountsIds, username)).doesNotThrowAnyException();
    }

    @DisplayName("그룹에 계정을 추가할 때 그룹이 존재하지 않으면 예외가 발생해야 한다.")
    @Test
    void add_user_if_group_not_found() {
        // given
        var groupId = 1L;
        var username = "test";
        var accountsIds = List.of(2L, 3L);
        when(accountService.findByUsername(username)).thenReturn(new Account());
        when(groupRepository.findById(groupId)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> groupService.addAccounts(groupId, accountsIds, username))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.GROUP_NOT_FOUND);
    }

    @DisplayName("그룹을 삭제 성공하면 예외가 발생하지 않아야 한다.")
    @Test
    void delete_group_if_success() {
        // given
        var groupId = 1L;
        var username = "test";
        var account = new Account(1L);
        var now = LocalDateTime.now();
        var groupEntity = new Group("group", "description", account, now, now);
        when(accountService.findByUsername(username)).thenReturn(account);
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(groupEntity));

        // when, then
        assertThatCode(() -> groupService.delete(groupId, username)).doesNotThrowAnyException();
    }

    @DisplayName("그룹을 삭제할 때 그룹이 존재하지 않으면 예외가 발생해야 한다.")
    @Test
    void delete_group_if_group_not_found() {
        // given
        var groupId = 1L;
        var username = "test";
        var account = new Account(1L);
        when(accountService.findByUsername(username)).thenReturn(account);
        when(groupRepository.findById(groupId)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> groupService.delete(groupId, username)).isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.GROUP_NOT_FOUND);
    }

    @DisplayName("그룹을 삭제할 때 그룹의 소유자가 아니면 예외가 발생해야 한다.")
    @Test
    void delete_group_if_not_owner() {
        // given
        var id = 1L;
        var username = "test";
        var account = new Account(1L);
        var otherAccount = new Account(2L);
        var now = LocalDateTime.now();
        var group = new Group("group", "description", otherAccount, now, now);
        when(accountService.findByUsername(username)).thenReturn(account);
        when(groupRepository.findById(id)).thenReturn(Optional.of(group));

        // when, then
        assertThatThrownBy(() -> groupService.delete(id, username)).isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.GROUP_NOT_OWNER);
    }

    @DisplayName("그룹을 생성할 때 이미 존재하는 이름이면 예외가 발생해야 한다.")
    @Test
    void create_group_if_name_exists() {
        // given
        String name = "group";
        String description = "description";
        String username = "test";
        when(accountService.findByUsername(username)).thenReturn(new Account());
        when(groupRepository.existsByName(name)).thenReturn(true);

        // when, then
        assertThatThrownBy(() -> groupService.create(name, description, username))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.GROUP_NAME_ALREADY_EXISTS);
    }

    @DisplayName("그룹을 생성할 때 DataIntegrityViolationException이 발생하면 예외가 발생해야 한다.")
    @Test
    void create_group_if_data_integrity_violation_exception() {
        // given
        String name = "group";
        String description = "description";
        String username = "test";
        when(accountService.findByUsername(username)).thenReturn(new Account());
        when(groupRepository.existsByName(name)).thenReturn(false);
        when(groupRepository.save(any())).thenThrow(new DataIntegrityViolationException(""));

        // when, then
        assertThatThrownBy(() -> groupService.create(name, description, username))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.GROUP_NAME_ALREADY_EXISTS);
    }

    @DisplayName("그룹을 생성하면 IdResponse를 반환해야 한다.")
    @Test
    void create_group_if_success() {
        // given
        String name = "group";
        String description = "description";
        String username = "test";
        when(accountService.findByUsername(username)).thenReturn(new Account());
        var mockGroup = mock(Group.class);
        when(groupRepository.save(any())).thenReturn(mockGroup);
        var expectedId = 1L;
        when(mockGroup.getId()).thenReturn(expectedId);

        // when
        var target = groupService.create(name, description, username);

        // then
        assertThat(target.id()).isEqualTo(expectedId);
    }

    @DisplayName("그룹을 수정 성공하면 예외가 발생하지 않아야 한다.")
    @Test
    void update_group_if_success() {
        // given
        Long id = 1L;
        String name = "group";
        String description = "description";
        String username = "test";
        when(accountService.findByUsername(username)).thenReturn(new Account());
        var mockGroup = mock(Group.class);
        when(groupRepository.findById(id)).thenReturn(Optional.of(mockGroup));

        // when, then
        assertThatCode(() -> {
            groupService.update(id, name, description, username);
        }).doesNotThrowAnyException();
    }

    @DisplayName("그룹을 수정할 때 그룹이 존재하지 않으면 예외가 발생해야 한다.")
    @Test
    void update_group_if_group_not_found() {
        // given
        Long id = 1L;
        String name = "group";
        String description = "description";
        String username = "test";
        when(accountService.findByUsername(username)).thenReturn(new Account());
        when(groupRepository.findById(id)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> {
            groupService.update(id, name, description, username);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(GroupServiceImpl.GROUP_NOT_FOUND);
    }

    @DisplayName("그룹에 할 일을 할당하면 예외가 발생하지 않아야 한다.")
    @Test
    void assign_todos_if_success() {
        // given
        Long groupId = 1L;
        var todoIds = List.of(1L, 2L);
        String username = "test";
        when(accountService.findByUsername(username)).thenReturn(new Account());
        var mockGroup = mock(Group.class);
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(mockGroup));
        var mockTodo = mock(Todo.class);
        when(todoService.findAllByIdInAndOwner(todoIds, username)).thenReturn(List.of(mockTodo));

        // when, then
        assertThatCode(() -> {
            groupService.assignTodos(groupId, todoIds, username);
        }).doesNotThrowAnyException();
    }

    @DisplayName("그룹에 할 일을 할당할 때 그룹이 존재하지 않으면 예외가 발생해야 한다.")
    @Test
    void assign_todos_if_group_not_found() {
        // given
        Long groupId = 1L;
        var todoIds = List.of(1L, 2L);
        String username = "test";
        when(accountService.findByUsername(username)).thenReturn(new Account());
        when(groupRepository.findById(groupId)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> groupService.assignTodos(groupId, todoIds, username))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(GroupServiceImpl.GROUP_NOT_FOUND);
    }

    @DisplayName("그룹에 할 일을 할당할 때 그룹의 맴버가 아니면 예외가 발생해야 한다.")
    @Test
    void assign_todos_if_not_member() {
        // given
        Long groupId = 1L;
        var todoIds = List.of(1L, 2L);
        String username = "test";
        when(accountService.findByUsername(username)).thenReturn(new Account());
        var mockGroup = mock(Group.class);
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(mockGroup));
        when(mockGroup.isOwner(any())).thenReturn(false);
        when(mockGroup.isMember(any())).thenReturn(false);

        // when, then
        assertThatThrownBy(() -> groupService.assignTodos(groupId, todoIds, username))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.GROUP_NOT_MEMBER);
    }

    @DisplayName("그룹에서 할 일을 해제하면 예외가 발생하지 않아야 한다.")
    @Test
    void unassign_todos_if_success() {
        // given
        Long groupId = 1L;
        var todoIds = List.of(1L, 2L);
        String username = "test";
        when(accountService.findByUsername(username)).thenReturn(new Account());
        var mockGroup = mock(Group.class);
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(mockGroup));
        var mockTodo = mock(Todo.class);
        when(todoService.findAllByIdInAndOwner(todoIds, username)).thenReturn(List.of(mockTodo));

        // when, then
        assertThatCode(() -> groupService.unassignTodos(groupId, todoIds, username)).doesNotThrowAnyException();
    }

    @DisplayName("그룹에서 할 일을 해제할 때 그룹이 존재하지 않으면 예외가 발생해야 한다.")
    @Test
    void unassign_todos_if_group_not_found() {
        // given
        Long groupId = 1L;
        var todoIds = List.of(1L, 2L);
        String username = "test";
        when(accountService.findByUsername(username)).thenReturn(new Account());
        when(groupRepository.findById(groupId)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> groupService.unassignTodos(groupId, todoIds, username))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(GroupServiceImpl.GROUP_NOT_FOUND);
    }

    @DisplayName("그룹의 할 일 목록을 조회하면 예외가 발생하지 않아야 한다.")
    @Test
    void list_todos_if_success() {
        // given
        Long id = 1L;
        String username = "test";
        var mockGroup = mock(Group.class);
        when(groupRepository.findByIdAndOwnerUsername(id, username)).thenReturn(Optional.of(mockGroup));

        // when, then
        assertThatCode(() -> groupService.listTodos(id, username)).doesNotThrowAnyException();
    }

    @DisplayName("그룹의 할 일 목록을 조회할 때 그룹이 존재하지 않으면 예외가 발생해야 한다.")
    @Test
    void list_todos_if_group_not_found() {
        // given
        Long id = 1L;
        String username = "test";
        when(groupRepository.findByIdAndOwnerUsername(id, username)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> groupService.listTodos(id, username))
                .isInstanceOf(BusinessException.class)
                .hasMessage(GroupServiceImpl.GROUP_NOT_FOUND);
    }
}
