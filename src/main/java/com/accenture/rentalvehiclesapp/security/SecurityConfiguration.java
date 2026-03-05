package com.accenture.rentalvehiclesapp.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity // Autorise la configuration via cette classe de filtre dans la méthode securityFilterChain
@EnableMethodSecurity(
        prePostEnabled = true,   // @PreAuthorize, @PostAuthorize, @PostFilter, @PreFilter
        securedEnabled = true,   // @Secured
        jsr250Enabled = true     // @RolesAllowed, @PermitAll, @DenyAll
)


public class SecurityConfiguration {


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html"
                                ).permitAll()
                                .requestMatchers(HttpMethod.POST, "/customers/**").permitAll()

                                .requestMatchers(HttpMethod.GET, "/customers/*").hasAnyRole("CUSTOMER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/customers/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/customers/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/customers/**").hasAnyRole("CUSTOMER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/customers/*").hasAnyRole("CUSTOMER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/customers/**").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/admins/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/admins/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/admins/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/admins/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/admins/**").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/cars/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/cars/**").hasAnyRole("CUSTOMER", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/cars/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/cars/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/cars/**").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/motorcycles/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/motorcycles/**").hasAnyRole("CUSTOMER", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/motorcycles/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/motorcycles/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/motorcycles/**").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/bicycles/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/bicycles/**").hasAnyRole("CUSTOMER", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/bicycles/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/bicycles/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/bicycles/**").hasRole("ADMIN")

//                                .requestMatchers(HttpMethod.POST, "/admins/**").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/persons/**").hasRole("USER")
//                        .requestMatchers(HttpMethod.PUT, "/persons/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PATCH, "/persons/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "/persons/**").hasAnyRole("ADMIN", "SUPERADMIN")
                                .anyRequest().authenticated()
//                                .anyRequest().permitAll()
                ).sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //@Bean
    //A FAIRE: IMPLEMENTER LA SECURITE AVEC LE LOGIN ET MDP EN BASE PAR LA SUITE
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("select login, password, 1  from utilisateur where login = ?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select login, role from utilisateur where login = ?");
        return jdbcUserDetailsManager;
    }

    @Bean
    UserDetailsManager inMemoryUserDetailsManager() {
        UserDetails customer = org.springframework.security.core.userdetails.User
                .withUsername("customer@example.com")
                .password(passwordEncoder().encode("customer")) //commande git pour checker le mot de passe $ echo "info dans le header de la réponse" | base64 -d
                .roles("CUSTOMER")
                .build();

        UserDetails admin = org.springframework.security.core.userdetails.User
                .withUsername("admin@example.com")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN", "CUSTOMER")
                .build();

        return new InMemoryUserDetailsManager(customer, admin);
    }

}
