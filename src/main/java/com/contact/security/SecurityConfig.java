package com.contact.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.security.config.Customizer;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/**")
                        .authenticated()
                        .anyRequest()
                        .permitAll()

                )

                // TODO Custom login
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login_process")
                        .failureUrl("/login?error=true")
                        .defaultSuccessUrl("/newHome", true)
                        .passwordParameter("password")
                        .usernameParameter("email")
                        .failureHandler((request, response, exception) -> {
                            // Redirect to login with a custom parameter
                            response.sendRedirect("/login?error=invalid_credentials");
                        })
                        .permitAll())

                // TODO Custom logout
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .rememberMe(rememberMe -> rememberMe
                        .key("unique")
                        .rememberMeParameter("rememberMe")
                        .tokenValiditySeconds(24 * 60 * 60))
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    // private LogoutHandler customLogoutHandler() {
    //     return (HttpServletRequest request, HttpServletResponse response, HttpSession session) -> {
    //         if (session != null) {
    //             session.removeAttribute("name");
    //         }
    //     };
    // }
}
