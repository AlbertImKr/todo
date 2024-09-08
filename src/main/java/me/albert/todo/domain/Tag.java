package me.albert.todo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;

@Table(name = "tag")
@Entity
public class Tag {

    @Getter
    @Id
    @GeneratedValue
    private Long id;
    @Getter
    @Column(nullable = false, unique = true)
    private String name;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public Tag(long id, String name) {
        this.id = id;
        this.name = name;
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
        if (!(o instanceof Tag tag)) {
            return false;
        }
        return Objects.equals(getId(), tag.getId());
    }
}
