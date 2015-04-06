package com.awalterbos.jarvis.server.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@Entity
@Accessors(chain = true)
public class EntityWithID {

	@Id
	@Column(name = "id", nullable = false)
	@Setter(AccessLevel.NONE)
	private long id;

	@NotEmpty(message = "You must specify a name for the entity")
	@Column(name = "name", nullable = false, length = 32)
	private String name;

	@Column(name = "description")
	private String description;
}
