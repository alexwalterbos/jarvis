package com.awalterbos.jarvis.server;

import java.util.Collection;

import com.awalterbos.antenna.Antenna;
import com.awalterbos.antenna.Protocol;
import com.awalterbos.jarvis.server.data.daos.Groups;
import com.awalterbos.jarvis.server.data.entities.Group;
import com.awalterbos.protocol.ProtocolOne;
import com.awalterbos.protocol.ProtocolTwo;
import com.google.common.base.Strings;
import com.google.inject.Inject;

public class GroupsBackend {

	private static final Protocol PROTOCOL_ONE = new ProtocolOne();
	private static final Protocol PROTOCOL_TWO = new ProtocolTwo();

	private final Antenna antenna;
	private final Groups groups;

	@Inject
	public GroupsBackend(Antenna antenna, Groups groups) {
		this.antenna = antenna;
		this.groups = groups;
	}

	public Collection<Group> listAll() {
		return groups.listAll();
	}

	public Group findById(long id) {
		return groups.findById(id);
	}

	public Group create(Group group) {
		return groups.persist(group);
	}

	public void activate(long id) throws InterruptedException {
		Group group = groups.findById(id);
		antenna.send(getProtocol(group), group.getSignalOn());
	}

	public void deactivate(long id) throws InterruptedException {
		Group group = groups.findById(id);
		antenna.send(getProtocol(group), group.getSignalOff());
	}

	public void activateAll() throws InterruptedException {
		for (Group group : groups.listAll()) {
			antenna.send(getProtocol(group), group.getSignalOn());
		}
	}

	public void deactivateAll() throws InterruptedException {
		for (Group group : groups.listAll()) {
			antenna.send(getProtocol(group), group.getSignalOff());
		}
	}

	public Group updateGroup(Group group) {
		Group fromDB = groups.findById(group.getId());

		if (!Strings.isNullOrEmpty(group.getName())) {
			fromDB.setName(group.getName());
		}

		if (group.getDescription() != null) {
			if (group.getDescription().isEmpty()) {
				fromDB.setDescription(null);
			}
			else {
				fromDB.setDescription(group.getDescription());
			}
		}

		if (group.getSignalOff() != null) {
			fromDB.setSignalOff(group.getSignalOff());
		}

		if (group.getSignalOn() != null) {
			fromDB.setSignalOn(group.getSignalOn());
		}

		if (group.getTriggerOnSunset() != null) {
			fromDB.setTriggerOnSunset(group.getTriggerOnSunset());

		}

		return groups.merge(group);
	}

	public void activateSunsetTriggered() throws InterruptedException {
		Collection<Group> sunsetTriggered = (Collection<Group>) groups.findSunsetTriggered();
		for (Group group : sunsetTriggered) {
			System.out.println("Activating group " + group.getId());
			antenna.send(getProtocol(group), group.getSignalOn());
		}
	}

	private Protocol getProtocol(Group group) {
		switch ((int) group.getProtocol()) {
			case 1:
				return PROTOCOL_ONE;
			case 2:
				return PROTOCOL_TWO;
			default:
				throw new IllegalArgumentException("Non-implemented protocol");
		}
	}
}
