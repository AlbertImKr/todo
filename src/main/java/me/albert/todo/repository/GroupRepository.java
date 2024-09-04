package me.albert.todo.repository;

import me.albert.todo.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {


}
