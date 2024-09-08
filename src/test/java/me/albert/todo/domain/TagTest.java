package me.albert.todo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("태그 도메인 테스트")
class TagTest {

    @DisplayName("태그 아이디가 같으면 같은 태그로 판단한다.")
    @Test
    void same_tag() {
        // given
        var tag1 = new Tag(1L, "tag1");
        var tag2 = new Tag(1L, "tag2");

        // when
        assertThat(tag1).isEqualTo(tag2);
    }
}
