package io.simpolor.multiconfig.controller;

import io.simpolor.multiconfig.model.StudentDto;
import io.simpolor.multiconfig.repository.entity.Student;
import io.simpolor.multiconfig.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/admin/student")
@RequiredArgsConstructor
public class StudentAdminController {

	private final StudentService studentService;

	@Secured({"ROLE_ADMIN"})
	@GetMapping(value = "/list")
	public ModelAndView list(ModelAndView mav) {

		// 시큐리티 테스트를 위한 구분
		Long totalCount = studentService.getTotalCount();
		List<StudentDto> studentDtos = StudentDto.of(studentService.getAll());

		mav.addObject("totalCount", totalCount);
		mav.addObject("studentDtos", studentDtos);
		mav.setViewName("admin/student_list");

		return mav;
	}

	@Secured({"ROLE_ADMIN"})
	@GetMapping(value="/detail/{seq}")
	public ModelAndView detail(ModelAndView mav, @PathVariable long seq) {

		Student student = studentService.get(seq);

		mav.addObject("studentDto", StudentDto.of(student));
		mav.setViewName("admin/student_detail");
		return mav;
	}

	@Secured({"ROLE_ADMIN"})
	@GetMapping(value="/register")
	public ModelAndView studentRegisterForm(ModelAndView mav) {

		mav.setViewName("admin/student_register");
		return mav;
	}

	@Secured({"ROLE_ADMIN"})
	@PostMapping(value = "/register")
	public ModelAndView register(ModelAndView mav, StudentDto studentDto) {

		log.info("studentDto : {}", studentDto.toString());

		Student student = studentService.create(studentDto.toEntity());

		mav.setViewName("redirect:/admin/student/detail/"+student.getSeq());
		return mav;
	}

	@Secured({"ROLE_ADMIN"})
	@GetMapping(value="/modify/{seq}")
	public ModelAndView studentModifyForm(ModelAndView mav, @PathVariable long seq) {

		Student student = studentService.get(seq);

		mav.addObject("studentDto", StudentDto.of(student));
		mav.setViewName("admin/student_modify");
		return mav;
	}

	@Secured({"ROLE_ADMIN"})
	@PostMapping(value="/modify/{seq}")
	public ModelAndView modify(ModelAndView mav, @PathVariable long seq, StudentDto studentDto) {

		log.info("studentDto : {}", studentDto.toString());

		studentDto.setSeq(seq);
		studentService.update(studentDto.toEntity());

		mav.setViewName("redirect:/admin/student/detail/"+seq);
		return mav;
	}

	@Secured({"ROLE_ADMIN"})
	@PostMapping(value="/delete/{seq}")
	public ModelAndView delete(ModelAndView mav, @PathVariable long seq) {

		studentService.delete(seq);

		mav.setViewName("redirect:/admin/student/list");
		return mav;
	}

}
