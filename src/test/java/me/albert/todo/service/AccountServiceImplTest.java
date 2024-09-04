package me.albert.todo.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import me.albert.todo.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("AccountService 테스트")
@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @DisplayName("유저를 등록 성공하면 아무일도 일어나지 않아야 한다.")
    @Test
    void register_if_success() {
        // given
        String username = "test";
        String password = "password";
        when(accountRepository.existsByUsername(username)).thenReturn(false);

        // when, then
        assertThatCode(() -> accountService.register(username, password))
                .doesNotThrowAnyException();
    }

    @DisplayName("유저를 등록 시 이미 존재하는 유저면 예외가 발생해야 한다.")
    @Test
    void register_if_username_is_existed() {
        // given
        String username = "test";
        String password = "password";
        when(accountRepository.existsByUsername(username)).thenReturn(true);

        // when, then
        assertThatThrownBy(() -> accountService.register(username, password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(AccountServiceImpl.USERNAME_IS_EXISTED);
    }
}
