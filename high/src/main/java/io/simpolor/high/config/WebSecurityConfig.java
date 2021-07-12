package io.simpolor.high.config;

import io.simpolor.high.security.PasswordEncoding;
import io.simpolor.high.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final PasswordEncoding passwordEncoding;


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
                    .antMatchers("/user/**").permitAll()
                    .antMatchers("/student/list").permitAll()
                    .antMatchers("/student/detail/**").hasAuthority("USER")
                    .antMatchers("/student/register", "/student/modify/**", "/student/delete/**").hasAuthority("ADMIN")
                    .anyRequest().authenticated()
                    .accessDecisionManager(accessDecisionManager())

                // 로그인 설정
                .and()
                    .formLogin()
                    .loginPage("/user/login") // 로그인 페이지 ( 컨트롤러를 매핑하지 않으면 기본 제공 로그인 페이지 호출 )
                    .loginProcessingUrl("/user/login") // 로그인 프로세스 처리할 URL
                    .failureUrl("/user/login?error=true") // 로그인 실패시 이동할 URL
                    .defaultSuccessUrl("/student/list")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    //.and()

                // 에러 페이지 설정
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/access-denied-page")

                // 비밀번호 자동저장 설정
                .and()
                    .rememberMe()
                    .key("remember-me")
                    .rememberMeParameter("remember-me")
                    .rememberMeCookieName("remember-me")
                    .tokenValiditySeconds(86400) // 1일 = 86400초

                // 로그아웃 설정
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                    .logoutSuccessUrl("/student/list");
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoding);
    }

    public AccessDecisionManager accessDecisionManager() {

        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ADMIN > USER");

        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy( roleHierarchy );

        WebExpressionVoter webExpressVoter = new WebExpressionVoter();
        webExpressVoter.setExpressionHandler( handler );

        List<AccessDecisionVoter<? extends Object>> voters = Arrays.asList( webExpressVoter );
        return new AffirmativeBased( voters );
    }

    /*@Bean
    public RoleHierarchy roleHierarchy() {

        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ADMIN > USER");

        return roleHierarchy;
    }

    @Bean
    public AccessDecisionVoter<? extends  Object> roleVoter(){

        RoleHierarchyVoter roleHierarchyVoter = new RoleHierarchyVoter(roleHierarchy());

        return roleHierarchyVoter;
    }*/

}
