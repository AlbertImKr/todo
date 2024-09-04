package me.albert.todo.repository;

import me.albert.todo.domain.Account;
import me.albert.todo.domain.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Page<Group> findByOwner(Account account, Pageable pageable);
}
