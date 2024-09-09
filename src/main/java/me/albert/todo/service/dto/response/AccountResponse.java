package me.albert.todo.service.dto.response;

import me.albert.todo.domain.Account;

public record AccountResponse(
        Long id,
        String username,
        String email
) {

    public static AccountResponse from(Account account) {
        return new AccountResponse(account.getId(), account.getUsername(), account.getEmail());
    }
}
