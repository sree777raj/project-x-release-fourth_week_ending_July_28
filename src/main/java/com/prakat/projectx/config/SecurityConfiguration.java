package com.prakat.projectx.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prakat.projectx.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import static org.springframework.security.config.Customizer.withDefaults;
/**
 * The {@code SecurityConfiguration} class provides configuration for Spring Security in the application.
 * It configures the security filters, authentication provider, and authorization rules.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private static final Logger logger= LoggerFactory.getLogger(SecurityConfiguration.class);
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    /**
     * Configures the security filter chain for handling incoming HTTP requests.
     *
     * @param http The HttpSecurity object to configure security settings.
     * @return A SecurityFilterChain object representing the configured security filter chain.
     * @throws Exception If an error occurs during configuration.
     */

    ObjectMapper objectMapper=new ObjectMapper();

    private static final String[] PUBLIC_URL = {
        "/auth/**",
        "/api-docs",
        "/api-docs.yaml",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**",
        "/api-docs/**",
        "/swagger-ui/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http
                .cors(withDefaults())
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(PUBLIC_URL).permitAll()
                .requestMatchers("/actuator/health").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .exceptionHandling()
                .authenticationEntryPoint((request, response, e) ->
                {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write(objectMapper.writeValueAsString(new ErrorResponseDTO(403,
                            "Access Denied",
                            request.getRequestURI())));
                });
        return http.build();
    }
}
