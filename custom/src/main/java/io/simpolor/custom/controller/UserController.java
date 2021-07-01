package io.simpolor.custom.controller;

import io.simpolor.custom.model.UserDto;
import io.simpolor.custom.repository.entity.User;
import io.simpolor.custom.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@RequestMapping(value="/join", method=RequestMethod.GET)
	public ModelAndView joinForm(ModelAndView mav) {

		mav.setViewName("user_join");
		return mav;
	}

	@RequestMapping(value="/join", method=RequestMethod.POST)
	public ModelAndView join(ModelAndView mav, UserDto userDto) {

		userService.insert(userDto.toEntity());

		mav.setViewName("redirect:/student/list");
		return mav;
	}

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView loginForm(ModelAndView mav) {

		mav.setViewName("user_login");
		return mav;
	}

	@GetMapping(value="/detail/{seq}")
	public ModelAndView detail(ModelAndView mav, @PathVariable long seq) {

		User user = userService.get(seq);

		// Collection<? extends GrantedAuthority> grantedAuthorityList = member.getAuthorities();
		// Iterator<? extends GrantedAuthority> it = grantedAuthorityList.iterator();

		// Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) member.getAuthorities();
		// while (it.hasNext()) {
		// 	GrantedAuthority authority = it.next();
		//	log.info("authority.getAuthority() : {}", authority.getAuthority());
		// assertThat(authorities, hasItem(new SimpleGrantedAuthority(authority.getAuthority())));
		// }

		mav.addObject("userDto", UserDto.of(user));
		mav.setViewName("user_detail");
		return mav;
	}

}
