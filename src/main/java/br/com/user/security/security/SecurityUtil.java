package br.com.user.security.security;

import br.com.user.security.exception.BusinessException;
import br.com.user.security.util.JsonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Optional.ofNullable;

public class SecurityUtil {

    private static final String XFORWARDEDFOR_HEADER = "X-Forwarded-For";

    private SecurityUtil() {
    }

    public static void sendUnauthorized(ServletResponse response, String message) throws IOException {
        sendUnauthorized(response, new BusinessException(HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED, message));
    }

    public static void sendUnauthorized(ServletResponse response, BusinessException exception) throws IOException {
        response.getOutputStream().println(JsonUtil.writeValueAsString(exception));
        ((HttpServletResponse) response).setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }

    public static String getClientIpAddress(HttpServletRequest request) {
        return ofNullable(request.getHeader(XFORWARDEDFOR_HEADER))
                .orElse(request.getRemoteAddr());
    }
}
