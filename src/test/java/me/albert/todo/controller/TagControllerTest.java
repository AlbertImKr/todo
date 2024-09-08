package me.albert.todo.controller;

import static me.albert.todo.controller.docs.TagDocument.createTagDocumentation;
import static me.albert.todo.controller.docs.TagDocument.searchTagDocumentation;
import static me.albert.todo.controller.steps.AccountSteps.getFixtureFirstAccountAccessToken;
import static me.albert.todo.controller.steps.TagSteps.태그_생성_요청;
import static me.albert.todo.controller.steps.TagSteps.태그_이름으로_태그_조회_요청;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import me.albert.todo.TodoAcceptanceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("태그 관련 인수 테스트")
class TagControllerTest extends TodoAcceptanceTest {

    String accessToken;

    @BeforeEach
    void setUser() {
        accessToken = getFixtureFirstAccountAccessToken();
    }

    @DisplayName("태그 이름으로 검색 성공 시 200 상태 코드를 반환한다.")
    @Test
    void search_tag() {
        // docs
        this.spec.filter(searchTagDocumentation());

        // given
        var body = new HashMap<>();
        var target = "tag";
        body.put("name", target);
        var expectedId = 태그_생성_요청(body, accessToken).jsonPath().getLong("id");
        var query = new HashMap<String, Object>();
        query.put("name", target);

        // when
        var response = 태그_이름으로_태그_조회_요청(query, accessToken, this.spec);

        // then
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(200),
                () -> assertThat(response.jsonPath().getLong("id")).isEqualTo(expectedId)
        );
    }

    @DisplayName("태그 생성 성공 시 201 상태 코드를 반환한다.")
    @Test
    void create_tag() {
        // docs
        this.spec.filter(createTagDocumentation());

        // given
        var body = new HashMap<>();
        body.put("name", "tag");

        // when
        var response = 태그_생성_요청(body, accessToken, this.spec);

        // then
        assertThat(response.statusCode()).isEqualTo(201);
    }

    @DisplayName("태그 이름으로 검색 실패 테스트")
    @Nested
    class SearchTagFail {

        @DisplayName("태그 이름이 없으면 400 상태 코드를 반환한다.")
        @Test
        void search_tag_without_name() {
            // given
            var body = new HashMap<String, Object>();
            body.put("name", "");

            // when
            var response = 태그_이름으로_태그_조회_요청(body, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("태그 이름이 20자를 넘으면 400 상태 코드를 반환한다.")
        @Test
        void search_tag_with_long_name() {
            // given
            var body = new HashMap<String, Object>();
            body.put("name", "a".repeat(21));

            // when
            var response = 태그_이름으로_태그_조회_요청(body, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("태그 이름이 null이면 400 상태 코드를 반환한다.")
        @Test
        void search_tag_with_null_name() {
            // given
            var body = new HashMap<String, Object>();
            body.put("name", null);

            // when
            var response = 태그_이름으로_태그_조회_요청(body, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }
    }

    @DisplayName("태그 생성 실패 테스트")
    @Nested
    class CreateTagFail {

        @DisplayName("태그 이름이 없으면 400 상태 코드를 반환한다.")
        @Test
        void create_tag_without_name() {
            // given
            var body = new HashMap<>();
            body.put("name", "");

            // when
            var response = 태그_생성_요청(body, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("태그 이름이 20자를 넘으면 400 상태 코드를 반환한다.")
        @Test
        void create_tag_with_long_name() {
            // given
            var body = new HashMap<>();
            body.put("name", "a".repeat(21));

            // when
            var response = 태그_생성_요청(body, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("태그 이름이 null이면 400 상태 코드를 반환한다.")
        @Test
        void create_tag_with_null_name() {
            // given
            var body = new HashMap<>();
            body.put("name", null);

            // when
            var response = 태그_생성_요청(body, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }
    }
}
