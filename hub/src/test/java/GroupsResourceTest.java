import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import com.awalterbos.jarvis.server.data.daos.Groups;
import com.awalterbos.jarvis.server.data.entities.Group;
import com.awalterbos.jarvis.server.resources.GroupsResource;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

public class GroupsResourceTest {

	private static final Groups DAO = mock(Groups.class);

	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
			.addResource(new GroupsResource(DAO))
			.build();
	private static final int CODEWORD = 12345;

	private final String testGroup = "TestGroup";
	private final Group group = (Group) new Group().setCodeword(CODEWORD).setDescription("TEST").setName(testGroup);

	@Before
	public void setup() {
		doReturn(group).when(DAO).findById(eq(1L));
		doReturn(group).when(DAO).createOrUpdate(any(Group.class));
		doThrow(new EntityNotFoundException()).when(DAO).findById(eq(2L));
		doThrow(new NullPointerException()).when(DAO).delete(eq(2L));
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
	@Ignore("Not implemented server-side")
	public void activateGroup() {
		Response result = resources.client().target("/groups/activate/1").request().put(Entity.json(""));
		System.out.println(result);
	}

	@Test
	@Ignore("Not implemented server-side")
	public void deactivateGroup() {
		Response result = resources.client().target("/groups/deactivate/1").request().put(Entity.json(""));
		System.out.println(result);
	}
}
