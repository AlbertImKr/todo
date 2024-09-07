package me.albert.todo.repository;

import java.util.List;
import java.util.Optional;
import me.albert.todo.domain.Account;
import me.albert.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    Optional<Todo> findByIdAndOwner(Long id, Account owner);

    /**
     * 할 일 그룹이 없는 할 일을 조회한다.
     *
     * @param id 할 일 ID
     * @return 할 일
     */
    Optional<Todo> findByIdAndGroupNull(Long id);

    List<Todo> findAllByIdInAndOwner(List<Long> todoIds, Account owner);
}
