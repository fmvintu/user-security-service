package br.com.user.security.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@ApiModel(value = "Request object to create a user")
public class SigninRequest {

    @ApiModelProperty(value = "Username", required = true)
    @NotNull(message = "Username is missing")
    @Email
    private String userName;

    @ApiModelProperty(value = "User password", required = true)
    @NotNull(message = "User password is missing")
    private String password;
}
