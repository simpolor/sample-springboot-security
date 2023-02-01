package io.simpolor.custom.model;

import io.simpolor.custom.repository.entity.User;
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
			return this.toEntity(null);
		}

		public User toEntity(Long userId){
			User user = new User();
			user.setUserId(userId);
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

		public static List<UserResponse> of(List<User> users){

			return users.stream()
					.map(UserResponse::of)
					.collect(Collectors.toList());
		}
	}
}
