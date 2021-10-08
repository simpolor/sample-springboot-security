package io.simpolor.multiconfig.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Order(1)
    @Configuration
    @RequiredArgsConstructor
    public static class AdminSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/admin/**")
                .csrf()
                    .ignoringAntMatchers("/admin/login")

                    // URL에 따른 권한 체크
                .and()
                    .authorizeRequests()
                    .antMatchers("/admin", "/admin/login").permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()

                // 로그인 설정
                .and()
                    .formLogin()
                    .loginPage("/admin/login")
                    .loginProcessingUrl("/admin/login")
                    .defaultSuccessUrl("/admin")
                    .usernameParameter("email")
                    .passwordParameter("password")

                // 로그아웃 설정
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout"))
                    .logoutSuccessUrl("/admin")

                // 에러 페이지 설정
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/access-denied-page");
        }

        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                .withUser("admin@gmail.com")
                .password("{noop}1234").roles("ADMIN");
        }
    }

    @Order(2)
    @Configuration
    @RequiredArgsConstructor
    public static class UserSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .csrf()
                    .ignoringAntMatchers("/h2-console/**")
                // URL에 따른 권한 체크
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/index", "/welcome").permitAll()
                    .antMatchers("/error", "/error/**", "/test/**").permitAll()
                    .antMatchers("/student/list").permitAll()
                    .antMatchers("/student/detail").authenticated()
                    .antMatchers("/student/register", "/student/modify/**", "/student/delete/**").hasRole("USER")
                    .anyRequest().authenticated()

                // 로그인 설정
                .and()
                    .formLogin()
                    .successForwardUrl("/welcome")

                // 로그아웃 설정
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/welcome")

                // 에러 페이지 설정
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/access-denied-page");
        }

        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .withUser("user@gmail.com")
                    .password("{noop}1234").roles("USER");
        }
    }

}
