package io.simpolor.custom.security;

import io.simpolor.custom.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider{

	private String rolePrefix = "ROLE_";

	private final PasswordEncoding passwordEncoding;
	private final UserService userService;

	/**
	 * 인증
	 * @param authentication
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		log.info("CustomAuthenticationProvider.authenticate");
		
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		log.info("> authentication.getName() : {}", authentication.getName());
		log.info("> authentication.getCredentials() : {}", authentication.getCredentials().toString());

		try {
			UserDetails userDetails = userService.loadUserByUsername(username);
			
			log.info("> userDetails.getUsername() : {}", userDetails.getUsername());
			log.info("> userDetails.getPassword() : {}", userDetails.getPassword());
			log.info("> userDetails.getAuthorities() : {}", userDetails.getAuthorities());
			
			log.info("> password 비교 : {}", passwordEncoding.matches(password, userDetails.getPassword()));
			
			if(!passwordEncoding.matches(password, userDetails.getPassword())) {
				throw new BadCredentialsException("The password does not match.");
			}
			return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

		} catch(UsernameNotFoundException e) {
			throw new UsernameNotFoundException("This username does not exist.");
		} catch(BadCredentialsException e) { 
			throw new BadCredentialsException(e.getMessage()); 
		} catch(Exception e) { 
			throw new RuntimeException(e.getMessage()); 
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
}
