package com.base.mongo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.base.mongo.config.security.CustomUserDetailsService;
import com.base.mongo.config.security.JWTAuthEntryPoint;
import com.base.mongo.config.security.JWTAuthenticationFilter;

/**
 * securedEnabled: It enables the @Secured annotation using which you can protect your controller/service methods like so 
 * 					@Secured({"ROLE_USER", "ROLE_ADMIN"})
 * 					public User getAllUsers() {}
 * 
 * @author virginia
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {

	@Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JWTAuthEntryPoint unauthorizedHandler;

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	  @Override
	  protected void configure(HttpSecurity http) throws Exception {
		  http
		  .cors()
          .and()
		      .csrf()
		          .disable()
		      .exceptionHandling()
		          .authenticationEntryPoint(unauthorizedHandler)
		          .and()
		      .sessionManagement()
		          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		          .and()
		      .authorizeRequests()
		          .antMatchers("/api/v1/auth/**")
		              .permitAll()
		          .antMatchers("/api/v1/**")
		              .authenticated()
		          .anyRequest()
		              .authenticated();
		  
		// Add our custom JWT security filter
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

	  }


}
