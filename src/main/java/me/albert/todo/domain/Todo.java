package me.albert.todo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;

@Table(name = "todo")
@Entity
public class Todo {

    @Getter
    @Id
    @GeneratedValue
    private Long id;
    @Getter
    private String title;
    @Column(length = 1000)
    @Getter
    private String description;
    @Getter
    @Enumerated(EnumType.STRING)
    private TodoStatus status;
    @Getter
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    @Getter
    private LocalDateTime updatedAt;
    @ManyToOne
    private Account owner;
    @ManyToOne
    private Group group;

    public Todo() {
    }

    public Todo(
            String title, String description, LocalDateTime localDateTime, Account account, LocalDateTime createdAt,
            LocalDateTime updatedAt, TodoStatus status
    ) {
        this.title = title;
        this.description = description;
        this.dueDate = localDateTime;
        this.owner = account;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }

    public void update(
            String title,
            String description,
            LocalDateTime dueDate,
            LocalDateTime updatedAt,
            TodoStatus status
    ) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.updatedAt = updatedAt;
        this.status = status;
    }
}
