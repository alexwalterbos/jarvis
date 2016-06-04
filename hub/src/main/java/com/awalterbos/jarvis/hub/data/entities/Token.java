package com.awalterbos.jarvis.hub.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import org.joda.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Entity
@Table(name="tokens")
public class Token {

	@Id
	@Column(name = "token")
	private String token;

	@JsonIgnore
	@OneToOne(targetEntity = User.class)
	private User user;

	@JsonIgnore
	@Column(name = "created")
	private LocalDateTime created;

	public static Token fromUUID(UUID uuid, User user) {
		return new Token()
				.setToken(uuid.toString())
				.setUser(user);
	}
}
