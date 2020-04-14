package br.com.user.security.security;

import br.com.user.security.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static br.com.user.security.security.SecurityUtil.getClientIpAddress;
import static br.com.user.security.security.SecurityUtil.sendUnauthorized;

@Slf4j
public class ServiceAccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.info("Access denied to: [" + request.getMethod() + "][" + request.getRequestURI() + "] " +
				"From: [" + getClientIpAddress(request) + "] - Exception: [" + accessDeniedException.getMessage() + "]");

		sendUnauthorized(response, new BusinessException(HttpStatus.UNAUTHORIZED.toString(), accessDeniedException.getMessage(), accessDeniedException.toString()));
	}
}
