package br.com.user.security.security.service;

import br.com.user.security.domain.dto.RoleDto;
import br.com.user.security.domain.dto.UserAndRoleDto;
import br.com.user.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.Collections.singletonList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) {
        UserAndRoleDto userAndRoleDto = userService.findByUserNameWithRole(userName)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with username: [%s]", userName)));

        List<GrantedAuthority> authorities = getUserAuthority(userAndRoleDto.getRole());

        return buildUserForAuthentication(userAndRoleDto, authorities);
    }

    private List<GrantedAuthority> getUserAuthority(RoleDto roleDto) {
        return singletonList(new SimpleGrantedAuthority(roleDto.getRoleName().getRoleValue()));
    }

    private UserDetails buildUserForAuthentication(UserAndRoleDto userAndRoleDto, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(userAndRoleDto.getUser().getUserName(), userAndRoleDto.getUser().getPassword(),
                userAndRoleDto.getUser().isActive(), true, true, true, authorities);
    }
}
