package io.simpolor.oauthresource.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuthToken {

    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private Long expiresIn;
    private String scope;
}
