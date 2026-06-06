package br.com.fiap.terraform.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final TerraFormAuthenticationEntryPoint authenticationEntryPoint;
    private final TerraFormAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(TerraFormAuthenticationEntryPoint authenticationEntryPoint,
            TerraFormAccessDeniedHandler accessDeniedHandler) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/health").permitAll()
                        .requestMatchers("/h2-console/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("VIEWER", "OPERATOR", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/**").hasAnyRole("OPERATOR", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasAnyRole("OPERATOR", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                        .requestMatchers("/ws/**").hasAnyRole("OPERATOR", "ADMIN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .httpBasic(basic -> basic.authenticationEntryPoint(authenticationEntryPoint))
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder,
            @Value("${terraform.security.admin-user:admin}") String adminUser,
            @Value("${terraform.security.admin-password:terraform-admin}") String adminPassword,
            @Value("${terraform.security.operator-user:operator}") String operatorUser,
            @Value("${terraform.security.operator-password:terraform-operator}") String operatorPassword,
            @Value("${terraform.security.viewer-user:viewer}") String viewerUser,
            @Value("${terraform.security.viewer-password:terraform-viewer}") String viewerPassword) {
        return new InMemoryUserDetailsManager(
                User.withUsername(adminUser)
                        .password(passwordEncoder.encode(adminPassword))
                        .roles("ADMIN", "OPERATOR", "VIEWER")
                        .build(),
                User.withUsername(operatorUser)
                        .password(passwordEncoder.encode(operatorPassword))
                        .roles("OPERATOR", "VIEWER")
                        .build(),
                User.withUsername(viewerUser)
                        .password(passwordEncoder.encode(viewerPassword))
                        .roles("VIEWER")
                        .build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
