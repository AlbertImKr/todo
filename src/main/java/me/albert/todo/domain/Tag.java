package me.albert.todo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;

@Table(name = "tag")
@Entity
public class Tag {

    @Getter
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToMany
    @JoinTable(
            name = "todo_tags",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "todo_id")
    )
    private List<Todo> todos;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }
}
