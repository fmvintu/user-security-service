package br.com.user.security.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ApiModel(value = "Request object to create a user")
public class SignupRequest {

    @ApiModelProperty(value = "Username (must be an email)", required = true)
    @NotNull(message = "Username is missing and must be an e-mail")
    @Email
    private String userName;

    @ApiModelProperty(value = "User password", required = true)
    @NotNull(message = "User password is missing")
    private String password;

    @ApiModelProperty(value = "Name", required = true)
    @NotNull(message = "Name is missing")
    private String name;

    @ApiModelProperty(value = "Last name", required = true)
    @NotNull(message = "Last name is missing")
    private String lastName;

    @ApiModelProperty(value = "User email", required = true)
    @NotNull(message = "User email is missing")
    @Email
    private String email;
}
