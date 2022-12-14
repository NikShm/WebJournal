package com.webjournal.security;

import com.webjournal.security.jwt.AuthEntryPointJwt;
import com.webjournal.security.jwt.AuthTokenFilter;
import com.webjournal.security.jwt.CustomAccessDeniedHandler;
import com.webjournal.service.auth.AuthServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class SecurityConfig
 * @since 10/18/2022 - 15.53
 **/
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final AuthServiceImpl userDetailsService;
    private final AuthEntryPointJwt unauthorizedHandler;
    private final CustomAccessDeniedHandler forbiddenHandler;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(AuthServiceImpl userDetailsService, AuthEntryPointJwt unauthorizedHandler, CustomAccessDeniedHandler forbiddenHandler, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.forbiddenHandler = forbiddenHandler;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .exceptionHandling().accessDeniedHandler(forbiddenHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/posts/top-per-month", "/api/posts/{id}", "/api/posts/similar-posts",
                        "/api/users/public/{id}", "/api/users/top", "/api/tags/{id}", "/api/tags/actual",
                        "/api/tags/tag={name}").permitAll()
                .antMatchers(HttpMethod.POST, "/api/posts/search", "/api/posts/by-user", "/api/users/search",
                        "/api/users/{id}/posts-approved", "/api/comments/search").permitAll()
                .anyRequest().authenticated();

        return http.build();
    }
}
