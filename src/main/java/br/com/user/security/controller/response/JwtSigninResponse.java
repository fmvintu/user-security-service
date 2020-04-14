package br.com.user.security.controller.response;

import br.com.user.security.domain.dto.UserInfoDto;
import br.com.user.security.domain.dto.UserDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "User created object")
public class JwtSigninResponse {

    @ApiModelProperty(value = "Customer user name")
    private String username;

    @ApiModelProperty(value = "Customer name")
    private String name;

    @ApiModelProperty(value = "Customer last name")
    private String lastName;

    @ApiModelProperty(value = "Customer Email")
    private String email;

    public JwtSigninResponse(UserDto userDto, UserInfoDto userInfoDto) {
        this.username = userDto.getUserName();
        this.name = userInfoDto.getName();
        this.lastName = userInfoDto.getLastName();
        this.email = userInfoDto.getEmail();
    }
}
