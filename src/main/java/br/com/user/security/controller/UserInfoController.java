package br.com.user.security.controller;

import br.com.user.security.controller.response.UserInfoListResponse;
import br.com.user.security.controller.response.UserInfoResponse;
import br.com.user.security.exception.UserNotFoundException;
import br.com.user.security.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(value = "UserInfo API", produces = APPLICATION_JSON_VALUE)
@Slf4j
@Validated
@CrossOrigin
@RestController
@RequestMapping(path = "/userInfos", produces = APPLICATION_JSON_VALUE)
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation("Get the user info with the specified id")
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserInfoResponse findOne(@PathVariable(value = "id") Long id) {
        return userInfoService.getUserInfo(id)
                .map(UserInfoResponse::new)
                .orElseThrow(() -> new UserNotFoundException("CUSTOMER_NOT_FOUND", id));
    }

    @ApiOperation("Get the user info with the specified userName")
    @GetMapping(path = "/userInfo")
    @ResponseStatus(HttpStatus.OK)
    public UserInfoResponse findByUserName(@RequestParam("userName") String userName) {
        return userInfoService.getByUserName(userName)
                .map(UserInfoResponse::new)
                .orElseThrow(() -> new UserNotFoundException("USER_NOT_FOUND", userName));
    }

    @ApiOperation("List all userinfo")
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public UserInfoListResponse findAll() {
        return userInfoService.getAllUserInfo()
                .map(l -> l.stream()
                        .map(UserInfoResponse::new)
                        .collect(collectingAndThen(toList(), UserInfoListResponse::new)))
                .orElseThrow(() -> new UserNotFoundException("NO_USER_FOUND"));
    }
}
