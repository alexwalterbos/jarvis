package com.awalterbos.jarvis.server.data.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import com.awalterbos.jarvis.server.exceptions.NotYetImplementedException;
import com.awalterbos.jarvis.server.interfaces.Radio;
import com.awalterbos.jarvis.server.interfaces.Receiver;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = false)
@Data
@MappedSuperclass
@Accessors(chain = true)
@Table(name = "groups")
public class Group extends EntityWithID implements Receiver, Radio<Group> {

	@Column(name = "codeword")
	private int codeword;

	@Override
	public void activate() {
		// TODO send signal <channel> ON
		throw new NotYetImplementedException();
	}

	@Override
	public void deactivate() {
		// TODO send signal <channel> OFF
		throw new NotYetImplementedException();
	}
}
