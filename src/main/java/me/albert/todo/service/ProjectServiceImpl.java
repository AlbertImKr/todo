package me.albert.todo.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.albert.todo.domain.Project;
import me.albert.todo.exception.BusinessException;
import me.albert.todo.repository.ProjectRepository;
import me.albert.todo.service.dto.response.IdResponse;
import me.albert.todo.service.dto.response.ProjectDetailResponse;
import me.albert.todo.service.dto.response.ProjectResponse;
import me.albert.todo.utils.ErrorMessages;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final AccountService accountService;
    private final TodoService todoService;

    @Transactional
    @Override
    public IdResponse createProject(String name, String username) {
        var account = accountService.findByUsername(username);
        var project = new Project(name, account);
        var saved = projectRepository.save(project);
        return new IdResponse(saved.getId());
    }

    @Transactional
    @Override
    public void updateProject(Long projectId, String name, String username) {
        var account = accountService.findByUsername(username);
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException(ErrorMessages.PROJECT_NOT_FOUND, HttpStatus.NOT_FOUND));
        project.update(name, account);
    }

    @Transactional
    @Override
    public void deleteProject(Long projectId, String username) {
        var account = accountService.findByUsername(username);
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException(ErrorMessages.PROJECT_NOT_FOUND, HttpStatus.NOT_FOUND));
        if (!project.isOwner(account)) {
            throw new BusinessException(ErrorMessages.PROJECT_DELETE_NOT_ALLOWED, HttpStatus.FORBIDDEN);
        }
        projectRepository.delete(project);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProjectResponse> getProjects(String username, Pageable pageable) {
        var account = accountService.findByUsername(username);
        return projectRepository.findByOwnerAndGroupNull(account, pageable)
                .stream()
                .map(ProjectResponse::from)
                .toList();
    }

    @Transactional
    @Override
    public void assignTodoToProject(Long projectId, List<Long> todoIds, String username) {
        var account = accountService.findByUsername(username);
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException(ErrorMessages.PROJECT_NOT_FOUND, HttpStatus.NOT_FOUND));
        if (!project.isOwner(account)) {
            throw new BusinessException(ErrorMessages.PROJECT_ASSIGN_NOT_ALLOWED, HttpStatus.FORBIDDEN);
        }
        var todos = todoService.findAllByIdInAndOwner(todoIds, username);
        project.assignTodos(todos);
    }

    @Transactional
    @Override
    public void unassignTodoFromProject(Long projectId, List<Long> todoIds, String username) {
        var account = accountService.findByUsername(username);
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException(ErrorMessages.PROJECT_NOT_FOUND, HttpStatus.NOT_FOUND));
        if (!project.isOwner(account)) {
            throw new BusinessException(ErrorMessages.PROJECT_UNASSIGN_NOT_ALLOWED, HttpStatus.FORBIDDEN);
        }
        var todos = todoService.findAllByIdInAndOwner(todoIds, username);
        project.unassignTodos(todos);
    }


    @Transactional(readOnly = true)
    @Override
    public ProjectDetailResponse getProject(Long projectId, String username) {
        var account = accountService.findByUsername(username);
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException(ErrorMessages.PROJECT_NOT_FOUND, HttpStatus.NOT_FOUND));
        if (!project.isOwner(account)) {
            throw new BusinessException(ErrorMessages.PROJECT_GET_NOT_ALLOWED, HttpStatus.NOT_FOUND);
        }
        return ProjectDetailResponse.from(project);
    }
}
