package io.simpolor.high.model;

import io.simpolor.high.repository.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class UserDto {

	private long seq;
	private String email;
	private String password;
	private String name;
	private List<String> authorities;

	public User toEntity(){

		User user = new User();
		user.setSeq(this.seq);
		user.setEmail(this.email);
		user.setPassword(this.password);
		user.setName(this.name);
		user.setAuthorities(authorities);

		return user;
	}

	public static UserDto of(User user){

		UserDto userDto = new UserDto();
		userDto.setSeq(user.getSeq());
		userDto.setEmail(user.getUsername());
		userDto.setPassword(user.getPassword());
		userDto.setName(user.getName());

		if(!CollectionUtils.isEmpty(user.getAuthorities())){
			userDto.setAuthorities(
					user.getAuthorities().stream()
							.map(GrantedAuthority::getAuthority)
							.collect(Collectors.toList()));
		}

		return userDto;
	}

	public static List<UserDto> of(List<User> users){

		return users.stream()
				.map(UserDto::of)
				.collect(Collectors.toList());
	}
}
