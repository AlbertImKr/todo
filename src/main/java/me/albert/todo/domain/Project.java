package me.albert.todo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import me.albert.todo.exception.BusinessException;
import me.albert.todo.utils.ErrorMessages;
import org.hibernate.annotations.BatchSize;
import org.springframework.http.HttpStatus;

@Table(name = "project")
@Entity
public class Project {

    @Getter
    @Id
    @GeneratedValue
    private Long id;
    @Getter
    private String name;
    @Getter
    @BatchSize(size = 100)
    @OneToMany(mappedBy = "project")
    private List<Todo> todos = new ArrayList<>();
    @ManyToOne
    @JoinTable(name = "group_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Group group;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Account owner;

    public Project() {
    }

    public Project(Long id) {
        this.id = id;
    }

    public Project(String name, Account account) {
        this.name = name;
        this.owner = account;
    }

    public Project(long id, Account account) {
        this.id = id;
        this.owner = account;
    }

    public void update(String name, Account account) {
        if (!isOwner(account)) {
            throw new BusinessException(ErrorMessages.PROJECT_UPDATE_NOT_ALLOWED, HttpStatus.FORBIDDEN);
        }
        this.name = name;
    }

    public boolean isOwner(Account account) {
        return owner.equals(account);
    }

    public void assignTodos(List<Todo> todos) {
        todos.forEach(
                todo -> {
                    if (this.todos.contains(todo)) {
                        return;
                    }
                    this.todos.add(todo);
                    todo.assignToProject(this);
                }
        );
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
        if (!(o instanceof Project project)) {
            return false;
        }
        return Objects.equals(id, project.id);
    }
}
