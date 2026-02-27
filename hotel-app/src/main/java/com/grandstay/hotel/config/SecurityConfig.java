package com.grandstay.hotel.config;

import com.grandstay.hotel.model.User;
import com.grandstay.hotel.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> {
//            throw new UsernameNotFoundException("Not used with JWT");
//        };
//    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors(cors -> {})
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                    .anyRequest().permitAll())
                // .authorizeHttpRequests(auth -> auth
                //         .requestMatchers("/rest/auth/**").permitAll()
                //         .requestMatchers("/rest/reservation/**")
                //         .hasAnyRole("ADMIN", "CUSTOMER", "FRONT_DESK")

                //         .requestMatchers("/rest/admin/**", "/rest/Admin/**")
                //         .hasRole("ADMIN")

                //         .requestMatchers("/rest/customer/**")
                //         .hasAnyRole("CUSTOMER","ADMIN")

                //         .requestMatchers("/rest/frontdesk/**")
                //         .hasAnyRole("FRONT_DESK","ADMIN")

                //         .requestMatchers("/rest/room/**")
                //         .hasAnyRole("ADMIN","CUSTOMER","FRONT_DESK")

                //         .requestMatchers("/rest/housekeeping/**").hasAnyRole("ADMIN","FRONT_DESK","HOUSEKEEPING")
                //         .requestMatchers("/rest/billing/**").hasAnyRole("ADMIN","FRONT_DESK")
                //         .anyRequest().permitAll()
                // )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
