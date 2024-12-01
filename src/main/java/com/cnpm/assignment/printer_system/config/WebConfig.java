package com.cnpm.assignment.printer_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import com.cnpm.assignment.printer_system.filter.JwtTokenFilter;
import com.cnpm.assignment.printer_system.security.UserSecurityService;

@SuppressWarnings("deprecation")
@EnableWebSecurity
@Configuration
public class WebConfig extends WebSecurityConfigurerAdapter {
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return new UserSecurityService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers((header) -> header.frameOptions().disable())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeRequests(request -> request
                        .antMatchers(HttpMethod.POST, "/account/login**")
                        .permitAll()
                        .antMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs",
                                "/API license URL", "/cloudinary/all-file**")
                        .permitAll()
                        .antMatchers(HttpMethod.POST, "/printer/print**", "/student/document**", "/student/printer**",
                                "/student/pay", "/student/question**")
                        .hasRole("STUDENT")
                        .antMatchers(HttpMethod.POST, "/spso/printer**", "/spso/answer**")
                        .hasRole("SPSO")
                        .antMatchers(HttpMethod.GET, "/printer/package-print**", "/student/document**", "/student/page",
                                "/student/history-payment**", "/student/bills**")
                        .hasRole("STUDENT")
                        .antMatchers(HttpMethod.DELETE, "/student/document**")
                        .hasRole("STUDENT")
                        .antMatchers(HttpMethod.GET, "/spso/students**", "/spso/history-print**",
                                "/spso/history-q-and-a**")
                        .hasRole("SPSO")
                        .antMatchers(HttpMethod.PUT, "/spso/change-active**", "/spso/printer**")
                        .hasRole("SPSO")
                        .antMatchers(HttpMethod.POST, "/account/logout")
                        .authenticated()
                        .antMatchers(HttpMethod.PUT, "/account/information**")
                        .authenticated()
                        .antMatchers(HttpMethod.GET, "/account/information", "/account/detail-q-and-a**")
                        .authenticated()
                        .anyRequest().denyAll())
                .addFilterBefore(jwtTokenFilter(), LogoutFilter.class)
                .logout(logout -> logout.logoutUrl("/account/logout")
                        .deleteCookies("JSESSIONID"));
    }
}