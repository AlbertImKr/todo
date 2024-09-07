package me.albert.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import me.albert.todo.domain.Account;
import me.albert.todo.domain.Project;
import me.albert.todo.repository.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("프로젝트 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private AccountService accountService;

    @DisplayName("프로젝트를 생성한다")
    @Test
    void create_project() {
        // given
        var name = "프로젝트";
        var username = "user";
        var account = new Account(1L);
        var expectedId = 1L;
        var project = new Project(expectedId);
        when(accountService.findByUsername(username)).thenReturn(account);
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        // when
        var target = projectService.createProject(name, username);

        // then
        assertThat(target.id()).isEqualTo(expectedId);
    }

}
