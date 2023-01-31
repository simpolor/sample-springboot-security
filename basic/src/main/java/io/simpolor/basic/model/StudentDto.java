package io.simpolor.basic.model;

import io.simpolor.basic.repository.entity.Student;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class StudentDto {

	@Setter
	@Getter
	public static class StudentRequest {

		private String name;
		private Integer grade;
		private Integer age;

		public Student toEntity(){
			return this.toEntity(null);
		}

		public Student toEntity(Long studentId){

			Student student = new Student();
			student.setStudentId(studentId);
			student.setName(this.name);
			student.setGrade(this.grade);
			student.setAge(this.age);

			return student;
		}
	}

	@Setter
	@Getter
	public static class StudentResponse {

		private Long id;
		private String name;
		private Integer grade;
		private Integer age;

		public static StudentResponse of(Student student){

			StudentResponse response = new StudentResponse();
			response.setId(student.getStudentId());
			response.setName(student.getName());
			response.setGrade(student.getGrade());
			response.setAge(student.getAge());

			return response;
		}

		public static List<StudentResponse> of(List<Student> students){

			return students.stream()
					.map(StudentResponse::of)
					.collect(Collectors.toList());
		}
	}
}
