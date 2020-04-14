package br.com.user.security.controller.response;

import br.com.user.security.domain.dto.UserInfoDto;
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
@ApiModel(value = "Response UserInfo object")
public class UserInfoResponse {

    @ApiModelProperty(value = "User name")
    private String name;

    @ApiModelProperty(value = "User last name")
    private String lastName;

    @ApiModelProperty(value = "User email")
    private String email;

    public UserInfoResponse(UserInfoDto userInfoDto) {
        this.name = userInfoDto.getName();
        this.lastName = userInfoDto.getLastName();
        this.email = userInfoDto.getEmail();
    }
}
