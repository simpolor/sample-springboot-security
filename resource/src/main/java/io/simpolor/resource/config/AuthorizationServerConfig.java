package io.simpolor.resource.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * 인증서버 활성화
 *  ( 2019년 11월 24일 Spring Security 블로그에서 Authorization Server의 지원을 중지한다는 글을 발표 )
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    AuthenticationManager authenticationManager;
    public AuthorizationServerConfig(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 토큰유효성(/token/check_token) 접근을 위해 설정
        security.checkTokenAccess("permitAll()");
    }

    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("testClientId")
                .secret("{noop}testSecret")
                //.redirectUris("http://localhost:9090/oauth2/callback")
                .redirectUris("/oauth2/callback")
                .autoApprove(true)
                .authorizedGrantTypes("authorization_code", "password", "refresh_token")
                .scopes("read", "write");

        /**
         * redirectUris : 인증 완료 휴 이동할 웹 페이지 주소로 code를 전달
         * authorizedGrantTypes : 인증 방식 ( authorization_code, implict, password credential, client credential )
         * scopes : accessToken으로 접근할 수 있는 리소스 범위
         * accessTokenValiditySeconds : 유효시간
         *
         * // http://localhost:9090/oauth/authorize?client_id=testClientId&redirect_uri=http://localhost:9090/oauth2/callback&response_type=code&scope=read
         * // http://localhost:9090/oauth/authorize?client_id=testClientId&response_type=code
         */
    }



}
