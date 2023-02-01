package io.simpolor.custom.repository.entity;

import io.simpolor.custom.repository.converter.StringListConverter;
import lombok.Data;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Data
@Entity
public class Access {

	@Id
	@GeneratedValue
	private Long accessId;

	private String targetUrl;

	@Convert(converter = StringListConverter.class)
	private List<String> roles;
}
