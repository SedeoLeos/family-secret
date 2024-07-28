package org.slaega.family_secret.passwordless;

import org.slaega.family_secret.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtConfig jwtConfig;

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    PasswordLessAuthenticationProvider passwordLessAuthenticationProvider() {
        return new PasswordLessAuthenticationProvider();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(ar -> ar.requestMatchers("api/auth").permitAll()
                .requestMatchers("api/auth/**").permitAll()
                .requestMatchers("swagger-ui/").permitAll()
                .requestMatchers("swagger-ui/**").permitAll()
                .requestMatchers("api-docs/").permitAll()
                .requestMatchers("api-docs/**").permitAll()
                .anyRequest().permitAll())
                .authenticationProvider(passwordLessAuthenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    JWTAuthenticationFilter jwtAuthenticationFilter() {
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter();
        jwtAuthenticationFilter.setUserRepository(userRepository);

        jwtAuthenticationFilter.setJwtUtil(new JwtUtil(jwtConfig.getAccessSecret(), jwtConfig.getMagicLinkExpired()));
        return jwtAuthenticationFilter;
    }

}
