package me.albert.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import me.albert.todo.domain.Account;
import me.albert.todo.domain.Project;
import me.albert.todo.domain.Todo;
import me.albert.todo.exception.BusinessException;
import me.albert.todo.repository.ProjectRepository;
import me.albert.todo.utils.ErrorMessages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@DisplayName("프로젝트 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private AccountService accountService;

    @Mock
    private TodoService todoService;

    @DisplayName("프로젝트 아이디로 프로젝트를 조회한다")
    @Test
    void get_project_by_id() {
        // given
        var projectId = 1L;
        var project = new Project(1L);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // when
        var target = projectService.getProjectById(projectId);

        // then
        assertThat(target).isNotNull();
    }

    @DisplayName("프로젝트 아이디로 프로젝트를 조회할 때 프로젝트가 없으면 예외가 발생한다")
    @Test
    void get_project_by_id_without_project() {
        // given
        var projectId = 1L;
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> projectService.getProjectById(projectId))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorMessages.PROJECT_NOT_FOUND);
    }

    @DisplayName("그룹 프로젝트를 생성한다")
    @Test
    void create_group_project() {
        // given
        var name = "프로젝트";
        var username = "user";
        var account = new Account(1L);
        var project = new Project(1L);
        when(accountService.findByUsername(username)).thenReturn(account);
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        // when, then
        projectService.createGroupProject(name, username);
    }

    @DisplayName("프로젝트를 인증한다")
    @Test
    void validate_project_id() {
        // given
        var projectId = 1L;
        var username = "user";
        var account = new Account(1L);
        var project = new Project("프로젝트", account);
        when(accountService.findByUsername(username)).thenReturn(account);
        when(projectRepository.findByIdAndGroupNull(projectId)).thenReturn(Optional.of(project));

        // when
        projectService.validateProjectId(projectId, username);
    }

    @DisplayName("프로젝트를 인증할 때 프로젝트가 없으면 예외가 발생한다")
    @Test
    void validate_project_id_without_project() {
        // given
        var projectId = 1L;
        var username = "user";
        when(projectRepository.findByIdAndGroupNull(projectId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> projectService.validateProjectId(projectId, username))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorMessages.PROJECT_NOT_FOUND);
    }

    @DisplayName("프로젝트를 인증할 때 권한이 없으면 예외가 발생한다")
    @Test
    void validate_project_id_without_permission() {
        // given
        var projectId = 1L;
        var username = "user";
        var account = new Account(1L);
        var project = new Project("프로젝트", new Account(2L));
        when(accountService.findByUsername(username)).thenReturn(account);
        when(projectRepository.findByIdAndGroupNull(projectId)).thenReturn(Optional.of(project));

        // when & then
        assertThatThrownBy(() -> projectService.validateProjectId(projectId, username))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorMessages.PROJECT_GET_NOT_ALLOWED);
    }

    @DisplayName("프로젝트를 조회한다")
    @Test
    void get_project() {
        // given
        var projectId = 1L;
        var username = "user";
        var account = new Account(1L);
        var project = new Project("프로젝트", account);
        when(accountService.findByUsername(username)).thenReturn(account);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // when
        var target = projectService.getProject(projectId, username);

        // then
        assertThat(target).isNotNull();
    }

    @DisplayName("프로젝트를 조회할 때 프로젝트가 없으면 예외가 발생한다")
    @Test
    void get_project_without_project() {
        // given
        var projectId = 1L;
        var username = "user";
        when(accountService.findByUsername(username)).thenReturn(new Account(1L));
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> projectService.getProject(projectId, username))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorMessages.PROJECT_NOT_FOUND);
    }

    @DisplayName("프로젝트를 조회할 때 권한이 없으면 예외가 발생한다")
    @Test
    void get_project_without_permission() {
        // given
        var projectId = 1L;
        var username = "user";
        var account = new Account(1L);
        var project = new Project("프로젝트", new Account(2L));
        when(accountService.findByUsername(username)).thenReturn(account);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // when & then
        assertThatThrownBy(() -> projectService.getProject(projectId, username))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorMessages.PROJECT_GET_NOT_ALLOWED);
    }

    @DisplayName("할 일을 프로젝트에서 해제한다")
    @Test
    void unassign_todo_from_project() {
        // given
        var projectId = 1L;
        var todoIds = List.of(1L);
        var username = "user";
        var account = new Account(1L);
        var project = new Project("프로젝트", account);
        var todo1 = new Todo(1L);
        var todo2 = new Todo(2L);
        project.assignTodos(List.of(todo1, todo2));
        when(accountService.findByUsername(username)).thenReturn(account);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(todoService.findAllByIdInAndOwner(todoIds, username)).thenReturn(List.of(todo1));

        // when
        projectService.unassignTodoFromProject(projectId, todoIds, username);

        // then
        assertThat(project.getTodos()).containsExactly(todo2);
    }

    @DisplayName("할 일을 프로젝트에서 해제할 때 프로젝트가 없으면 예외가 발생한다")
    @Test
    void unassign_todo_from_project_without_project() {
        // given
        var projectId = 1L;
        var todoIds = List.of(1L);
        var username = "user";
        when(accountService.findByUsername(username)).thenReturn(new Account(1L));
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> projectService.unassignTodoFromProject(projectId, todoIds, username))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorMessages.PROJECT_NOT_FOUND);
    }

    @DisplayName("할 일을 프로젝트에서 해제할 때 권한이 없으면 예외가 발생한다")
    @Test
    void unassign_todo_from_project_without_permission() {
        // given
        var projectId = 1L;
        var todoIds = List.of(1L);
        var username = "user";
        var account = new Account(1L);
        var project = new Project("프로젝트", new Account(2L));
        when(accountService.findByUsername(username)).thenReturn(account);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // when & then
        assertThatThrownBy(() -> projectService.unassignTodoFromProject(projectId, todoIds, username))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorMessages.PROJECT_UNASSIGN_NOT_ALLOWED);
    }

    @DisplayName("할 일을 프로젝트에 할당한다")
    @Test
    void assign_todo_to_project() {
        // given
        var projectId = 1L;
        var todos = List.of(new Todo(1L), new Todo(2L));
        var todoIds = List.of(1L, 2L);
        var username = "user";
        var account = new Account(1L);
        var project = new Project("프로젝트", account);
        when(accountService.findByUsername(username)).thenReturn(account);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(todoService.findAllByIdInAndOwner(todoIds, username)).thenReturn(todos);

        // when
        projectService.assignTodoToProject(projectId, todoIds, username);

        // then
        assertThat(project.getTodos()).containsAll(todos);
    }

    @DisplayName("할 일을 프로젝트에 할당할 때 프로젝트가 없으면 예외가 발생한다")
    @Test
    void assign_todo_to_project_without_project() {
        // given
        var projectId = 1L;
        var todoIds = List.of(1L, 2L);
        var username = "user";
        when(accountService.findByUsername(username)).thenReturn(new Account(1L));
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> projectService.assignTodoToProject(projectId, todoIds, username))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorMessages.PROJECT_NOT_FOUND);
    }

    @DisplayName("할 일을 프로젝트에 할당할 때 권한이 없으면 예외가 발생한다")
    @Test
    void assign_todo_to_project_without_permission() {
        // given
        var projectId = 1L;
        var todoIds = List.of(1L, 2L);
        var username = "user";
        var account = new Account(1L);
        var project = new Project("프로젝트", new Account(2L));
        when(accountService.findByUsername(username)).thenReturn(account);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // when & then
        assertThatThrownBy(() -> projectService.assignTodoToProject(projectId, todoIds, username))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorMessages.PROJECT_ASSIGN_NOT_ALLOWED);
    }

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

    @DisplayName("프로젝트를 삭제한다")
    @Test
    void delete_project() {
        // given
        var projectId = 1L;
        var username = "user";
        var account = new Account(1L);
        var project = new Project("프로젝트", account);
        when(accountService.findByUsername(username)).thenReturn(account);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // when
        projectService.deleteProject(projectId, username);
    }

    @DisplayName("프로젝트를 삭제할 때 권한이 없으면 예외가 발생한다")
    @Test
    void delete_project_without_permission() {
        // given
        var projectId = 1L;
        var username = "user";
        var account = new Account(1L);
        var project = new Project("프로젝트", new Account(2L));
        when(accountService.findByUsername(username)).thenReturn(account);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // when & then
        assertThatThrownBy(() -> projectService.deleteProject(projectId, username))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorMessages.PROJECT_DELETE_NOT_ALLOWED);
    }

    @DisplayName("프로젝트를 삭제할 때 프로젝트가 없으면 예외가 발생한다")
    @Test
    void delete_project_without_project() {
        // given
        var projectId = 1L;
        var username = "user";
        var account = new Account(1L);
        when(accountService.findByUsername(username)).thenReturn(account);
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> projectService.deleteProject(projectId, username))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorMessages.PROJECT_NOT_FOUND);
    }

    @DisplayName("사용자의 프로젝트 목록을 조회한다")
    @Test
    void get_projects() {
        // given
        var username = "user";
        var account = new Account(1L);
        var project = new Project("프로젝트", account);
        when(accountService.findByUsername(username)).thenReturn(account);
        when(projectRepository.findByOwnerAndGroupNull(account, Pageable.unpaged()))
                .thenReturn(new PageImpl<>(List.of(project)));

        // when
        var target = projectService.getProjects(username, Pageable.unpaged());

        // then
        assertThat(target).hasSize(1);
    }

    @DisplayName("사용자의 프로젝트 목록을 조회할 때 프로젝트가 없으면 빈 목록을 반환한다")
    @Test
    void get_projects_without_project() {
        // given
        var username = "user";
        var account = new Account(1L);
        when(accountService.findByUsername(username)).thenReturn(account);
        when(projectRepository.findByOwnerAndGroupNull(account, Pageable.unpaged()))
                .thenReturn(Page.empty());

        // when
        var target = projectService.getProjects(username, Pageable.unpaged());

        // then
        assertThat(target).isEmpty();
    }
}
