package me.albert.todo.repository;

import me.albert.todo.domain.Account;
import me.albert.todo.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {


    Page<Project> findByOwnerAndGroupNull(Account owner, Pageable pageable);
}
