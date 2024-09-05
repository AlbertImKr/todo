package me.albert.todo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.Getter;

@Table(name = "recurring_task")
@Entity
public class RecurringTask {

    @Getter
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private Todo task;
    private Duration recurrencePattern;
    private LocalDateTime nextOccurrence;

    public RecurringTask() {
    }

    public RecurringTask(Todo task, Duration recurrencePattern, LocalDateTime nextOccurrence) {
        this.task = task;
        this.recurrencePattern = recurrencePattern;
        this.nextOccurrence = nextOccurrence;
    }
}
