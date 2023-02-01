package io.simpolor.custom.service;

import io.simpolor.custom.repository.TokenRepository;
import io.simpolor.custom.repository.entity.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

	private final TokenRepository tokenRepository;

	public Token get(String series){
		Optional<Token> optionalToken = tokenRepository.findById(series);
		if(!optionalToken.isPresent()){
			return null;
		}

		return optionalToken.get();
	}

	public Token getByUsername(String username){
		Optional<Token> optionalToken = tokenRepository.findByUsername(username);
		if(!optionalToken.isPresent()){
			return null;
		}

		return optionalToken.get();
	}

	public void create(Token token){
		tokenRepository.save(token);
	}

	public void update(Token token) {
		tokenRepository.save(token);
	}

	public void delete(String series) {
		tokenRepository.deleteById(series);
	}

}
