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
	@GetMapping("/list")
	public ModelAndView list(ModelAndView mav) {

		List<Student> students = studentService.getAll();

		mav.addObject("studentList", StudentDto.StudentResponse.of(students));
		mav.setViewName("admin/student_list");
		return mav;
	}

	@Secured({"ROLE_ADMIN"})
	@GetMapping("/detail/{studentId}")
	public ModelAndView detail(ModelAndView mav,
							   @PathVariable Long studentId) {

		Student student = studentService.get(studentId);

		mav.addObject("student", StudentDto.StudentResponse.of(student));
		mav.setViewName("admin/student_detail");
		return mav;
	}

	@Secured({"ROLE_ADMIN"})
	@GetMapping("/register")
	public ModelAndView registerForm(ModelAndView mav) {

		mav.setViewName("admin/student_register");
		return mav;
	}

	@Secured({"ROLE_ADMIN"})
	@PostMapping("/register")
	public ModelAndView register(ModelAndView mav,
								 StudentDto.StudentRequest request) {

		Student student = studentService.create(request.toEntity());

		mav.setViewName("redirect:/admin/student/detail/"+student.getStudentId());
		return mav;
	}

	@Secured({"ROLE_ADMIN"})
	@GetMapping("/modify/{studentId}")
	public ModelAndView modifyForm(ModelAndView mav,
								   @PathVariable Long studentId) {

		Student student = studentService.get(studentId);

		mav.addObject("student", StudentDto.StudentResponse.of(student));
		mav.setViewName("admin/student_modify");
		return mav;
	}

	@Secured({"ROLE_ADMIN"})
	@PostMapping("/modify/{studentId}")
	public ModelAndView modify(ModelAndView mav,
							   @PathVariable Long studentId,
							   StudentDto.StudentRequest request) {

		studentService.update(request.toEntity(studentId));

		mav.setViewName("redirect:/admin/student/detail/"+studentId);
		return mav;
	}

	@Secured({"ROLE_ADMIN"})
	@PostMapping("/delete/{studentId}")
	public ModelAndView delete(ModelAndView mav,
							   @PathVariable Long studentId) {

		studentService.delete(studentId);

		mav.setViewName("redirect:/admin/student/list");
		return mav;
	}

}
