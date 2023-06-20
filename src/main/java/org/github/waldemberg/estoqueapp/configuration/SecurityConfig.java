package org.github.waldemberg.estoqueapp.configuration;

import org.github.waldemberg.estoqueapp.configuration.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    private static void customizeSession(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry requests) {
        requests
                .antMatchers("/login/**").permitAll()
                .antMatchers("/pedidos").hasAuthority("ler_pedido")
                .antMatchers("/produtos").hasAuthority("ler_produto")
                .antMatchers("/usuarios").hasAuthority("ler_usuario")
                .anyRequest().authenticated();
    }

    private static void customizeSession(SessionManagementConfigurer<HttpSecurity> session) {
        session.
                sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .sessionFixation().migrateSession()
                .sessionAuthenticationErrorUrl("/login?error")
                .sessionAuthenticationFailureHandler((request, response, exception) ->
                        response.sendRedirect("/login?error")
                );
    }

    private static void customizeFormLogin(FormLoginConfigurer<HttpSecurity> form) {
        try {
            form
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/")
                    .permitAll()
                    .and()
                    .rememberMe()
                    .tokenValiditySeconds(86400); // Define a duração do cookie em segundos (1 dia);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests(SecurityConfig::customizeSession)
                .sessionManagement(SecurityConfig::customizeSession)
                .formLogin(SecurityConfig::customizeFormLogin)
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling()
                .accessDeniedPage("/access-denied");

        return http.build();
    }
}
