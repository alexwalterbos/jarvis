package com.awalterbos.jarvis.hub.util;

import java.util.concurrent.TimeUnit;

import com.awalterbos.jarvis.hub.exceptions.NotYetImplementedException;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class TimeUtil {
	public static LocalTime getNowPlusInterval(LocalTime now, int interval, TimeUnit intervalUnit) {
		switch (intervalUnit) {
			case SECONDS:
				return now.plusSeconds(interval);
			case MINUTES:
				return now.plusMinutes(interval);
			case HOURS:
				return now.plusHours(interval);
			default:
				throw new NotYetImplementedException(
						"Non-supported interval used. Use TimeUnit.{SECONDS, MINUTES, HOURS}.");
		}
	}

	public static LocalDateTime getNowPlusInterval(LocalDateTime now, int interval, TimeUnit intervalUnit) {
		switch (intervalUnit) {
			case SECONDS:
				return now.plusSeconds(interval);
			case MINUTES:
				return now.plusMinutes(interval);
			case HOURS:
				return now.plusHours(interval);
			default:
				throw new NotYetImplementedException(
						"Non-supported interval used. Use TimeUnit.{SECONDS, MINUTES, HOURS}.");
		}
	}
}
