package io.simpolor.basic.model;

import io.simpolor.basic.repository.entity.Student;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class StudentDto {

	private long seq;
	private String name;
	private int grade;
	private int age;

	public Student toEntity(){

		Student student = new Student();
		student.setSeq(this.seq);
		student.setName(this.name);
		student.setGrade(this.grade);
		student.setAge(this.age);

		return student;
	}

	public static StudentDto of(Student student){

		StudentDto studentDto = new StudentDto();
		studentDto.setSeq(student.getSeq());
		studentDto.setName(student.getName());
		studentDto.setGrade(student.getGrade());
		studentDto.setAge(student.getAge());

		return studentDto;
	}

	public static List<StudentDto> of(List<Student> students){

		return students.stream()
				.map(StudentDto::of)
				.collect(Collectors.toList());
	}
}
