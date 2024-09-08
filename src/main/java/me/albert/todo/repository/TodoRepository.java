package me.albert.todo.repository;

import java.util.List;
import java.util.Optional;
import me.albert.todo.domain.Account;
import me.albert.todo.domain.Tag;
import me.albert.todo.domain.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    Optional<Todo> findByIdAndOwner(Long id, Account owner);

    /**
     * 그룹이 없는 소유한 할 일을 조회한다.
     *
     * @param id    할 일 ID
     * @param owner 사용자
     * @return 할 일
     */
    Optional<Todo> findByIdAndOwnerAndGroupNull(Long id, Account owner);

    /**
     * 할 일 그룹이 없는 할 일을 조회한다.
     *
     * @param id 할 일 ID
     * @return 할 일
     */
    Optional<Todo> findByIdAndGroupNull(Long id);

    List<Todo> findAllByIdInAndOwner(List<Long> todoIds, Account owner);

    @EntityGraph(attributePaths = {"tags"})
    Page<Todo> findAllWithTagsByOwnerAndGroupNull(Account owner, Pageable pageable);

    @EntityGraph(attributePaths = {"tags"})
    Page<Todo> findAllByTagsContainingAndOwnerAndGroupNull(Tag tag, Account owner, Pageable pageable);

    @EntityGraph(attributePaths = {"tags"})
    Page<Todo> findAllWithTagsByProjectIdAndOwner(long projectId, Account owner, Pageable pageable);
}
