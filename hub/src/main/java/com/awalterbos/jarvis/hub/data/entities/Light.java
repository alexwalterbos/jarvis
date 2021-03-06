package com.awalterbos.jarvis.hub.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@Entity
@ToString(of = { "id", "name", "description" })
@Table(name = "lights")
public class Light implements EntityWithName {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@NotEmpty(message = "You must specify a name for the entity")
	@Column(name = "name", nullable = false, length = 32)
	private String name;

	@Column(name = "description")
	private String description;

	@JoinColumn(name = "group_id")
	@OneToOne(fetch = FetchType.LAZY)
	private Group group;
}
