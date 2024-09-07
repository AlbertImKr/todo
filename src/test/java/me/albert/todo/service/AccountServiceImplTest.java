package me.albert.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import me.albert.todo.domain.Account;
import me.albert.todo.exception.BusinessException;
import me.albert.todo.repository.AccountRepository;
import me.albert.todo.utils.ErrorMessages;
import me.albert.todo.utils.JwtTokenProvider;
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

    @Mock
    private JwtTokenProvider jwtTokenProvider;

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
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.USERNAME_IS_EXISTED);
    }

    @DisplayName("유저를 로그인 성공하면 TokenResponse를 반환해야 한다.")
    @Test
    void login_if_success() {
        // given
        String username = "test";
        String password = "password";
        when(accountRepository.findByUsername(username)).thenReturn(
                java.util.Optional.of(new Account(username, password)));

        // when
        var result = accountService.login(username, password);

        // then
        assertThat(result).isNotNull();
    }

    @DisplayName("유저를 로그인 시 유저 이름이 존재하지 않으면 예외가 발생해야 한다.")
    @Test
    void login_if_username_not_exist() {
        // given
        String username = "test";
        String password = "password";
        when(accountRepository.findByUsername(username)).thenReturn(java.util.Optional.empty());

        // when, then
        assertThatThrownBy(() -> accountService.login(username, password))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.USERNAME_OR_PASSWORD_NOT_MATCHED);
    }

    @DisplayName("유저를 로그인 시 비밀번호가 일치하지 않으면 예외가 발생해야 한다.")
    @Test
    void login_if_password_not_matched() {
        // given
        String username = "test";
        String password = "password";
        when(accountRepository.findByUsername(username)).thenReturn(
                java.util.Optional.of(new Account(username, "invalid")));

        // when, then
        assertThatThrownBy(() -> accountService.login(username, password))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.USERNAME_OR_PASSWORD_NOT_MATCHED);
    }

    @DisplayName("유저 이름으로 사용자를 조회하면 사용자 정보를 반환해야 한다.")
    @Test
    void find_by_username_if_success() {
        // given
        String username = "test";
        when(accountRepository.findByUsername(username)).thenReturn(
                java.util.Optional.of(new Account(username, "password")));

        // when
        var result = accountService.findByUsername(username);

        // then
        assertThat(result).isNotNull();
    }

    @DisplayName("유저 이름으로 사용자를 조회 시 존재하지 않으면 예외가 발생해야 한다.")
    @Test
    void find_by_username_if_not_exist() {
        // given
        String username = "test";
        when(accountRepository.findByUsername(username)).thenReturn(java.util.Optional.empty());

        // when, then
        assertThatThrownBy(() -> accountService.findByUsername(username))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.USERNAME_NOT_EXISTED);
    }
}
