package br.com.user.security.controller.response;

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
@ApiModel(value = "User signed in object")
public class SigninResponse {

    @ApiModelProperty(value = "Customer username")
    private String username;

    @ApiModelProperty(value = "Signed jwt")
    private String jwt;
}
