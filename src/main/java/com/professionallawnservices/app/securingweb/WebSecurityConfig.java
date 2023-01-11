package com.professionallawnservices.app.securingweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        /*
        Contains the directories that can be accessed prior to logging in.
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

        String[] managerPages = {
                "/add-appointment**",
                "/add-contact**",
                "/appointments**",
                "/contacts**",
                "/statistics**",
                "/manager**"
        };

        http
                .authorizeHttpRequests((requests) -> requests
                        //.antMatchers("/", "/home").permitAll()
                        .antMatchers(staticResources).permitAll()
                        .antMatchers(managerPages).hasRole("MANAGER")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/", true)
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        List<UserDetails> userDetailsList = new ArrayList<>();
        userDetailsList.add(User.withUsername("employee").password(passwordEncoder().encode("password"))
                .roles("EMPLOYEE", "USER").build());
        userDetailsList.add(User.withUsername("manager").password(passwordEncoder().encode("password"))
                .roles("MANAGER", "USER").build());

        return new InMemoryUserDetailsManager(userDetailsList);
    }
}