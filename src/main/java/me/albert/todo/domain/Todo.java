package me.albert.todo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    @Getter
    private LocalDateTime createdAt;
    @Getter
    private LocalDateTime updatedAt;
    @ManyToOne
    private Account owner;
    @ManyToOne
    private Group group;
    @ManyToMany(mappedBy = "todos")
    private List<Tag> tags;
    @Getter
    @ManyToMany
    @JoinTable(name = "todo_assignee",
            joinColumns = @JoinColumn(name = "todo_id"),
            inverseJoinColumns = @JoinColumn(name = "assignee_id")
    )
    private List<Account> assignees = new ArrayList<>();

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

    public void updateStatus(TodoStatus status, LocalDateTime updatedAt) {
        this.status = status;
        this.updatedAt = updatedAt;
    }

    public void assignUser(Account assignee) {
        if (this.assignees.contains(assignee)) {
            return;
        }
        this.assignees.add(assignee);
    }

    public boolean containsAssignee(Account assignee) {
        return this.assignees.contains(assignee);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Todo todo)) {
            return false;
        }
        return Objects.equals(getId(), todo.getId());
    }
}
