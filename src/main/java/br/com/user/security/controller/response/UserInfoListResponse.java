package br.com.user.security.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@ApiModel(value = "Response list customer object")
public class UserInfoListResponse {

    @ApiModelProperty(value = "Customers information")
    private List<UserInfoResponse> userInfoResponse;
}
