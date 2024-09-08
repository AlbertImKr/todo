package me.albert.todo.repository;

import java.util.Optional;
import me.albert.todo.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    boolean existsByName(String name);

    Optional<Tag> findByName(String name);
}
