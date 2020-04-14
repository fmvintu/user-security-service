package br.com.user.security.service;

import br.com.user.security.cache.Caches;
import br.com.user.security.domain.dto.RoleDto;
import br.com.user.security.domain.entity.enu.RoleEnum;
import br.com.user.security.domain.mapper.UserDataMapper;
import br.com.user.security.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserDataMapper userDataMapper;

    @Cacheable(cacheNames = Caches.ROLE_CACHE_NAME, key = "#p0.roleValue", unless = "#result == null")
    public RoleDto getRole(RoleEnum roleEnum) {
        return userDataMapper.toRoleDto(roleRepository.findByRoleName(roleEnum));
    }
}
