package models;

import com.awalterbos.jarvis.hub.data.entities.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class UserModel {
	private Long id;
	private String username;
	private String password;
	private boolean admin;

	public static UserModel fromUser(User user) {
		return new UserModel()
				.setId(user.getId())
				.setUsername(user.getUsername())
				.setAdmin(user.isAdmin());
	}
}
