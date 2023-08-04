package com.usermanagement.infrastructure.configuration.security;

import com.usermanagement.feature.constants.EndpointPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.BeanIds.AUTHENTICATION_MANAGER;

/**
 * @author Timur Berezhnoi
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsServiceImpl;

    private final RESTAuthenticationEntryPoint authenticationEntryPoint;

    private final RESTAuthenticationFailureHandler authenticationFailureHandler;

    private final RESTAuthenticationSuccessHandler authenticationSuccessHandler;

    private final RESTLogoutSuccessHandler restLogoutSuccessHandler;

    @Autowired
    public WebSecurityConfiguration(UserDetailsService userDetailsServiceImpl,
                                    RESTAuthenticationEntryPoint authenticationEntryPoint,
                                    RESTAuthenticationFailureHandler authenticationFailureHandler,
                                    RESTAuthenticationSuccessHandler authenticationSuccessHandler,
                                    RESTLogoutSuccessHandler restLogoutSuccessHandler) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.restLogoutSuccessHandler = restLogoutSuccessHandler;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Set "remember me" expiration date to a week.
        final int rememberMeExpiration = 86400 * 7;

        http.csrf().disable()
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .and()
                .formLogin()
                    .loginPage(EndpointPath.SIGN_IN)
                    .usernameParameter("email")
                    .successHandler(authenticationSuccessHandler)
                    .failureHandler(authenticationFailureHandler)
                    .and()
                .logout()
                    .logoutUrl(EndpointPath.SIGN_OUT)
                    .logoutSuccessHandler(restLogoutSuccessHandler)
                    .and()
                .authorizeRequests()
                        .antMatchers(GET, EndpointPath.USERS).authenticated()
                        .antMatchers(POST, EndpointPath.USER).authenticated()
                        .antMatchers(DELETE, EndpointPath.USER + "/**/**").authenticated()
                        .anyRequest()
                        .permitAll()
                        .and()
                .rememberMe()
                    .key("rem-me-key")
                    .rememberMeParameter("rememberMe")
                    .rememberMeCookieName("remember_me_token")
                    .tokenValiditySeconds(rememberMeExpiration)
                    .alwaysRemember(true);
    }

    @Override
    @Bean(name = AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(11);
    }
}
