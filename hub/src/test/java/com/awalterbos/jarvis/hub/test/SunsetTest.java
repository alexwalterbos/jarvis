package com.awalterbos.jarvis.hub.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.TimeZone;

import com.awalterbos.jarvis.hub.tasks.SunsetTask;
import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class SunsetTest {

	private SunriseSunsetCalculator calculator = mock(SunriseSunsetCalculator.class);

	@Test
	public void testShouldRun() {
		when(calculator.getCivilSunsetCalendarForDate(any(Calendar.class))).thenAnswer(new Answer<Calendar>() {
			public Calendar answer(InvocationOnMock invocation) throws Throwable {
				Calendar argument = invocation.getArgumentAt(0, Calendar.class);
				// This assumes minutes being used in the Task!
				argument.add(Calendar.MINUTE, -1 * (SunsetTask.INTERVAL - 5));
				return argument;
			}
		});

		assertThat(SunsetTask.shouldRun(Calendar.getInstance(TimeZone.getTimeZone("Europe/Amsterdam")), calculator)).isEqualTo(true);
	}

	@Test
	public void testShouldntRun() {
		when(calculator.getCivilSunsetCalendarForDate(any(Calendar.class))).thenAnswer(new Answer<Calendar>() {
			public Calendar answer(InvocationOnMock invocation) throws Throwable {
				Calendar argument = invocation.getArgumentAt(0, Calendar.class);
				// This assumes minutes being used in the Task!
				argument.add(Calendar.MINUTE, -1 * (SunsetTask.INTERVAL + 5));
				return argument;
			}
		});

		assertThat(SunsetTask.shouldRun(Calendar.getInstance(TimeZone.getTimeZone("Europe/Amsterdam")), calculator)).isEqualTo(false);
	}
}
