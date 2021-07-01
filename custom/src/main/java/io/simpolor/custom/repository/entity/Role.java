package io.simpolor.custom.repository.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

	@Id
	@GeneratedValue
	private long roleSeq;

	private String roleCode;

	private String roleName;

}
