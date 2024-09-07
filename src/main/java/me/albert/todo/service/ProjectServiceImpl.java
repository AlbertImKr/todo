package me.albert.todo.service;

import lombok.RequiredArgsConstructor;
import me.albert.todo.domain.Project;
import me.albert.todo.repository.ProjectRepository;
import me.albert.todo.service.dto.response.IdResponse;
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
}
