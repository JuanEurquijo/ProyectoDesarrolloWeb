package org.web.project.config;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration
@ConditionalOnProperty(value = "keycloak.enabled", matchIfMissing = true)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
   
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(keycloakAuthenticationProvider());
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(buildSessionRegistry());
    }

    @Bean
    protected SessionRegistry buildSessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/route/**").hasRole("ADMIN")
                 .antMatchers(HttpMethod.POST, "/api/route/**").hasRole("ADMIN")
                 .antMatchers(HttpMethod.PUT, "/api/route/**").hasRole("ADMIN")
                 .antMatchers(HttpMethod.DELETE, "/api/route/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/driver/**").hasRole("COORDINATOR")
                .antMatchers(HttpMethod.POST, "/api/driver/**").hasRole("COORDINATOR")
                .antMatchers(HttpMethod.DELETE, "/api/driver/**").hasRole("COORDINATOR")
                .antMatchers(HttpMethod.PUT, "/api/driver/**").hasRole("COORDINATOR")
                .antMatchers(HttpMethod.GET, "/api/bus/**").hasRole("COORDINATOR")
                .antMatchers(HttpMethod.POST, "/api/bus/**").hasRole("COORDINATOR")
                .antMatchers(HttpMethod.DELETE, "/api/bus/delete/{id}").hasRole("COORDINATOR")
                .antMatchers(HttpMethod.PUT, "/api/bus/**").hasRole("COORDINATOR")
                .antMatchers(HttpMethod.GET, "/api/route/list").hasRole("COORDINATOR")
                .antMatchers(HttpMethod.GET, "/api/assignment/{id}/assignedDrivers").hasRole("COORDINATOR")
                .antMatchers(HttpMethod.GET, "/api/assignment/****").hasRole("COORDINATOR")
                .antMatchers(HttpMethod.DELETE, "/api/assignment/****").hasRole("COORDINATOR")
                .anyRequest().denyAll();
        http.csrf().disable();
        http.cors();
        http.headers().frameOptions().sameOrigin(); 
    }

}