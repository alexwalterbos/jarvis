package com.awalterbos.antenna.test;

import static org.assertj.core.api.Assertions.assertThat;

import com.awalterbos.jarvis.server.tasks.NightNightTask;
import org.junit.Test;

public class NightNightTest {

	@Test
	public void testShouldRun() {
		assertThat(
				NightNightTask.shouldRun(NightNightTask.NIGHT_TIME.plusMinutes(NightNightTask.INTERVAL - 1)))
				.isEqualTo(true);
	}

	@Test
	public void testShouldntRun() {
		assertThat(
				NightNightTask.shouldRun(NightNightTask.NIGHT_TIME.plusMinutes(NightNightTask.INTERVAL + 1)))
				.isEqualTo(false);
	}

}
