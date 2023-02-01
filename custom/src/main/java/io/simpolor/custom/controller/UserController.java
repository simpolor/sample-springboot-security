package io.simpolor.custom.controller;

import io.simpolor.custom.model.UserDto;
import io.simpolor.custom.repository.entity.Role;
import io.simpolor.custom.repository.entity.User;
import io.simpolor.custom.service.RoleService;
import io.simpolor.custom.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final RoleService roleService;

	@GetMapping("/join")
	public ModelAndView joinForm(ModelAndView mav) {

		List<Role> roles = roleService.getAll();

		mav.addObject("roles", roles);
		mav.setViewName("user_join");
		return mav;
	}

	@PostMapping("/join")
	public ModelAndView join(ModelAndView mav,
							 UserDto.UserRequest request) {

		userService.insert(request.toEntity());

		mav.setViewName("redirect:/student/list");
		return mav;
	}

	@GetMapping("/login")
	public ModelAndView loginForm(ModelAndView mav) {



		mav.setViewName("user_login");
		return mav;
	}

	@GetMapping("/detail/{userId}")
	public ModelAndView detail(ModelAndView mav,
							   @PathVariable Long userId) {

		User user = userService.get(userId);

		// Collection<? extends GrantedAuthority> grantedAuthorityList = member.getAuthorities();
		// Iterator<? extends GrantedAuthority> it = grantedAuthorityList.iterator();

		// Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) member.getAuthorities();
		// while (it.hasNext()) {
		// 	GrantedAuthority authority = it.next();
		//	log.info("authority.getAuthority() : {}", authority.getAuthority());
		// assertThat(authorities, hasItem(new SimpleGrantedAuthority(authority.getAuthority())));
		// }

		mav.addObject("user", UserDto.UserResponse.of(user));
		mav.setViewName("user_detail");
		return mav;
	}

}
