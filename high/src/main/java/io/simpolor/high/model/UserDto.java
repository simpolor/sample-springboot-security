package io.simpolor.high.model;

import io.simpolor.high.repository.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class UserDto {

	@Setter
	@Getter
	public static class UserRequest {

		private String email;
		private String password;
		private String name;
		private List<String> authorities;

		public User toEntity(){
			User user = new User();
			user.setEmail(this.email);
			user.setPassword(this.password);
			user.setName(this.name);
			user.setAuthorities(authorities);

			return user;
		}
	}

	@Setter
	@Getter
	public static class UserResponse {

		private Long id;
		private String email;
		private String password;
		private String name;
		private List<String> authorities;

		public static UserResponse of(User user){

			UserResponse response = new UserResponse();
			response.setId(user.getUserId());
			response.setEmail(user.getUsername());
			response.setPassword(user.getPassword());
			response.setName(user.getName());

			if(!CollectionUtils.isEmpty(user.getAuthorities())){
				response.setAuthorities(
						user.getAuthorities().stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()));
			}

			return response;
		}
	}
}
