package me.albert.todo.repository;

import java.util.Optional;
import me.albert.todo.domain.Account;
import me.albert.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    Optional<Todo> findByIdAndOwner(Long id, Account owner);
}
