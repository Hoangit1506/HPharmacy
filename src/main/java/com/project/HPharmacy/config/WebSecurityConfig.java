package com.project.HPharmacy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    // @Autowired private UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
        // encoder pwd to inject into UserEntityServiceImpl
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean //can be omitted
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
//
//    @Bean //can be omitted
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsServiceImpl);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }

    /*
     * https://docs.spring.io/spring-security/reference/servlet/authentication/session-management.html
     * "In Spring Security 6, the default is that authentication mechanisms themselves
     * must invoke the SessionAuthenticationStrategy."
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                                .requestMatchers("/", "/home", "/about", "/shop", "/login", "/registration", "/403", "/css/**", "/fonts/**", "/images/**", "/js/**", "/scss/**", "/uploads/**").permitAll()
                                // Nếu muốn cho khách vãng lai (chưa đăng nhập) xem danh sách các sản phẩm
                                .requestMatchers("/products/**", "/product-detail").permitAll()
                                .requestMatchers("/webjars/**").permitAll()
                                .requestMatchers("/products/management", "/products/add-edit-product").hasAuthority("ADMIN")

//                .requestMatchers("/profile/**").authenticated()
                                .anyRequest().authenticated()
                )
                .formLogin((formBased) -> {
                            formBased
//                .usernameParameter("email")  --> NEED <input type="email" name="email">
//                .passwordParameter("password")
                                    .loginPage("/login")
                                    .defaultSuccessUrl("/", true).permitAll()
                                    .failureUrl("/login?error=true").permitAll();
                        }
                )
                .logout((logout) -> {
                            logout
                                    .invalidateHttpSession(true)
                                    .clearAuthentication(true)
                                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                    .logoutSuccessUrl("/login?logout")
                                    .permitAll();
                        }
                )
                .exceptionHandling(except -> except
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .accessDeniedPage("/403")
                );

        // http.authenticationProvider(authenticationProvider()); //can be omitted

        return http.build();
    }
}
