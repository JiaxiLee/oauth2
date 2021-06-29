package com.li.oauth.auth.config;

import com.li.oauth.auth.image.ImageValidateSecurityConfig;
import com.li.oauth.auth.properties.SecurityProperties;
import com.li.oauth.auth.security.InUserDetailService;
import com.li.oauth.auth.sms.SmsCodeAuthenticationProvider;
import com.li.oauth.auth.sms.SmsCodeAuthenticationSecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * description: WebSecurityConfig
 * date: 2020/7/29
 * author: lijiaxi-os
 */
@Configuration
@Slf4j
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private InUserDetailService userDetailService;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private SmsCodeAuthenticationProvider smsCodeAuthenticationProvider;

    @Autowired
    private ImageValidateSecurityConfig imageValidateSecurityConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("configure............");
        http.apply(smsCodeAuthenticationSecurityConfig);
        http.apply(imageValidateSecurityConfig);

        http
                //.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(securityProperties.getIgnore().getUrls()).permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(NoOpPasswordEncoder.getInstance());
        auth.authenticationProvider(smsCodeAuthenticationProvider);
        System.out.println("auth");
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
