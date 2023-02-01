package io.simpolor.custom.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {

	@Id
	private String series;

	private String username;
	private String token;
	private Date lastUsed;

}
