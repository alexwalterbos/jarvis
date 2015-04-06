import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import com.awalterbos.jarvis.server.data.daos.Sayings;
import com.awalterbos.jarvis.server.data.entities.Saying;
import com.awalterbos.jarvis.server.resources.SayingsResource;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

public class SayingsResourceTest {

	private static final Sayings DAO = mock(Sayings.class);

	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
			.addResource(new SayingsResource(DAO))
			.build();

	private final Saying saying = new Saying().setFormat("Hello %s!").setDefaultText("Stranger");

	@Before
	public void setup() {
		doReturn(saying).when(DAO).findById(eq(1L));
		doReturn(saying).when(DAO).createOrUpdate(any(Saying.class));
		doThrow(new EntityNotFoundException()).when(DAO).findById(2L);
		doThrow(new NullPointerException()).when(DAO).delete(eq(2L));
	}

	@Test
	public void testGetSaying() throws Exception {
		assertThat(resources.client().target("/sayings/1").request().get(Saying.class)).isEqualTo(saying);
		verify(DAO).findById(1l);
	}

	@Test
	public void testGetNull() throws Exception {
		Response response = resources.client()
				.target("/sayings/2")
				.request()
				.get();
		assertThat(response.getStatusInfo()).isEqualTo(Response.Status.NOT_FOUND);
	}

	@Test
	public void testAddSaying() throws Exception {
		assertThat(resources.client()
				.target("/sayings/create")
				.request()
				.post(Entity.json(saying), Saying.class)).isEqualTo(saying);
		verify(DAO).createOrUpdate(any(Saying.class));
	}

	@Test
	public void testDeleteSaying() throws Exception {
		Response response = resources.client()
				.target("/sayings/delete/1")
				.request()
				.delete();
		assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);

		verify(DAO).delete(eq(1L));
	}

	@Test
	public void testDeleteNull() throws Exception {
		Response response = resources.client()
				.target("/sayings/delete/2")
				.request()
				.delete();
		assertThat(response.getStatusInfo()).isEqualTo(Response.Status.NOT_FOUND);

		verify(DAO).delete(eq(2L));
	}
}
