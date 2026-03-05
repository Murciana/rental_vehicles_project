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
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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


    private static final String CUSTOMERS = "/customers/**";
    private static final String ADMINS = "/admins/**";
    private static final String CARS = "/cars/**";
    private static final String MOTORCYCLES = "/motorcycles/**";
    private static final String BICYCLES = "/bicycles/**";
    private static final String CUSTOMER = "CUSTOMER";
    private static final String ADMIN = "ADMIN";

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
                                .requestMatchers(HttpMethod.POST, CUSTOMERS).permitAll()

                                .requestMatchers(ADMINS).hasRole(ADMIN)

                                .requestMatchers(HttpMethod.GET, CUSTOMERS).hasAnyRole(CUSTOMER, ADMIN)
                                .requestMatchers(CUSTOMERS).hasRole(ADMIN)

                                .requestMatchers(HttpMethod.GET, CARS, MOTORCYCLES, BICYCLES).hasAnyRole(CUSTOMER, ADMIN)
                                .requestMatchers(CARS, MOTORCYCLES, BICYCLES).hasRole(ADMIN)

//                        .anyRequest().authenticated()
                                .anyRequest().hasRole(ADMIN)
                ).sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                """
                                SELECT email, password, 1
                                FROM (
                                    SELECT email, password, 1 FROM customer
                                    UNION ALL
                                    SELECT email, password, 1 FROM admin
                                ) AS all_users
                                WHERE email = ?
                        """
        );

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                """
                                          SELECT email, role
                                          FROM (
                                              SELECT email,
                                                          CASE WHEN role = 0 THEN 'ROLE_CUSTOMER'
                                                              WHEN role = 1 THEN 'ROLE_ADMIN'
                                                          END AS role
                                              FROM customer
                                              UNION ALL
                                              SELECT email,
                                                     CASE WHEN role = 0 THEN 'ROLE_CUSTOMER'
                                                          WHEN role = 1 THEN 'ROLE_ADMIN'
                                                  END AS role
                                              FROM admin
                                          ) AS all_users
                                          WHERE email = ?
                        """
        );
        return jdbcUserDetailsManager;
    }
}
