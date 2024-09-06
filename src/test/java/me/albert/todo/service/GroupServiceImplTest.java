package me.albert.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import me.albert.todo.domain.Account;
import me.albert.todo.domain.Group;
import me.albert.todo.domain.Todo;
import me.albert.todo.repository.GroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}
