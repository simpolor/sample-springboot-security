package io.simpolor.oauthresource.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 로그인시 password에 대한 인코딩을 안하도록 빈 설정
    @Bean
    public PasswordEncoder noOpPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return objectMapper;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // Cross site request forgery (사이트간 요청 위조 [사용 권장] )
            .csrf()
                .ignoringAntMatchers("/h2-console/**")

            .and()
                .headers()
                    .frameOptions().disable()

            // URL에 따른 권한 체크
            .and()
                .authorizeRequests()
                .antMatchers("/", "/index", "/welcome").permitAll()
                .antMatchers("/error", "/error/**", "/h2-console/**").permitAll()
                // .antMatchers("/user/**").permitAll()
                //.antMatchers("/oauth/**").permitAll()
                .antMatchers("/welcome").permitAll()
                .antMatchers("/oauth/**", "/oauth2/callback", "/h2-console/*").permitAll()
                .anyRequest().authenticated()
            .and().formLogin()
            .and().httpBasic();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // auth.userDetailsService(userService).passwordEncoder(passwordEncoding);
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("1234")
                .roles("USER");
    }

}
