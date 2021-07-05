package io.simpolor.custom.config;

import io.simpolor.custom.security.*;
import io.simpolor.custom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationProvider customAuthenticationProvider; // 로그인에 대한 처리
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler; // 로그인 성공에 대한 처리
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler; // 로그인실패에 대한 처리
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler; // 로그아웃 처리
    private final CustomAccessDeniedHandler customAccessDeniedHandler; // 접근 권한에 대한 처리
    private final CustomSecurityInterceptor customSecurityInterceptor; // 시큐리티 작업에 대한 인터셉터
    private final CustomPersistentTokenRepository customPersistentTokenRepository; // 토큰 처리

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
                    //.antMatchers("/error", "/error/**", "/h2-console/**").permitAll()
                    //.antMatchers("/user/join", "/user/login").permitAll()
                    .anyRequest().authenticated()

                // 로그인 설정
                .and()
                    .formLogin()
                    .loginPage("/user/login") // 로그인 페이지 ( 컨트롤러를 매핑하지 않으면 기본 제공 로그인 페이지 호출 )
                    .loginProcessingUrl("/user/login") // 로그인 프로세스 처리할 URL
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .successHandler(customAuthenticationSuccessHandler) // 로그인 성공시 이동할 핸들러
                    .failureHandler(customAuthenticationFailureHandler) // 로그인 성공시 이동할 핸들러
                    //.and()

                // 에러 페이지 설정
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/access-denied-page")

                // 비밀번호 자동저장 설정
                .and()
                    .rememberMe()
                    .rememberMeParameter("remember-me")
                    .key("remember-me")
                    .tokenRepository(customPersistentTokenRepository)
                    .tokenValiditySeconds(86400) // 1일 = 86400초

                // 로그아웃 설정
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                    .logoutSuccessHandler(customLogoutSuccessHandler) // 로그아웃 성공시 이동할 URL

                // 예외처리 설정
                .and()
                    .exceptionHandling()
                    .accessDeniedHandler(customAccessDeniedHandler)

                // .and()
                // .addFilterBefore(customFilterSecurity, UsernamePasswordAuthenticationFilter.class);

                // 필터 설정 (접근할 URL 및 해당 URL에 따른 권한을 확인)
                .and()
                    .addFilterBefore(customSecurityInterceptor, FilterSecurityInterceptor.class);

    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 인증 처리
        auth.authenticationProvider(customAuthenticationProvider);
    }
}
