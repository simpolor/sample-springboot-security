package io.simpolor.custom.repository.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long studentId;

	/* @Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
			name = "UUID",
			strategy = "org.hibernate.id.UUIDGenerator"
	)
	private long id; */

	private String name;
	private Integer grade;
	private Integer age;
}
