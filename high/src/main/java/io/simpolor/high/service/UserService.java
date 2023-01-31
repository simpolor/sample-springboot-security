package io.simpolor.high.service;

import io.simpolor.high.repository.UserRepository;
import io.simpolor.high.repository.entity.User;
import io.simpolor.high.security.PasswordEncoding;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final PasswordEncoding passwordEncoding;
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Optional<User> optionalUser = userRepository.findByEmail(email);
		if(!optionalUser.isPresent()){
			throw new UsernameNotFoundException("Exist Not User");
		}

		return optionalUser.get();
	}

	public User get(Long userId) {

		Optional<User> optionalUser = userRepository.findById(userId);
		if(!optionalUser.isPresent()){
			throw new UsernameNotFoundException("Exist Not User");
		}

		return optionalUser.get();
	}

	public void insert(User user) {

		user.setPassword(passwordEncoding.encode(user.getPassword()));
		userRepository.save(user);
	}
}
