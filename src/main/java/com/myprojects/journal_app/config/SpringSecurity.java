package com.myprojects.journal_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.myprojects.journal_app.service.UserDetailServiceImpl;

@Configuration
@EnableMethodSecurity // Enables method-level security annotations (e.g., @PreAuthorize)
public class SpringSecurity {

    private final UserDetailServiceImpl userDetailsService;

    public SpringSecurity(UserDetailServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for testing; use tokens in production for API security
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").hasRole("ADMIN") // Restrict `/admin/**` paths to ADMIN role
                .requestMatchers("/journal/**", "/users/**").authenticated() // Authenticate `/journal/**` and `/users/**`
                .anyRequest().permitAll() // Allow all other requests without authentication
            )
            .httpBasic() // Enable HTTP Basic Authentication
            .and()
            .formLogin(form -> form // Optional: Add a form-based login for web clients
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/home", true)
            )
            .logout(logout -> logout // Optional: Customize logout behavior
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
