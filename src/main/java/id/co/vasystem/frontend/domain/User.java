package id.co.vasystem.frontend.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection ="User")
public class User {

	@Id
	private String id;
	private String username;
	private String password;
	private String email;
	private String status;
	private String role;
}
