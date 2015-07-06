package com.awalterbos.antenna.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import javax.net.ssl.SSLEngineResult;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

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

	private Group group;

	@Before
	public void setup() {
		Injector injector = Guice.createInjector(Modules.override(new JarvisModule()).with(new JarvisTestModule()));
		group = injector.getInstance(Group.class);
		group.setSignalOff(SIGNAL_OFF)
				.setSignalOn(SIGNAL_ON)
				.setName("TestGroup")
				.setDescription("Test");

		doReturn(group).when(DAO).findById(eq(1L));
		doReturn(group).when(DAO).persistOrMerge(any(Group.class));
		doThrow(new EntityNotFoundException()).when(DAO).findById(eq(2L));
		doThrow(new NullPointerException()).when(DAO).delete(eq(2L));
	}

	@Test
	public void testUpdateGroup() {
		createGroup(group);

		Group group = this.group;
		group.setDescription("Updated description");

		Group result = resources.client()
				.target("/groups")
				.request()
				.put(Entity.json(group), Group.class);
		assertThat(result).isEqualTo(group);

		result = resources.client()
				.target("/groups")
				.request()
				.get(Group.class);
		assertThat(result).isEqualTo(group);

		group.setDescription(null);

		result = resources.client()
				.target("/groups")
				.request()
				.put(Entity.json(group), Group.class);
		assertThat(result).isEqualTo(group);
	}

	@Test
	public void testCreateGroup() {
		Group result = resources.client()
				.target("/groups/create/")
				.request()
				.post(Entity.json(group), Group.class);
		assertThat(result).isEqualTo(group);
	}

	@Test
	public void activateGroup() {
		Response result = resources.client().target("/groups/activate/1").request().put(Entity.json(""));
		assertThat(result.getStatusInfo()).isEqualTo(Response.Status.fromStatusCode(204));
		System.out.println(result);
	}

	@Test
	public void deactivateGroup() {
		Response result = resources.client().target("/groups/deactivate/1").request().delete();
		assertThat(result.getStatusInfo()).isEqualTo(Response.Status.fromStatusCode(204));
		System.out.println(result);
	}

	@Test
	public void storylineTest() {
		createGroup(group);

		activateGroup();

		group.setDescription("TestGroupTest");

		updateGroup(group);
	}

	private void createGroup(Group group) {
		Response post = resources.client()
				.target("/groups/create/")
				.request()
				.post(Entity.json(group));

		assertThat(post.getStatusInfo()).isEqualTo(Response.Status.OK);
	}

	private void updateGroup(Group group) {
		Response response = resources.client()
				.target("/groups")
				.request()
				.put(Entity.json(group));

		assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
	}

	private void activate(Group group) {
		Response result = resources.client().target("/groups/activate/1").request().put(Entity.json(""));
		assertThat(result.getStatusInfo()).isEqualTo(Response.Status.fromStatusCode(204));
	}
}
