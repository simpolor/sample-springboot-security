package io.simpolor.resource.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class OAuthController {



    @GetMapping("/oauth2/callback")
    public String callback(@RequestParam String code) throws JsonProcessingException {

        System.out.println("code = " + code);

        OauthTokenDto token = getToken(code);
        System.out.println("getToken() = " + token);

        return token.toString();
    }

    private OauthTokenDto getToken(String code) throws JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        String credentials = "testClientId:testSecret";
        String encodedCredentials = new String(Base64.encode(credentials.getBytes()));

        System.out.println("encodedCredentials : "+encodedCredentials);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Basic " + encodedCredentials);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("grant_type", "authorization_code");

        System.out.println("code : "+code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:9090/oauth/token", request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("response.getBody() = " + response.getBody());
            OauthTokenDto oauthTokenDto = objectMapper.readValue(response.getBody(), OauthTokenDto.class);
            return oauthTokenDto;
        }
        return null;
    }

    @Getter
    @ToString
    static class OauthTokenDto {
        private String access_token;
        private String token_type;
        private String refresh_token;
        private long expires_in;
        private String scope;
    }
}
