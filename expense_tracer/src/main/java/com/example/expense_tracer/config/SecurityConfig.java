package com.example.expense_tracer.config;

import com.example.expense_tracer.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Отключаем CSRF для Postman
                .authorizeHttpRequests(auth -> auth
                        // 1. Регистрацию разрешаем всем (даже без логина)
                        .requestMatchers(HttpMethod.POST, "/user/v1/add").permitAll()

                        // 2. Только АДМИН может видеть всех, удалять и обновлять
                        .requestMatchers("/user/v1/all").hasRole("ADMIN")
                        .requestMatchers("/user/v1/delete/**").hasRole("ADMIN")
                        .requestMatchers("/user/v1/update/**").hasRole("ADMIN")

                        // 3. Просматривать конкретного юзера (id) разрешим и обычным юзерам
                        .requestMatchers("/user/v1/{id}").hasAnyRole("USER", "ADMIN")

                        // 4. Все остальные запросы требуют просто входа
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // Включаем Basic Auth

        return http.build();

    }
}