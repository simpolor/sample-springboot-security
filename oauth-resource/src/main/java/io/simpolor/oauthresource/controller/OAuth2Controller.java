package io.simpolor.oauthresource.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.simpolor.oauthresource.model.OAuthToken;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth2")
public class OAuth2Controller {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @GetMapping("/callback")
    public OAuthToken callback(@RequestParam String code) throws JsonProcessingException {

        String credentials = "testClientId:testSecret";
        String encoderCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Basic "+encoderCredentials);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", "/oauth2/callback");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:9090/oauth/token", request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("response.getBody() = " + response.getBody());
            OAuthToken oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
            return oAuthToken;
        }

        return null;
    }
}
