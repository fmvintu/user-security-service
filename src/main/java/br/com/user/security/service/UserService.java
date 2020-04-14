package br.com.user.security.service;

import br.com.user.security.cache.Caches;
import br.com.user.security.domain.dto.RoleDto;
import br.com.user.security.domain.dto.UserAndRoleDto;
import br.com.user.security.domain.dto.UserDto;
import br.com.user.security.domain.entity.User;
import br.com.user.security.domain.mapper.UserDataMapper;
import br.com.user.security.repository.RoleRepository;
import br.com.user.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDataMapper userDataMapper;

    @Cacheable(cacheNames = Caches.USERNAME_USERANDROLE_CACHE_NAME, key = "#userName", unless = "#result == null")
    public Optional<UserAndRoleDto> findByUserNameWithRole(String userName) {
        return userRepository.retrieveByUserNameWithRole(userName)
                .map(u -> userDataMapper.toUserAndRoleDto(u, u.getRole()));
    }

    @Cacheable(cacheNames = Caches.USERNAME_USER_CACHE_NAME, key = "#userName", unless = "#result == null")
    public Optional<UserDto> findByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .map(u -> userDataMapper.toUserDto(u));
    }

    public UserDto saveUserWithRole(UserDto userDto, RoleDto roleDto) {
        User user = userDataMapper.toUser(userDto, roleDto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        return userDataMapper.toUserDto(userRepository.save(user));
    }
}