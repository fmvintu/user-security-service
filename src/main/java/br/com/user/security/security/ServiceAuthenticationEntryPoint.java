package br.com.user.security.security;

import br.com.user.security.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static br.com.user.security.security.SecurityUtil.getClientIpAddress;
import static br.com.user.security.security.SecurityUtil.sendUnauthorized;

@Slf4j
public class ServiceAuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		log.info("Forbidden access to: [" + request.getMethod() + "][" + request.getRequestURI() + "] " +
				"From: [" + getClientIpAddress(request) + "] - Exception: [" + authException.getMessage() + "]");

		sendUnauthorized(response, new BusinessException(HttpStatus.UNAUTHORIZED.toString(), authException.getMessage(), authException.toString()));
	}
}
