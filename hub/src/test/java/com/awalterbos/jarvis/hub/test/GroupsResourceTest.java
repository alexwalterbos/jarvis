package com.awalterbos.jarvis.hub.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Collection;

import com.awalterbos.jarvis.hub.JarvisModule;
import com.awalterbos.jarvis.hub.backends.GroupsBackend;
import com.awalterbos.jarvis.hub.backends.TokensBackend;
import com.awalterbos.jarvis.hub.data.daos.Groups;
import com.awalterbos.jarvis.hub.data.daos.Tokens;
import com.awalterbos.jarvis.hub.data.entities.Group;
import com.awalterbos.jarvis.hub.data.entities.Token;
import com.awalterbos.jarvis.hub.data.entities.User;
import com.awalterbos.jarvis.hub.resources.GroupsResource;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class GroupsResourceTest {

	private static final Groups GROUPS = mock(Groups.class);
	private static final Tokens TOKENS = mock(Tokens.class);

	@Rule
	public final ResourceTestRule resources = ResourceTestRule.builder()
			.addResource(new GroupsResource(mock(GroupsBackend.class), mock(TokensBackend.class)))
			.build();
	private static final int SIGNAL_OFF = 12345;
	private static final int SIGNAL_ON = 54321;
	private final String TEST_TOKEN = "Token test_token";
	private final String ADMIN_TOKEN = "Token admin_token";
	private long newId = 0L;

	private Group group;
	private Token token;
	private Token adminToken;
	private User user;
	private User admin;

	@Before
	public void setup() {
		newId = 0L;

		user = new User().setId(1L).setUsername("TestUser").setAdmin(false);
		token = new Token().setToken(TEST_TOKEN).setUser(user);
		admin = new User().setId(1L).setUsername("TestUser").setAdmin(true);
		adminToken = new Token().setToken(ADMIN_TOKEN).setUser(admin);

		Injector injector = Guice.createInjector(Modules.override(new JarvisModule()).with(new JarvisTestModule()));
		group = injector.getInstance(Group.class);
		group.setSignalOff(SIGNAL_OFF)
				.setSignalOn(SIGNAL_ON)
				.setName("TestGroup")
				.setDescription("Test");

		doReturn(group).when(GROUPS).findByIdOrThrow(any(Long.class));
		doReturn(group).when(GROUPS).merge(any(Group.class));
		doReturn(group.setId(incrementAndGetNewId())).when(GROUPS).persistOrMerge(any(Group.class));
		ArrayList<Group> toBeReturned = new ArrayList<Group>();
		toBeReturned.add(group);
		doReturn(toBeReturned).when(GROUPS).listAll();
		doThrow(new EntityNotFoundException()).when(GROUPS).findByIdOrThrow(eq(2L));
		doThrow(new NullPointerException()).when(GROUPS).delete(eq(2L));

		doReturn(adminToken).when(TOKENS).findByKeyOrThrow(eq(ADMIN_TOKEN));
		doReturn(token).when(TOKENS).findByKeyOrThrow(eq(TEST_TOKEN));
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
		Response result = resources.client()
				.target("/groups/activate/1")
				.request()
				.header(HttpHeaders.AUTHORIZATION, TEST_TOKEN)
				.get();
		assertThat(result.getStatusInfo()).isEqualTo(Response.Status.fromStatusCode(204));
		System.out.println(result);
	}

	@Test
	public void testActivateAll() {
		Response result = resources.client()
				.target("/groups/activate_all")
				.request()
				.header(HttpHeaders.AUTHORIZATION, TEST_TOKEN)
				.get();
		assertThat(result.getStatusInfo()).isEqualTo(Response.Status.fromStatusCode(204));
		System.out.println(result);
	}

	@Test
	public void testDeactivateGroup() {
		Response result = resources.client()
				.target("/groups/deactivate/1")
				.request()
				.header(HttpHeaders.AUTHORIZATION, TEST_TOKEN)
				.delete();
		assertThat(result.getStatusInfo()).isEqualTo(Response.Status.fromStatusCode(204));
		System.out.println(result);
	}

	@Test
	public void testDeactivateAll() {
		Response result = resources.client()
				.target("/groups/deactivate_all")
				.request()
				.header(HttpHeaders.AUTHORIZATION, TEST_TOKEN)
				.delete();
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
