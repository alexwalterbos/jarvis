package com.awalterbos.jarvis.server.data.entities;

import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import lombok.Data;

@Data
@MappedSuperclass
@Table(name = "lights")
public class Light extends EntityWithID {

	@JoinColumn(name = "group_id")
	private Group group;
}
