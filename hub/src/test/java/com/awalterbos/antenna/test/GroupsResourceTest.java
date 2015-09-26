package com.awalterbos.antenna.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Collection;

import com.awalterbos.jarvis.server.JarvisModule;
import com.awalterbos.jarvis.server.data.daos.Groups;
import com.awalterbos.jarvis.server.data.entities.Group;
import com.awalterbos.jarvis.server.resources.GroupsResource;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

public class GroupsResourceTest {

	private static final Groups DAO = mock(Groups.class);

	private static final MockAntenna antenna = new MockAntenna();

	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
			.addResource(new GroupsResource(DAO, antenna))
			.build();
	private static final int SIGNAL_OFF = 12345;
	private static final int SIGNAL_ON = 54321;
	private long newId = 0L;

	private Group group;

	@Before
	public void setup() {
		Injector injector = Guice.createInjector(Modules.override(new JarvisModule()).with(new JarvisTestModule()));
		group = injector.getInstance(Group.class);
		group.setSignalOff(SIGNAL_OFF)
				.setSignalOn(SIGNAL_ON)
				.setName("TestGroup")
				.setDescription("Test");

		doReturn(group).when(DAO).findById(any(Long.class));
		doReturn(group).when(DAO).merge(any(Group.class));
		doReturn(group.setId(incrementAndGetNewId())).when(DAO).persistOrMerge(any(Group.class));
		ArrayList<Group> toBeReturned = new ArrayList<Group>();
		toBeReturned.add(group);
		doReturn(toBeReturned).when(DAO).listAll();
		doThrow(new EntityNotFoundException()).when(DAO).findById(eq(2L));
		doThrow(new NullPointerException()).when(DAO).delete(eq(2L));
	}

	private long incrementAndGetNewId() {
		newId += 1;
		return newId;
	}

	@Test
	public void testUpdateGroup() {
		Group group = createGroup(this.group);

		group.setDescription("Updated description");

		Group result = resources.client()
				.target("/groups/")
				.request()
				.put(Entity.json(group), Group.class);
		assertThat(result).isEqualTo(group);

		result = resources.client()
				.target("/groups/" + result.getId())
				.request()
				.get(Group.class);
		assertThat(result).isEqualTo(group);
	}

	@Test
	public void testCreateGroup() {
		Group result = resources.client()
				.target("/groups/create/")
				.request()
				.post(Entity.json(group), Group.class);
		assertThat(result).isEqualTo(group);

		resources.client()
				.target("/groups/")
				.request()
				.get(Collection.class);
	}

	@Test
	public void testActivateGroup() {
		Response result = resources.client().target("/groups/activate/1").request().put(Entity.json(""));
		assertThat(result.getStatusInfo()).isEqualTo(Response.Status.fromStatusCode(204));
		System.out.println(result);
	}

	@Test
	public void testActivateAll() {
		Response result = resources.client().target("/groups/activate_all").request().get();
		assertThat(result.getStatusInfo()).isEqualTo(Response.Status.fromStatusCode(204));
		System.out.println(result);
	}

	@Test
	public void testDeactivateGroup() {
		Response result = resources.client().target("/groups/deactivate/1").request().delete();
		assertThat(result.getStatusInfo()).isEqualTo(Response.Status.fromStatusCode(204));
		System.out.println(result);
	}

	@Test
	public void testDeactivateAll() {
		Response result = resources.client().target("/groups/deactivate_all").request().get();
		assertThat(result.getStatusInfo()).isEqualTo(Response.Status.fromStatusCode(204));
		System.out.println(result);
	}

	@Test
	public void storylineTest() {
		Group group = createGroup(this.group);

		activate(group);

		group.setDescription("TestGroupTest");

		updateGroup(group);

		deactivate(group);
	}

	private Group createGroup(Group group) {
		Group response = resources.client()
				.target("/groups/create/")
				.request()
				.post(Entity.json(group), Group.class);

		assertThat(response).isEqualTo(group);

		return response;
	}

	private void updateGroup(Group group) {
		Response response = resources.client()
				.target("/groups")
				.request()
				.put(Entity.json(group));

		assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
	}

	private void activate(Group group) {
		Response result = resources.client().target("/groups/activate/" + group.getId())
				.request()
				.put(Entity.json(""));
		assertThat(result.getStatusInfo()).isEqualTo(Response.Status.fromStatusCode(204));
	}

	private void deactivate(Group group) {
		Response result = resources.client().target("/groups/deactivate/" + group.getId())
				.request()
				.delete();
		assertThat(result.getStatusInfo()).isEqualTo(Response.Status.fromStatusCode(204));
	}
}
