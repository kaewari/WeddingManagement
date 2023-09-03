/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.configs;

import com.qltc.filters.CustomAccessDeniedHandler;
import com.qltc.filters.JwtAuthenticationTokenFilter;
import com.qltc.filters.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author sonho
 */
@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.qltc.controller",
    "com.qltc.repository",
    "com.qltc.service",
    "com.qltc.components"})
@Order(1)
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
        JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
        jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
        return jwtAuthenticationTokenFilter;
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/api/**");
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/login/").permitAll()
                .antMatchers(HttpMethod.GET, "/api/users/").hasAuthority("VIEW_LIST_USER")
                .antMatchers(HttpMethod.GET, "/api/users/id/**").hasAuthority("VIEW_USER_BY_ID")
                .antMatchers(HttpMethod.GET, "/api/users/name/**").hasAuthority("VIEW_USER_BY_NAME")
                .antMatchers(HttpMethod.POST, "/api/user/").hasAuthority("ADD_USER")
                .antMatchers(HttpMethod.POST, "/api/users/update/**").hasAuthority("UPDATE_USER")
                .antMatchers(HttpMethod.DELETE, "/api/users/delete/**").hasAuthority("DELETE_USER")
                .antMatchers(HttpMethod.GET, "/api/employees/").hasAuthority("VIEW_LIST_EMPLOYEE")
                .antMatchers(HttpMethod.GET, "/api/employees/id/**").hasAuthority("VIEW_USER_BY_ID")
                .antMatchers(HttpMethod.GET, "/api/employees/identity/**").hasAuthority("VIEW_EMPLOYEE_BY_IDENTITY_NUMBER")
                .antMatchers(HttpMethod.POST, "/api/employees/").hasAuthority("ADD_EMPLOYEE")
                .antMatchers(HttpMethod.POST, "/api/employees/update/**").hasAuthority("UPDATE_EMPLOYEE")
                .antMatchers(HttpMethod.DELETE, "/api/employees/delete/**").hasAuthority("DELETE_EMPLOYEE")
                .antMatchers(HttpMethod.GET, "/api/test/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/current-user/").authenticated()
                .antMatchers(HttpMethod.GET, "/api/**/comments/").permitAll();
        http.antMatcher("/api/**").httpBasic().authenticationEntryPoint(restServicesEntryPoint()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").access("hasRole('ADMIN') or hasRole('USER')")
                .antMatchers(HttpMethod.POST, "/api/**").access("hasRole('ADMIN') or hasRole('USER')")
                .antMatchers(HttpMethod.DELETE, "/api/**").access("hasRole('ADMIN') or hasRole('USER')").and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
    }
}
