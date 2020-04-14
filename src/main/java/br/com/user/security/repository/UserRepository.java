package br.com.user.security.repository;

import br.com.user.security.domain.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);

    @EntityGraph(attributePaths = {"role"})
    @Query("Select user From User user Where user.userName = :userName")
    Optional<User> retrieveByUserNameWithRole(@Param("userName") String userName);
}