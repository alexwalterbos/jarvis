package com.awalterbos.jarvis.server.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.awalterbos.jarvis.server.interfaces.Radio;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@Accessors(chain = true)
@Entity
@ToString(of = { "id", "name", "description" })
@Table(name = "groups")
public class Group implements EntityWithID, Radio<Group> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotEmpty(message = "You must specify a name for the entity")
	@Column(name = "name", nullable = false, length = 32)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "trigger_on_sunset")
	private Boolean triggerOnSunset;

	@Column(name = "signal_on")
	private Integer signalOn;

	@Column(name = "signal_off")
	private Integer signalOff;

	@Column(name = "protocol")
	private long protocol;
}
