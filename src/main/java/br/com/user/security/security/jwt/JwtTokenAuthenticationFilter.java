package br.com.user.security.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static br.com.user.security.security.SecurityUtil.sendUnauthorized;

@Slf4j
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private JwtAuthenticationService jwtAuthenticationService;

    public JwtTokenAuthenticationFilter(JwtAuthenticationService jwtAuthenticationService) {
        this.jwtAuthenticationService = jwtAuthenticationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            authenticate(request);
            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            log.error("Failed to authenticate request", e);
            sendUnauthorized(response, "Invalid Jwt");
        }
    }

    private void authenticate(HttpServletRequest req) {
        jwtAuthenticationService
                .resolveToken(getAuthenticationHeader(req))
                .ifPresent(tk -> authenticateUser(tk, req));
    }

    private void authenticateUser(String token, HttpServletRequest req) {
        jwtAuthenticationService.getAuthentication(token)
                .ifPresent(auth -> {
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                });
    }

    private Optional<String> getAuthenticationHeader(HttpServletRequest req) {
        return Optional.ofNullable(req.getHeader("Authorization"));
    }
}
