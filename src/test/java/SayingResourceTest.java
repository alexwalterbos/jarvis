import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.awalterbos.jarvis.server.SayingDAO;
import com.awalterbos.jarvis.server.entities.Saying;
import com.awalterbos.jarvis.server.resources.SayingResource;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

public class SayingResourceTest {

	private static final SayingDAO DAO = mock(SayingDAO.class);

	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
			.addResource(new SayingResource(DAO))
			.build();

	private final Saying saying = new Saying().setFormat("Hello %s!").setDefaultText("Stranger");

	@Before
	public void setup() {
		when(DAO.findById(eq(1l))).thenReturn(saying);
	}

	@Test
	public void testGetSaying() throws Exception {
		assertThat(resources.client().target("/sayings/1").request().get(Saying.class)).isEqualTo(saying);
		verify(DAO).findById(1l);
	}

	@Test
	public void testAddSaying() throws Exception {
		assertThat(resources.client()
				.target("/sayings/create")
				.request()
				.get(Saying.class)).isEqualTo(saying);
		verify(DAO).create(any(Saying.class));
	}
}
