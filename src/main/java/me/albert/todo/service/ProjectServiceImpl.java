package me.albert.todo.service;

import lombok.RequiredArgsConstructor;
import me.albert.todo.domain.Project;
import me.albert.todo.exception.BusinessException;
import me.albert.todo.repository.ProjectRepository;
import me.albert.todo.service.dto.response.IdResponse;
import me.albert.todo.utils.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final AccountService accountService;

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
}
