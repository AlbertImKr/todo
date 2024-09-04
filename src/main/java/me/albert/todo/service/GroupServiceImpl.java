package me.albert.todo.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import me.albert.todo.domain.Account;
import me.albert.todo.domain.Group;
import me.albert.todo.repository.GroupRepository;
import me.albert.todo.service.dto.response.IdResponse;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final AccountService accountService;

    @Override
    public IdResponse create(String name, String description, String username) {
        Account account = accountService.findByUsername(username);
        LocalDateTime now = LocalDateTime.now();
        Group group = groupRepository.save(new Group(name, description, account, now, now));
        return new IdResponse(group.getId());
    }
}
