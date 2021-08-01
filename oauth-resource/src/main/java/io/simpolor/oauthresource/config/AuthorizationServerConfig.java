package io.simpolor.oauthresource.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@Configuration
@EnableAuthorizationServer // 인증 서버 활성화
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {


    // https://gaemi606.tistory.com/entry/Spring-Boot-Spring-Security-OAuth2-3-JDBC%EB%B0%A9%EC%8B%9D%EC%9C%BC%EB%A1%9C-%EB%B0%94%EA%BE%B8%EA%B8%B0
    /* OAuth2 서버가 작동하기 위한 EndPoint에 대한 정보 설정 */
    /*@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }*/

    /* 클라이언트 대한 정보를 설정하는 부분 */
    /* jdbc(DataBase)를 이용하는 방식 */
    /*@Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
    }*/

    /**
     *
     * @param clients
     * @throws Exception
     */
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("testClientId")
                .secret("testSecret")
                .redirectUris("/oauth2/callback")
                .authorizedGrantTypes("authorization_code")
                .scopes("read", "write")
                //.autoApprove(true)
                .accessTokenValiditySeconds(60 * 60 * 5)
                .refreshTokenValiditySeconds(60 * 60 * 24 * 120);

        /**
         * withClient : 클라이언트 아이디
         *
         * secret : 클라이언트 시크릿
         *
         * redirectUris : 인증 완료 후 인증 결과를 수신한 URL에 code 값을 전달
         * - redirectUris과 요청시 redirect_uri이 동일해야함
         * - 요청시 redirect_uri 파라미터에 값이 없을 경우 redirectUris 자동으로 인식
         *
         * authorizedGrantTypes : 인증 방식
         * - authorization_code
         *   : 가장 대중적인 방식으로 Service Provider가 제공하는 인증 화면에 로그인하면, 클라이언트 앱이 요청하는 리소스에 접근 요청을 승인하면
         *     지정한 redirect_uri로 code를 넘겨주는데, 해당  code로 acess_token을 얻음
         * - implict
         *   : authorization_code와 비슷하나, 인증 후 redirect_uri로 직접 access_token을 전달 받으므로, 전체적으로 간단해지나 보안성은 떨어짐
         * - password credential
         *   : Resource Owner가 직접 Client에 아이디와 패스워드를 입력하고 인증 서버로 해당 정보로 인증받아 access_token을 직접 얻어오는 방식
         *     아이디 패스워가 노출되므로 보안성이 떨이짐
         * - client credential
         *   : 정해진 인증 key(secret)로 요청하며, 일반적으로 서버간의 통신할 때 사용 됨
         *
         * scopes : 해당 클라이언트의 접근 범위 / accessToken으로 접근할 수 있는 리소스 범위
         * - read
         * - write
         *
         * autoApprove : OAuth Approval 화면 나오지 않게 처리
         * - 로그인 후 scopes 권한 선택화면 노출 여부
         *
         * accessTokenValiditySeconds :  access token 유효 기간 (초 단위)
         *
         * refreshTokenValiditySeconds : refresh token 유효 기간 (초 단위)
         *
         * 로그인 URL
         * http://localhost:9090/oauth/authorize?client_id=testClientId&redirect_uri=/oauth2/callback&response_type=code&scope=read
         *
         * 다음에 할꺼
         * https://daddyprogrammer.org/post/1287/spring-oauth2-authorizationserver-database/
         *
         * 읽어볼꺼
         * https://autumnly.tistory.com/64
         * https://lemontia.tistory.com/927
         * https://velog.io/@agugu95/OAuth%EC%84%9C%EB%B2%84-%EA%B5%AC%ED%98%84%EC%9D%84-%EC%9C%84%ED%95%9C-Spring-Security-%EC%95%8C%EC%95%84%EB%B3%B4%EA%B8%B0
         */
    }
}
