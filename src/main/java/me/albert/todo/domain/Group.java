package me.albert.todo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import me.albert.todo.domain.exception.AccessDeniedException;

@Table(name = "todo_group")
@Entity
public class Group {

    @Getter
    @Id
    @GeneratedValue
    private Long id;
    @Getter
    private String name;
    @Getter
    private String description;
    @OneToOne
    private Account owner;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Group() {
    }

    public Group(String name, String description, Account owner, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public boolean isOwner(Account account) {
        return owner.equals(account);
    }

    public void update(Account account, String name, String description, LocalDateTime updatedAt) {
        if (!isOwner(account)) {
            throw new AccessDeniedException("해당 그룹을 수정할 권한이 없습니다.");
        }
        this.name = name;
        this.description = description;
        this.updatedAt = updatedAt;
    }
}
