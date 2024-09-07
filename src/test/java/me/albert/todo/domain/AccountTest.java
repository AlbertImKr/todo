package me.albert.todo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("계정 관련 도메인 테스트")
class AccountTest {


    @DisplayName("계정의 아이디가 같으면 같은 계정이다.")
    @Test
    void account_is_same() {
        // given
        var account1 = new Account(1L);
        var account2 = new Account(1L);

        // when
        var result = account1.equals(account2);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("계정의 아이디가 다르면 다른 계정이다.")
    @Test
    void account_is_not_same() {
        // given
        var account1 = new Account(1L);
        var account2 = new Account(2L);

        // when
        var result = account1.equals(account2);

        // then
        assertThat(result).isFalse();
    }
}
