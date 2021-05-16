package com.shrikant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class UserDetailsManagerConfig extends WebSecurityConfigurerAdapter {

   @Bean
    public UserDetailsService uds(DataSource dataSource){
        var udm = new JdbcUserDetailsManager(dataSource);
        udm.setUsersByUsernameQuery("select email,password,enabled from user where email = ?");
        udm.setAuthoritiesByUsernameQuery("select email,authority from user where email = ?");
        return udm;
    }

    @Bean
    public PasswordEncoder pe(){
       return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll()
                .and()
                .csrf().disable();
    }
}
