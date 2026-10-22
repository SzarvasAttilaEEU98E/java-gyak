
package hu.nje.javagyakbead.config;

import hu.nje.javagyakbead.service.CustomUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public WebSecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            PasswordEncoder passwordEncoder
    ) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {

        return (
                HttpServletRequest request,
                HttpServletResponse response,
                Authentication authentication
        ) -> {

            if (authentication.getAuthorities().contains(
                    new SimpleGrantedAuthority("ROLE_ADMIN")
            )) {

                response.sendRedirect(
                        request.getContextPath() + "/admin"
                );

            } else {

                response.sendRedirect(
                        request.getContextPath() + "/user"
                );
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationProvider authenticationProvider,
            AuthenticationSuccessHandler authenticationSuccessHandler
    ) throws Exception {

        http
                .authenticationProvider(authenticationProvider)

                .authorizeHttpRequests(auth -> auth

                        // Nyilvánosan elérhető oldalak
                        .requestMatchers(
                                "/",
                                "/register",
                                "/login",
                                "/kapcsolat",
                                "/adatbazis",
                                "/filmek",
                                "/mozik",
                                "/eloadasok",
                                "/diagram",
                                "/rest",
                                "/css/**",
                                "/js/**"
                        ).permitAll()

                        // Nyilvános REST API GET végpontok
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/**"
                        ).permitAll()

                        // Adminisztrációs oldalak
                        .requestMatchers(
                                "/admin",
                                "/admin/**"
                        ).hasRole("ADMIN")

                        // Bejelentkezett felhasználók
                        .requestMatchers(
                                "/user",
                                "/user/**"
                        ).hasAnyRole("USER", "ADMIN")

                        // Üzenetek megtekintése
                        .requestMatchers(
                                "/uzenetek"
                        ).hasAnyRole("USER", "ADMIN")

                        // Minden más végpont bejelentkezést igényel
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(authenticationSuccessHandler)
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )

                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/access-denied")
                );

        return http.build();
    }
}
