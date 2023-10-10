package com.example.clmp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.clmp.filter.JwtFilter;
import com.example.clmp.service.CustomUserDetailsService;


//enable h2 database console
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    //DESCRIPTION: Declaring URL patterns that are allowed w/o authentication
    private static final String[] AUTH_WHITE_LIST = {
        "/authenticate", 
        "/swagger-ui/**", 
        "/v3/api-docs/**", 
        "/swagger-resources/**", 
        "/webjars/**"
    };

    @Override
    //DESCRIPTION: Configuring the authmangerBuilder to use (custom)userDetailsService for authentication
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    //DESCRIPTION: Configuring security for HTTP requests
    protected void configure(HttpSecurity http) throws Exception {
        //Allowing authenticate and swagger endpoints to be hit without authentication.
        http.cors().and().csrf().disable().headers().frameOptions().deny().and()
            .authorizeRequests().antMatchers(AUTH_WHITE_LIST).permitAll() 
            //Requiring authentication for any other request.
            .anyRequest().authenticated()
            .and().exceptionHandling().and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //Adding custom jwtFilter before the default spring security authentication filter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

