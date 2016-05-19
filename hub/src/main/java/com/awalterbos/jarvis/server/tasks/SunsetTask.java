package com.awalterbos.jarvis.server.tasks;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import com.awalterbos.jarvis.server.GroupsBackend;
import com.awalterbos.jarvis.server.util.TimeUtil;
import com.google.common.util.concurrent.AbstractScheduledService;
import com.google.inject.Inject;
import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ManagedSessionContext;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class SunsetTask extends AbstractScheduledService {

	private static final String LAT = "52.081919";
	private static final String LONG = "4.342304";
	public static final int INTERVAL = 10;
	private static final TimeUnit INTERVAL_UNIT = TimeUnit.MINUTES;
	private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("Europe/Amsterdam");
	private static SunriseSunsetCalculator calculator;
	private SessionFactory sessionFactory;
	private GroupsBackend groupsBackend;

	@Inject
	public SunsetTask(
			SessionFactory sessionFactory,
			GroupsBackend groupsBackend
	) {
		this.sessionFactory = sessionFactory;
		this.groupsBackend = groupsBackend;

		Location location = new Location(LAT, LONG);
		calculator =
				new SunriseSunsetCalculator(location, TIME_ZONE);
	}

	@Override
	protected void runOneIteration() throws Exception {
		LocalDateTime now = LocalDateTime.now(DateTimeZone.forID("Europe/Amsterdam"));
		if (!shouldRun(Calendar.getInstance(TIME_ZONE), calculator)) {
			System.out.println("[" + now.toLocalTime().toString() + "] Sunset task not run.");
			return;
		}
		System.out.println("[" + now.toLocalTime().toString() + "] Running sunset task.");
		Session session = sessionFactory.openSession();
		try {
			ManagedSessionContext.bind(session);
			Transaction transaction = session.beginTransaction();
			try {
				groupsBackend.activateSunsetTriggered();
				transaction.commit();
			}
			catch (Exception e) {
				transaction.rollback();
				e.printStackTrace();
			}
		}
		finally {
			session.close();
			ManagedSessionContext.unbind(sessionFactory);
		}
		System.out.println("Sunset task completed.");
	}

	public static boolean shouldRun(Calendar nowCal, SunriseSunsetCalculator calculator) {
		LocalTime now = LocalTime.fromCalendarFields(nowCal);
		LocalTime sunset =
				LocalTime.fromCalendarFields(calculator.getCivilSunsetCalendarForDate(nowCal));

		return (now.isAfter(sunset) && TimeUtil.getNowPlusInterval(now, INTERVAL * -1, INTERVAL_UNIT).isBefore(sunset));
	}

	@Override
	protected Scheduler scheduler() {
		Scheduler scheduler = Scheduler.newFixedDelaySchedule(1, INTERVAL, INTERVAL_UNIT);
		System.out.println("Scheduling sunset task with intermittent delays of " + INTERVAL + " " + INTERVAL_UNIT
				.name()
				.toLowerCase() + ".");
		return scheduler;
	}
}
