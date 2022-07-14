package com.tabwu.SAP.user.config;

import com.tabwu.SAP.common.config.RSAKeyProperty;
import com.tabwu.SAP.user.filter.JwtLoginTokenfilter;
import com.tabwu.SAP.user.filter.JwtVerifyFilter;
import com.tabwu.SAP.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/14 14:28
 * @DESCRIPTION:
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(RSAKeyProperty.class)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RSAKeyProperty pro;

    @Autowired
    private IUserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
//                .authorizeRequests().antMatchers("/login").permitAll()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(new JwtLoginTokenfilter(authenticationManager(),pro))
                .addFilter(new JwtVerifyFilter(authenticationManager(),pro))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
