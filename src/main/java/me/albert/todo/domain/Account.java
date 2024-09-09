package me.albert.todo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;

@Table(name = "account")
@Entity
public class Account {

    @Getter
    @Id
    @GeneratedValue
    private Long id;
    @Getter
    @Column(unique = true)
    private String username;
    private String password;
    @Getter
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Account() {
    }

    public Account(Long id) {
        this.id = id;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account account)) {
            return false;
        }
        return Objects.equals(id, account.id);
    }
}
