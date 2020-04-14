package br.com.user.security.config.security;

import br.com.user.security.domain.entity.enu.RoleEnum;
import br.com.user.security.security.ApplicationSecurityCondition;
import br.com.user.security.security.ServiceAccessDeniedHandler;
import br.com.user.security.security.ServiceAuthenticationEntryPoint;
import br.com.user.security.security.jwt.JwtAuthenticationService;
import br.com.user.security.security.jwt.JwtTokenAuthenticationFilter;
import br.com.user.security.security.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@EnableWebSecurity //(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = "br.com.promotion.security")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${" + ApplicationSecurityCondition.ENABLE_SECURITY_PROP_NAME + ":true}")
    private boolean enforceSecurity;

    @Autowired(required = false)
    private JwtAuthenticationService jwtAuthenticationService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostConstruct
    public void init() {
        log.info("Loading Web Security Configuration");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        if(!enforceSecurity) {
            configureNoSecurity(http);
        } else {
            configureEnforcedSecurity(http);
        }
    }

    private void configureNoSecurity(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("*/**")
                .permitAll().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().anonymous().disable();
    }

    private void configureEnforcedSecurity(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry =
                http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .enableSessionUrlRewriting(false).and()
                        .csrf().disable()
                        .logout().disable()
                        .exceptionHandling()
                        .authenticationEntryPoint(new ServiceAuthenticationEntryPoint())
                        .accessDeniedHandler(new ServiceAccessDeniedHandler()).and()
                        .authorizeRequests();

        addSwaggerDefaults(registry);
        addApplicationDefaults(registry);

        registry.antMatchers("/**").authenticated().and()
                .addFilterBefore(new JwtTokenAuthenticationFilter(jwtAuthenticationService), UsernamePasswordAuthenticationFilter.class);
    }

    private void addSwaggerDefaults(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        // Swagger
        registry.antMatchers(HttpMethod.GET, "/webjars/**").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
                .antMatchers(HttpMethod.GET, "/v2/api-docs").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-resources").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-resources/configuration/ui").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-resources/configuration/security").permitAll();
    }

    private void addApplicationDefaults(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        registry
                .antMatchers(HttpMethod.POST, "/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/**").hasAnyAuthority(RoleEnum.USER.getRoleValue())
                .antMatchers(HttpMethod.POST, "/**").hasAnyAuthority(RoleEnum.USER.getRoleValue())
                .antMatchers(HttpMethod.PUT, "/**").hasAnyAuthority(RoleEnum.USER.getRoleValue())
                .antMatchers(HttpMethod.DELETE, "/**").hasAnyAuthority(RoleEnum.USER.getRoleValue())
                .antMatchers(HttpMethod.PATCH, "/**").hasAnyAuthority(RoleEnum.USER.getRoleValue());
    }
}
