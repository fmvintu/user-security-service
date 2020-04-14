package br.com.user.security.repository;

import br.com.user.security.domain.entity.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {
    List<UserInfo> findAll();
    Optional<UserInfo> findByEmail(String email);

    @Query("Select userInfo From UserInfo userInfo Join userInfo.user Where userInfo.user.userName = :userName")
    Optional<UserInfo> findByUserName(@Param("userName") String userName);
}
