package com.professionallawnservices.app.config;

import com.professionallawnservices.app.helpers.SecurityHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.util.ArrayList;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        /*
        Contains the directories that can be accessed prior to logging in. This content is static and is necessary
        for page layout.
         */

        String[] staticResources = {
                "/css/**",
                "/images/**",
                "/js/**",
                "/assets/**"
        };

        /*
        Contains the pages that only users with the manager role can access.
         */

        String[] highPages = {
                "/add-appointment/**",
                "/add-contact/**",
                "/appointments/**",
                "/contacts/**",
                "/customers/**",
                "/statistics/**",
                "/manager/**"
        };

        //Added .cors().and().csrf().disable() to allow for post requests

        http.cors().and().csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        //.antMatchers("/**").permitAll() //Allows access to pages without logging in
                        .antMatchers(staticResources).permitAll()
                        .antMatchers(highPages).hasAnyRole("MANAGER", "ADMIN", "OWNER")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/home", true)
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager()
    {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);


/*
        jdbcUserDetailsManager.createUser(User.withUsername("3").password(passwordEncoder().encode("3"))
               .authorities("ROLE_OWNER").build());

        userDetails.createUser(User.withUsername("2").password(SecurityHelpers.passwordEncoder().encode("2"))
               .authorities("ROLE_EMPLOYEE", "ROLE_USER").build());

         */



        return jdbcUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}