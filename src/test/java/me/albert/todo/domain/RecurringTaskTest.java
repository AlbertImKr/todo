package me.albert.todo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.Period;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("반복 작업 도메인 테스트")
class RecurringTaskTest {

    RecurringTask recurringTask;

    @BeforeEach
    void setUp() {
        Todo todo = new Todo();
        Period recurrencePattern = Period.ofDays(1);
        LocalDateTime nextOccurrence = LocalDateTime.now();
        recurringTask = new RecurringTask(todo, recurrencePattern, nextOccurrence);
    }

    @DisplayName("반복 주기와 다음 발생 시간을 수정한다")
    @Test
    void update_period() {
        // given
        Period newRecurrencePattern = Period.ofDays(2);
        LocalDateTime updatedAt = LocalDateTime.now().plusDays(2);

        // when
        recurringTask.updatePeriod(newRecurrencePattern, updatedAt);

        // then
        assertThat(recurringTask.getRecurrencePattern()).isEqualTo(newRecurrencePattern);
        assertThat(recurringTask.getNextOccurrence()).isEqualTo(updatedAt.plus(newRecurrencePattern));
    }
}
