package io.simpolor.custom.repository.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	private String email;
	private String password;
	private String name;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name="user_role",
			joinColumns = @JoinColumn(name= "userId", referencedColumnName = "userId"))
	private List<String> authorities;


	@Override
	public String getUsername(){
		return email;
	}

	@Override
	public String getPassword(){
		return password;
	}

	public String getName(){
		return name;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		for(String authority : authorities){
			grantedAuthorities.add(new SimpleGrantedAuthority(authority));
		}
		return grantedAuthorities;
	}

	@Override
	public boolean isAccountNonExpired(){
		return true;
	}

	@Override
	public boolean isAccountNonLocked(){
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired(){
		return true;
	}

	@Override
	public boolean isEnabled(){
		return true;
	}



}
