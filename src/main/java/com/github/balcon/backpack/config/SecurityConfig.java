package com.github.balcon.backpack.config;

import com.github.balcon.backpack.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.github.balcon.backpack.config.ApplicationConfig.API_URL;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(security -> security
                        .requestMatchers(API_URL + "/admin/**").hasAuthority(Role.ADMIN.getAuthority())
                        .anyRequest().hasAnyAuthority(Role.USER.getAuthority(), Role.ADMIN.getAuthority()))
                .httpBasic(Customizer.withDefaults())
                .csrf(CsrfConfigurer::disable)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
