package br.com.user.security.repository;

import br.com.user.security.domain.entity.Role;
import br.com.user.security.domain.entity.enu.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(RoleEnum role);
}
