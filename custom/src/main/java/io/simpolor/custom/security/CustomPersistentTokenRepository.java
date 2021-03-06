package io.simpolor.custom.security;

import io.simpolor.custom.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * 자동 로그일을 처리하기 위한 저장소 ( DB )
 */
@Component
@RequiredArgsConstructor
public class CustomPersistentTokenRepository implements PersistentTokenRepository{

	private final TokenService tokenService;
	
	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		return tokenService.getTokenForSeries(seriesId);
	}
	
	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		tokenService.createNewToken(token.getUsername(), token.getSeries(), token.getTokenValue(), token.getDate());
	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {

		tokenService.updateToken(series, tokenValue, lastUsed);
	}

	@Override
	public void removeUserTokens(String username) {

		if(Objects.nonNull(tokenService.selectToken(username))){
			tokenService.removeUserTokens(username);
		}
	}

}
