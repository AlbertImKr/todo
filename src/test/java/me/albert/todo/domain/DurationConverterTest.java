package me.albert.todo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DurationConverterTest {

    DurationConverter converter = new DurationConverter();

    @DisplayName("시간만 포함한 Duration을 문자열로 변환한다")
    @Test
    void convert_to_database_column() {
        // given
        var duration = Duration.ofHours(1);

        // when
        var dbData = converter.convertToDatabaseColumn(duration);

        // then
        assertThat(dbData).isEqualTo("PT1H");
    }

    @DisplayName("시간과 분을 포함한 Duration을 문자열로 변환한다")
    @Test
    void convert_to_database_column_with_minute() {
        // given
        var duration = Duration.ofHours(1).plusMinutes(30);

        // when
        var dbData = converter.convertToDatabaseColumn(duration);

        // then
        assertThat(dbData).isEqualTo("PT1H30M");
    }

    @DisplayName("시간과 분, 초를 포함한 Duration을 문자열로 변환한다")
    @Test
    void convert_to_database_column_with_second() {
        // given
        var duration = Duration.ofHours(1).plusMinutes(30).plusSeconds(30);

        // when
        var dbData = converter.convertToDatabaseColumn(duration);

        // then
        assertThat(dbData).isEqualTo("PT1H30M30S");
    }

    @DisplayName("문자열을 Duration으로 변환한다")
    @Test
    void convert_to_entity_attribute() {
        // given
        var dbData = "PT1H30M30S";

        // when
        var duration = converter.convertToEntityAttribute(dbData);

        // then
        assertThat(duration).isEqualTo(Duration.ofHours(1).plusMinutes(30).plusSeconds(30));
    }
}
