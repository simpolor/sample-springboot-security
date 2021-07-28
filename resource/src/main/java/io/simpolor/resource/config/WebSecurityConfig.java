package io.simpolor.resource.config;

import io.simpolor.resource.security.PasswordEncoding;
import io.simpolor.resource.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final PasswordEncoding passwordEncoding;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
           .csrf().disable()
            .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                    .antMatchers("/", "/index", "/welcome").permitAll()
                    .antMatchers("/user/**").permitAll()
                    .antMatchers("/oauth/**", "/oauth2/callback", "/h2-console/*").permitAll()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("auth : "+auth);
        auth.userDetailsService(userService).passwordEncoder(passwordEncoding);
    }

    /*protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("ADMIN", "USER");
        auth.inMemoryAuthentication().withUser("user").password("{noop}user").roles("USER");
        // {noop} : NoOpPasswordEncoder
    }*/
}
