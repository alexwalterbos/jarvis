package com.awalterbos.jarvis.hub.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;
import models.UserModel;
import org.mindrot.jbcrypt.BCrypt;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "users")
public class User implements EntityWithId {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "admin")
	private boolean admin;

	public static User createFromModel(UserModel userModel) {
		return new User()
				.setUsername(userModel.getUsername())
				.setPassword(BCrypt.hashpw(userModel.getPassword(), BCrypt.gensalt()));
	}
}
