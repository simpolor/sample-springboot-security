package io.simpolor.basic.controller;

import io.simpolor.basic.model.StudentDto;
import io.simpolor.basic.repository.entity.Student;
import io.simpolor.basic.security.SecurityContext;
import io.simpolor.basic.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/student")
@RequiredArgsConstructor
public class StudentController {

	private final StudentService studentService;

	@GetMapping("/list")
	public ModelAndView list(ModelAndView mav) {

		// 시큐리티 테스트를 위한 구분
		if(SecurityContext.isAuthenticated()){
			UserDetails user = SecurityContext.getPrincipal();
			log.info("-- user.getUsername() : "+user.getUsername());
			log.info("-- user.getPassword() : "+user.getPassword());
			log.info("-- user.isAccountNonLocked() : "+user.isAccountNonLocked());
			log.info("-- user.isEnabled() : "+user.isEnabled());
			log.info("-- user.getAuthorities() : "+user.getAuthorities());
			log.info("-- user.isCredentialsNonExpired() : "+user.isCredentialsNonExpired());
			log.info("-- user.isAccountNonExpired() : "+user.isAccountNonExpired());
			log.info("-- user.isAccountNonExpired() : "+user.isAccountNonExpired());
		}

		List<Student> students = studentService.getAll();

		mav.addObject("studentList", StudentDto.StudentResponse.of(students));
		mav.setViewName("student_list");

		return mav;
	}

	@GetMapping("/detail/{studentId}")
	public ModelAndView detail(ModelAndView mav,
							   @PathVariable Long studentId) {

		Student student = studentService.get(studentId);

		mav.addObject("student", StudentDto.StudentResponse.of(student));
		mav.setViewName("student_detail");
		return mav;
	}

	@GetMapping(value="/register")
	public ModelAndView registerForm(ModelAndView mav) {

		mav.setViewName("student_register");
		return mav;
	}

	@PostMapping(value = "/register")
	public ModelAndView register(ModelAndView mav,
								 StudentDto.StudentRequest request) {

		log.info("studentDto: {}", request.toString());

		Student student = studentService.create(request.toEntity());

		mav.setViewName("redirect:/student/detail/"+student.getStudentId());
		return mav;
	}

	@GetMapping(value="/modify/{studentId}")
	public ModelAndView modifyForm(ModelAndView mav,
								   @PathVariable Long studentId) {

		Student student = studentService.get(studentId);

		mav.addObject("student", StudentDto.StudentResponse.of(student));
		mav.setViewName("student_modify");
		return mav;
	}

	@PostMapping(value="/modify/{studentId}")
	public ModelAndView modify(ModelAndView mav,
							   @PathVariable Long studentId,
							   StudentDto.StudentRequest request) {

		log.info("studentDto : {}", request.toString());

		studentService.update(request.toEntity(studentId));

		mav.setViewName("redirect:/student/detail/"+studentId);
		return mav;
	}

	@PostMapping(value="/delete/{studentId}")
	public ModelAndView delete(ModelAndView mav,
							   @PathVariable Long studentId) {

		studentService.delete(studentId);

		mav.setViewName("redirect:/student/list");
		return mav;
	}

}
