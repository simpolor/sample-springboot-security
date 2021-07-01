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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String series;

	// 여기서 username은 User의 email을 뜻한다.
	private String username;

	private String token;

	private Date lastUsed;

}
