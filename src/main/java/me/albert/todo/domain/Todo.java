package me.albert.todo.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import me.albert.todo.exception.BusinessException;
import me.albert.todo.utils.ErrorMessages;
import org.springframework.http.HttpStatus;

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
    @Getter
    @ManyToOne
    private Group group;
    @Getter
    @Enumerated(EnumType.STRING)
    private TodoPriority priority;
    @Getter
    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private RecurringTask recurringTask;
    @Getter
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Tag> tags = new ArrayList<>();
    @Getter
    @ManyToMany
    @JoinTable(name = "todo_assignee",
            joinColumns = @JoinColumn(name = "todo_id"),
            inverseJoinColumns = @JoinColumn(name = "assignee_id")
    )
    private List<Account> assignees = new ArrayList<>();
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
    @Getter
    @ElementCollection
    @CollectionTable(name = "notification_setting", joinColumns = @JoinColumn(name = "todo_id"))
    private List<Duration> notificationSettings = new ArrayList<>();

    public Todo() {
    }

    public Todo(Long id) {
        this.id = id;
    }

    public Todo(
            String title, String description, LocalDateTime localDateTime, Account account, LocalDateTime createdAt,
            LocalDateTime updatedAt, TodoStatus status, TodoPriority priority
    ) {
        this.title = title;
        this.description = description;
        this.dueDate = localDateTime;
        this.owner = account;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.priority = priority;
    }

    public void update(
            String title,
            String description,
            LocalDateTime dueDate,
            LocalDateTime updatedAt,
            TodoStatus status,
            Account owner
    ) {
        if (!isOwner(owner)) {
            throw new BusinessException(ErrorMessages.TODO_UPDATE_NOT_ALLOWED, HttpStatus.FORBIDDEN);
        }
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.updatedAt = updatedAt;
        this.status = status;
    }

    public void updateStatus(TodoStatus status, LocalDateTime updatedAt, Account owner) {
        if (!isOwner(owner)) {
            throw new BusinessException(ErrorMessages.TODO_UPDATE_NOT_ALLOWED, HttpStatus.FORBIDDEN);
        }
        this.status = status;
        this.updatedAt = updatedAt;
    }

    public void assignUser(Account assignee) {
        if (this.assignees.contains(assignee)) {
            return;
        }
        this.assignees.add(assignee);
    }

    public void unassignUser(Account assignee) {
        this.assignees.remove(assignee);
    }

    public boolean containsAssignee(Account assignee) {
        return this.assignees.contains(assignee);
    }

    public boolean isOwner(Account owner) {
        return this.owner.equals(owner);
    }

    public void assignToProject(Project project) {
        this.project = project;
    }

    public void unassignFromProject() {
        this.project = null;
    }

    public boolean containsTag(Tag tag) {
        return this.tags.contains(tag);
    }

    public void assignTag(Tag tag) {
        if (this.containsTag(tag)) {
            return;
        }
        this.tags.add(tag);
    }

    public void unassignTag(Tag tag) {
        this.tags.remove(tag);
    }

    public void updateRecurringTask(RecurringTask savedRecurringTask) {
        this.recurringTask = savedRecurringTask;
    }

    public void removeRecurringTask() {
        this.recurringTask = null;
    }

    public void updatePriority(TodoPriority priority, Account owner) {
        if (!isOwner(owner)) {
            throw new BusinessException(ErrorMessages.TODO_UPDATE_NOT_ALLOWED, HttpStatus.FORBIDDEN);
        }
        this.priority = priority;
    }

    public void updateNotificationSettings(List<Duration> durations, Account owner) {
        if (!isOwner(owner)) {
            throw new BusinessException(ErrorMessages.TODO_UPDATE_NOT_ALLOWED, HttpStatus.FORBIDDEN);
        }
        this.notificationSettings.clear();
        this.notificationSettings.addAll(durations);

    }

    public void deleteNotificationSettings(Account owner) {
        if (!isOwner(owner)) {
            throw new BusinessException(ErrorMessages.TODO_UPDATE_NOT_ALLOWED, HttpStatus.FORBIDDEN);
        }
        this.notificationSettings.clear();
    }

    public void assignGroup(Group group) {
        this.group = group;
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
