package br.com.user.security.service;

import br.com.user.security.domain.dto.UserDto;
import br.com.user.security.domain.dto.UserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserFacadeService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private SecurityFacadeService saveUserWithRoleUser;

    @Transactional(transactionManager = "transactionManager")
    public Pair<UserDto, UserInfoDto> createUserAndCustomer(UserDto userDto, UserInfoDto userInfoDto) {
        UserDto createdUserDto = saveUserWithRoleUser.saveUserWithRoleUser(userDto);
        UserInfoDto createdUserInfoDto = userInfoService.save(userInfoDto, createdUserDto);

        return Pair.of(createdUserDto, createdUserInfoDto);
    }
}
