package br.com.user.security.service;

import br.com.user.security.domain.dto.RoleDto;
import br.com.user.security.domain.dto.UserDto;
import br.com.user.security.domain.entity.enu.RoleEnum;
import br.com.user.security.domain.mapper.UserDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SecurityFacadeService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDataMapper userDataMapper;

    public UserDto saveUserWithRoleUser(UserDto userDto) {
        RoleDto roleDto = roleService.getRole(RoleEnum.USER);

        return userService.saveUserWithRole(userDto, roleDto);
    }
}
