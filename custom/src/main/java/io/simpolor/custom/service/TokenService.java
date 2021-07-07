package io.simpolor.custom.service;

import io.simpolor.custom.repository.TokenRepository;
import io.simpolor.custom.repository.entity.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

	private final TokenRepository tokenRepository;

	public PersistentRememberMeToken getTokenForSeries(String series) {

		Token token = tokenRepository.findById(series).get();
		
		return new PersistentRememberMeToken(token.getUsername(), token.getSeries(), token.getToken(), token.getLastUsed());
	}

	public Token selectToken(String username) {

		Optional<Token> optionalToken = tokenRepository.findByUsername(username);
		if(optionalToken.isPresent()){
			return optionalToken.get();
		}

		return null;
	}

	public void createNewToken(String username, String series, String tokenValue, Date lastUsed) {
		Token token = new Token();
		token.setUsername(username);
		token.setSeries(series);
		token.setToken(tokenValue);
		token.setLastUsed(lastUsed);
		
		tokenRepository.save(token);
	}

	public void updateToken(String series, String tokenValue, Date lastUsed) {
		Token token = new Token();
		token.setSeries(series);
		token.setToken(tokenValue);
		token.setLastUsed(lastUsed);
		
		tokenRepository.save(token);
	}

	public void removeUserTokens(String username) {
		tokenRepository.deleteById(username);
	}

}
