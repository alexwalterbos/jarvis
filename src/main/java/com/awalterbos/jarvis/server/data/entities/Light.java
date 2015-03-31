package com.awalterbos.jarvis.server.data.entities;

import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "lights")
public class Light extends EntityWithID {

	@JoinColumn(name = "group_id")
	private Group group;
}
