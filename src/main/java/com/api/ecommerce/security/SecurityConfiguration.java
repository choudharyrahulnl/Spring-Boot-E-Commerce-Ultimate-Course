package com.api.ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    /**
     * On App startup, Spring Security will look for a bean of type SecurityFilterChain
     * and use it to configure the security filter chain.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                //.requestMatchers("/users/**").hasRole("Admin") // Enable Authorization - hasRole insert ROLE_ prefix automatically & In DB, we have to insert ROLE_ prefix
                //.requestMatchers("/users/**").hasAnyRole("Admin") // Enable Authorization - Allow multiple roles
                //.requestMatchers("/v1/users/**").hasAuthority("Admin") // Enable Authorization does not insert ROLE_ prefix automatically & In DB, we have to insert ROLE_ prefix
                //.requestMatchers("/v1/users/**").hasAnyAuthority("Admin") // Enable Authorization Allow multiple roles
                //.anyRequest().permitAll(); // Disable security
                .anyRequest().authenticated() // Enable security
                .and()
                .formLogin().disable() // Disable form login
                .httpBasic(); // Enable HTTP Basic authentication

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return new ShopmeUserDetailsService();
    }

    /**
     * The DaoAuthenticationProvider is the default implementation of the
     * AuthenticationProvider interface. It uses a UserDetailsService to retrieve
     * user details and a PasswordEncoder to validate passwords.
     *
     * DaoAuthenticationProvider -> ShopmeUserDetailsService -> ShopmeUserDetails
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}
