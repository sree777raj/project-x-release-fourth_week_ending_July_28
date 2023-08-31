package com.prakat.projectx.config;

import com.prakat.projectx.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The {@code ApplicationConfig} class provides configuration for various beans used in the application.
 * It configures the user details service, authentication provider, authentication manager, and password encoder.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private static final Logger logger= LoggerFactory.getLogger(ApplicationConfig.class);
    private final UserRepository repository;

    /**
     * Creates a UserDetailsService bean to fetch user details from the UserRepository based on the provided username.
     *
     * @return A UserDetailsService implementation that retrieves user details from the UserRepository.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }

    /**
     * Creates an AuthenticationProvider bean to use the DaoAuthenticationProvider for user authentication.
     *
     * @return An AuthenticationProvider implementation using DaoAuthenticationProvider.
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Creates an AuthenticationManager bean to be used for authentication.
     *
     * @param config The AuthenticationConfiguration object used to obtain the AuthenticationManager.
     * @return An AuthenticationManager instance obtained from the AuthenticationConfiguration.
     * @throws Exception If an error occurs while obtaining the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Creates a PasswordEncoder bean to encode passwords for user authentication.
     *
     * @return A BCryptPasswordEncoder instance to encode passwords using the BCrypt algorithm.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
