package me.albert.todo.repository;

import java.util.Optional;
import me.albert.todo.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByUsername(String username);

    Optional<Account> findByUsername(String username);
}
