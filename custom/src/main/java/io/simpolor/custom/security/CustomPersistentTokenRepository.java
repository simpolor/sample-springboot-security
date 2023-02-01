package io.simpolor.custom.security;

import io.simpolor.custom.repository.entity.Token;
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
	public PersistentRememberMeToken getTokenForSeries(String series) {
		Token token = tokenService.get(series);
		if(Objects.isNull(token)){
			return null;
		}

		return new PersistentRememberMeToken(token.getUsername(), token.getSeries(), token.getToken(), token.getLastUsed());
	}
	
	@Override
	public void createNewToken(PersistentRememberMeToken persistentRememberMeToken) {

		Token token = new Token();
		token.setSeries(persistentRememberMeToken.getSeries());
		token.setUsername(persistentRememberMeToken.getUsername());
		token.setToken(persistentRememberMeToken.getTokenValue());
		token.setLastUsed(persistentRememberMeToken.getDate());

		tokenService.create(token);
	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		Token token = tokenService.get(series);
		if(Objects.isNull(token)){
			return;
		}
		token.setToken(tokenValue);
		token.setLastUsed(lastUsed);

		tokenService.update(token);
	}

	@Override
	public void removeUserTokens(String username) {
		Token token = tokenService.getByUsername(username);
		if(Objects.isNull(token)){
			return;
		}

		tokenService.delete(token.getSeries());
	}

}
