package me.albert.todo.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.albert.todo.domain.Account;
import me.albert.todo.domain.Group;
import me.albert.todo.repository.GroupRepository;
import me.albert.todo.service.dto.response.GroupResponse;
import me.albert.todo.service.dto.response.IdResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {

    public static final String GROUP_NOT_FOUND = "그룹이 존재하지 않습니다.";

    private final GroupRepository groupRepository;
    private final AccountService accountService;

    @Transactional
    @Override
    public IdResponse create(String name, String description, String username) {
        Account account = accountService.findByUsername(username);
        LocalDateTime now = LocalDateTime.now();
        Group group = groupRepository.save(new Group(name, description, account, now, now));
        return new IdResponse(group.getId());
    }

    @Transactional
    @Override
    public void update(long id, String name, String description, String username) {
        Account account = accountService.findByUsername(username);
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(GROUP_NOT_FOUND));
        group.update(account, name, description, LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    @Override
    public List<GroupResponse> list(String username, Pageable pageable) {
        Account account = accountService.findByUsername(username);
        Page<Group> groups = groupRepository.findByOwner(account, pageable);
        return groups.map(GroupResponse::from).getContent();
    }
}
