package com.awalterbos.jarvis.hub.test;

import static org.assertj.core.api.Assertions.assertThat;

import com.awalterbos.jarvis.hub.tasks.NightNightTask;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.junit.Test;

public class NightNightTest {

	@Test
	public void testShouldRun() {
		LocalDateTime now = LocalDateTime.now(DateTimeZone.forID("Europe/Amsterdam"));
		LocalDateTime nightTime = now.minusMinutes(10);
		boolean shouldRun = NightNightTask.shouldRun(now, nightTime);
		assertThat(shouldRun).isEqualTo(true);
	}

	@Test
	public void testShouldntRun() {
		LocalDateTime now = LocalDateTime.now(DateTimeZone.forID("Europe/Amsterdam"));
		LocalDateTime nightTime = now.plusMinutes(10);
		boolean shouldRun = NightNightTask.shouldRun(now, nightTime);
		assertThat(shouldRun).isEqualTo(false);
	}
}
