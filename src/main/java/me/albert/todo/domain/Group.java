package me.albert.todo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import me.albert.todo.exception.BusinessException;
import me.albert.todo.utils.ErrorMessages;
import org.springframework.http.HttpStatus;

@Table(name = "todo_group")
@Entity
public class Group {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Column(unique = true)
    private String name;
    @Getter
    private String description;
    @ManyToOne
    private Account owner;
    @Getter
    private LocalDateTime createdAt;
    @Getter
    private LocalDateTime updatedAt;
    @Getter
    @ManyToMany
    @JoinTable(name = "group_todo",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "todo_id")
    )
    private List<Todo> todos = new ArrayList<>();
    @Getter
    @ManyToMany
    @JoinTable(name = "group_account",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id")
    )
    private Set<Account> users = new HashSet<>();

    public Group() {
    }

    public Group(Long id) {
        this.id = id;
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
            throw new BusinessException("해당 그룹을 수정할 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        this.name = name;
        this.description = description;
        this.updatedAt = updatedAt;
    }

    public void assignTodos(Account account, List<Todo> todos) {
        if (!isOwner(account)) {
            throw new BusinessException("할 일을 할당할 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        todos.stream().filter(todo -> !this.contains(todo)).forEach(todo -> this.todos.add(todo));
    }

    public boolean contains(Todo todo) {
        return todos.contains(todo);
    }

    public void unassignTodos(Account account, List<Todo> todos) {
        if (!isOwner(account)) {
            throw new BusinessException("할 일을 해제할 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        todos.forEach(this.todos::remove);
    }

    public void addAccounts(Account currentAccount, List<Account> accounts) {
        if (!isOwner(currentAccount)) {
            throw new BusinessException(ErrorMessages.GROUP_ADD_USER_NOT_ALLOWED, HttpStatus.FORBIDDEN);
        }
        this.users.addAll(accounts);
    }

    public void removeAccounts(Account account, List<Account> accountsToRemove) {
        if (!isOwner(account)) {
            throw new BusinessException(ErrorMessages.GROUP_REMOVE_USER_NOT_ALLOWED, HttpStatus.FORBIDDEN);
        }
        accountsToRemove.forEach(users::remove);
    }

    public boolean isMember(Account currentAccount) {
        return users.contains(currentAccount);
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
        if (!(o instanceof Group group)) {
            return false;
        }
        return Objects.equals(getId(), group.getId());
    }
}
