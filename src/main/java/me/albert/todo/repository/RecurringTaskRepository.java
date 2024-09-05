package me.albert.todo.repository;

import java.util.Optional;
import me.albert.todo.domain.RecurringTask;
import me.albert.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecurringTaskRepository extends JpaRepository<RecurringTask, Long> {


    Optional<RecurringTask> findByIdAndTask(Long recurringTaskId, Todo task);
}
