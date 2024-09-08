package me.albert.todo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.Period;
import lombok.Getter;

@Table(name = "recurring_task")
@Entity
public class RecurringTask {

    @Getter
    @Id
    @GeneratedValue
    private Long id;
    @Getter
    private Period recurrencePattern;
    @Getter
    private LocalDateTime nextOccurrence;

    public RecurringTask() {
    }

    public RecurringTask(Period recurrencePattern, LocalDateTime nextOccurrence) {
        this.recurrencePattern = recurrencePattern;
        this.nextOccurrence = nextOccurrence;
    }
}
