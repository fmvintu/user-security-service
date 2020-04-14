package br.com.user.security.controller;

import br.com.user.security.controller.request.SigninRequest;
import br.com.user.security.controller.request.SignupRequest;
import br.com.user.security.controller.response.SigninResponse;
import br.com.user.security.controller.response.SignupResponse;
import br.com.user.security.domain.dto.UserDto;
import br.com.user.security.domain.dto.UserInfoDto;
import br.com.user.security.domain.mapper.UserDataMapper;
import br.com.user.security.exception.UserAlreadyExistsException;
import br.com.user.security.security.jwt.JwtAuthenticationService;
import br.com.user.security.service.UserFacadeService;
import br.com.user.security.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.data.util.Optionals.ifPresentOrElse;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(value = "Authentication API", produces = APPLICATION_JSON_VALUE)
@Slf4j
@Validated
@CrossOrigin
@RestController
@RequestMapping(path = "/auth", produces = APPLICATION_JSON_VALUE)
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDataMapper userDataMapper;

    @Autowired
    private UserFacadeService customerFacadeService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtAuthenticationService jwtAuthenticationService;

    @ApiOperation("Signup a user")
    @PostMapping(path = "/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignupResponse registerUser(@RequestBody @Valid SignupRequest signupRequest) {
        ifPresentOrElse(userService.findByUserName(signupRequest.getUserName()),
                u -> {
                        throw new UserAlreadyExistsException("USER_ALREADY_EXISTS", u.getUserName());
                    },
                () -> log.debug("New user signup: [{}]", signupRequest.getUserName()));

        UserDto createdUserDto = userDataMapper.toUserDto(signupRequest);
        UserInfoDto createUserInfoDto = userDataMapper.toUserInfoDto(signupRequest);

        Pair<UserDto, UserInfoDto> pair = customerFacadeService.createUserAndCustomer(createdUserDto, createUserInfoDto);

        return new SignupResponse(pair.getFirst(), pair.getSecond());
    }

    @ApiOperation("Signin a user")
    @PostMapping(path = "/signin")
    @ResponseStatus(HttpStatus.OK)
    public SigninResponse authenticateUser(@RequestBody @Valid SigninRequest signinRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getUserName(), signinRequest.getPassword()));
        String jwt = jwtAuthenticationService.createTokenRoleUser(signinRequest.getUserName());

        return new SigninResponse(signinRequest.getUserName(), jwt);
    }
}
