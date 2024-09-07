package me.albert.todo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import me.albert.todo.exception.BusinessException;
import me.albert.todo.utils.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("프로젝트 도메인 테스트")
class ProjectTest {

    Project project;
    Account account;

    @BeforeEach
    void setUp() {
        account = new Account(1L);
        project = new Project(1L, account);
    }

    @DisplayName("프로젝트를 수정한다")
    @Test
    void update_project() {
        // given
        var name = "프로젝트";

        // when
        project.update(name, account);

        // then
        assertThat(project.getName()).isEqualTo(name);
    }

    @DisplayName("프로젝트를 수정 시 계정이 다르면 예외를 발생한다")
    @Test
    void update_project_with_different_account() {
        // given
        var name = "프로젝트";
        var anotherAccount = new Account(2L);

        // when & then
        assertThatThrownBy(() -> project.update(name, anotherAccount))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.PROJECT_UPDATE_NOT_ALLOWED);
    }
}
