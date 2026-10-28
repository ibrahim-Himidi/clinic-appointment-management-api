package com.ibrahim.ClinicAppointmentManagementAPI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                // JWT tabanlı, cookie kullanmayan API için CSRF kapalı
                .csrf(AbstractHttpConfigurer::disable)

                // HTTP Session kullanılmayacak
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Endpoint yetkilendirmeleri
                .authorizeHttpRequests(auth -> auth

                        // Login herkese açık
                        .requestMatchers("/api/auth/login").permitAll()

                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/receptionist/**").hasAnyRole("RECEPTIONIST","ADMIN")
                        .requestMatchers("/api/doctor/**").hasAnyRole("DOCTOR","ADMIN")
                        .requestMatchers("/api/patients/**").hasAnyRole("PATIENT","ADMIN")
                        .anyRequest().authenticated())

                // Varsayılan Basic Authentication kapalı
                .httpBasic(AbstractHttpConfigurer::disable)

                // Varsayılan form login kapalı
                .formLogin(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
