package me.albert.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import me.albert.todo.domain.Account;
import me.albert.todo.domain.Project;
import me.albert.todo.exception.BusinessException;
import me.albert.todo.repository.ProjectRepository;
import me.albert.todo.utils.ErrorMessages;
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


    @DisplayName("프로젝트를 수정한다")
    @Test
    void update_project() {
        // given
        var projectId = 1L;
        var name = "프로젝트";
        var username = "user";
        var account = new Account(1L);
        var project = new Project("프로젝트", account);
        when(accountService.findByUsername(username)).thenReturn(account);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // when
        projectService.updateProject(projectId, name, username);
    }

    @DisplayName("프로젝트를 수정할 때 권한이 없으면 예외가 발생한다")
    @Test
    void update_project_without_permission() {
        // given
        var projectId = 1L;
        var name = "프로젝트";
        var username = "user";
        var account = new Account(1L);
        var project = new Project("프로젝트", new Account(2L));
        when(accountService.findByUsername(username)).thenReturn(account);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // when & then
        assertThatThrownBy(() -> projectService.updateProject(projectId, name, username))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorMessages.PROJECT_UPDATE_NOT_ALLOWED);
    }

    @DisplayName("프로젝트를 수정할 때 프로젝트가 없으면 예외가 발생한다")
    @Test
    void update_project_without_project() {
        // given
        var projectId = 1L;
        var name = "프로젝트";
        var username = "user";
        var account = new Account(1L);
        when(accountService.findByUsername(username)).thenReturn(account);
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> projectService.updateProject(projectId, name, username))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorMessages.PROJECT_NOT_FOUND);
    }
}
