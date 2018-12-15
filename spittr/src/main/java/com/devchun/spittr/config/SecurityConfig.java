package com.devchun.spittr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import com.devchun.spittr.data.SpitterRepository;
import com.devchun.spittr.security.SpitterUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
  
  @Autowired
  @Qualifier("mybatisSpitterRepository")
  SpitterRepository spitterRepository;
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // http.csrf().disable();
    http
    .formLogin().loginPage("/login").and()
    .rememberMe().tokenValiditySeconds(2419200).key("spittrKey").and()
    .logout().logoutRequestMatcher(new RegexRequestMatcher("/logout", null)).logoutSuccessUrl("/").and()
    .authorizeRequests()
    .antMatchers("/spitters").access("hasRole('ROLE_SPITTER')")
    .antMatchers(HttpMethod.POST, "/spittles").access("hasRole('ROLE_SPITTER')")
    .anyRequest().permitAll();
  }
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    /*
    auth.jdbcAuthentication().dataSource(dataSourceSpied)
      .usersByUsernameQuery(
          "SELECT USERNAME, PASSWORD, true FROM SPITTER WHERE USERNAME=?"
      ).authoritiesByUsernameQuery(
          "SELECT USERNAME, 'ROLE_USER' FROM SPITTER WHERE USERNAME=?"
      ).passwordEncoder(new StandardPasswordEncoder("chundol42"));
    */
    
    auth.userDetailsService(new SpitterUserService(spitterRepository))
      .passwordEncoder(new StandardPasswordEncoder("chundol42"));
  }
}
