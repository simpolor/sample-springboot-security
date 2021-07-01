package io.simpolor.custom.repository.entity;

import io.simpolor.custom.repository.converter.StringListConverter;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Access {

	@Id
	@GeneratedValue
	private long accessSeq;

	private String accessUrl;

	@Convert(converter = StringListConverter.class)
	private List<String> accessRoles;
}
