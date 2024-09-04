package me.albert.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import me.albert.todo.domain.Account;
import me.albert.todo.domain.Group;
import me.albert.todo.repository.GroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("GroupService 테스트")
@ExtendWith(MockitoExtension.class)
class GroupServiceImplTest {

    @InjectMocks
    private GroupServiceImpl groupService;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private AccountService accountService;

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
}
