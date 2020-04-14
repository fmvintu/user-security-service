package br.com.user.security.domain.mapper;

import br.com.user.security.controller.request.SignupRequest;
import br.com.user.security.domain.dto.RoleDto;
import br.com.user.security.domain.dto.UserAndRoleDto;
import br.com.user.security.domain.dto.UserDto;
import br.com.user.security.domain.dto.UserInfoDto;
import br.com.user.security.domain.entity.Role;
import br.com.user.security.domain.entity.User;
import br.com.user.security.domain.entity.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.WARN)
public interface UserDataMapper {
    UserDataMapper INSTANCE = Mappers.getMapper(UserDataMapper.class);

    UserDto toUserDto(User user);
    User toUser(UserDto userDto);

    @Mapping(source = "userDto.id", target = "id")
    @Mapping(source = "roleDto.id", target = "role.id")
    @Mapping(source = "roleDto.roleName", target = "role.roleName")
    User toUser(UserDto userDto, RoleDto roleDto);

    UserDto toUserDto(SignupRequest signupRequest);

    RoleDto toRoleDto(Role role);
    Role toRole(RoleDto roleDto);

    @Mapping(source = "user", target = "user")
    @Mapping(source = "role", target = "role")
    UserAndRoleDto toUserAndRoleDto(User user, Role role);

    UserInfoDto toUserInfoDto(UserInfo userInfo);
    UserInfo toUserInfo(UserInfoDto userInfoDto);
    UserInfoDto toUserInfoDto(SignupRequest signupRequest);
}
