package me.albert.todo.repository;

import me.albert.todo.domain.RecurringTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecurringTaskRepository extends JpaRepository<RecurringTask, Long> {

}
