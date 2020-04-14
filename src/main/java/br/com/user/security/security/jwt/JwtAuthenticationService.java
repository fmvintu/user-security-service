package br.com.user.security.security.jwt;

import br.com.user.security.domain.entity.enu.RoleEnum;
import br.com.user.security.security.jwt.exception.InvalidJwtAuthenticationException;
import br.com.user.security.security.jwt.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

@Slf4j
@Component
public class JwtAuthenticationService {
    private static final String BEARER_TOKEN = "Bearer ";

    private static final String JWT_ROLES_ENTRY = "roles";

	@Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserDetailsService userDetailsService;
    
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes());
    }

    public String createTokenRoleUser(String username) {
        return createToken(username, singletonList(RoleEnum.USER.getRoleValue()));
    }

    public String createToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(JWT_ROLES_ENTRY, roles);

        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getValidityInMs());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public Optional<UsernamePasswordAuthenticationToken> getAuthentication(String token) {
        return ofNullable(getSubject(token))
                .map(sbj -> userDetailsService.loadUserByUsername(sbj))
                .filter(Objects::nonNull)
                .map(userDetails -> new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
    }

    private String getSubject(String token) {
        return tryValidateJwtGetSubject(token);
    }

    private String tryValidateJwtGetSubject(String token) {
        try {
            Jws<Claims> claimsJws = getTokenClaims(token);
            Claims claims = claimsJws.getBody();
            validateClaims(claims);

            return getJwtSubject(claims);
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Jwt is invalid", e);
            throw new InvalidJwtAuthenticationException("Invalid JWT token");
        }
    }

    private String getJwtSubject(Claims claims) {
        String subject = claims.getSubject();
        if(isNull(subject)) {
            throw new JwtException("Jwt missing identification information");
        }

        return subject;
    }

    private void validateClaims(Claims claims) {
        if(claims.getExpiration().before(new Date())) {
            throw new JwtException("Jwt expired");
        }

        List<String> roles = claims.get(JWT_ROLES_ENTRY, List.class);
        if(isNull(roles) || roles.isEmpty()) {
            throw new JwtException("Jwt missing identification information");
        }
    }

    private Jws<Claims> getTokenClaims(String token) {
        return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
    }

    public Optional<String> resolveToken(Optional<String> authorization) {
        return authorization.filter(this::isBearer)
                    .map(tk -> tk.substring(BEARER_TOKEN.length(), tk.length()));
    }

    private boolean isBearer(String token) {
        return token.startsWith(BEARER_TOKEN);
    }

    public void setJwtProperties(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }
}
