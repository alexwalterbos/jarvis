package com.awalterbos.jarvis.server.tasks;

import java.util.concurrent.TimeUnit;

import com.awalterbos.jarvis.server.GroupsBackend;
import com.awalterbos.jarvis.server.util.TimeUtil;
import com.google.common.util.concurrent.AbstractScheduledService;
import com.google.inject.Inject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ManagedSessionContext;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

public class NightNightTask extends AbstractScheduledService {
	public static final int INTERVAL = 30;
	public static final TimeUnit INTERVAL_UNIT = TimeUnit.MINUTES;
	private final SessionFactory sessionFactory;
	private GroupsBackend groupsBackend;

	@Inject
	public NightNightTask(
			SessionFactory sessionFactory,
			GroupsBackend groupsBackend
	) {
		this.sessionFactory = sessionFactory;
		this.groupsBackend = groupsBackend;
	}

	@Override
	protected void runOneIteration() throws Exception {
		LocalDateTime now = LocalDateTime.now(DateTimeZone.forID("Europe/Amsterdam"));
		LocalDateTime nightTime =
				LocalDateTime.now(DateTimeZone.forID("Europe/Amsterdam")).withHourOfDay(1).withMinuteOfHour(0);
		if (!shouldRun(now, nightTime)) {
			System.out.println("[" + now.toLocalTime().toString() + "] NightNightTask not run. Night time: "
					+ nightTime.toLocalTime().toString() + ", interval: " + INTERVAL + " " + INTERVAL_UNIT.name()
					.toLowerCase() + ".");
			return;
		}
		System.out.println("[" + now.toLocalTime().toString() + "] Night night!");
		Session session = sessionFactory.openSession();
		try {
			ManagedSessionContext.bind(session);
			Transaction transaction = session.beginTransaction();
			try {
				groupsBackend.deactivateAll();
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
		System.out.println("NightNightTask completed.");
	}

	public static boolean shouldRun(LocalDateTime now, LocalDateTime nightTime) {
		return now.isAfter(nightTime) &&
				TimeUtil.getNowPlusInterval(now, INTERVAL * -1, INTERVAL_UNIT).isBefore(nightTime);
	}

	@Override
	protected Scheduler scheduler() {
		Scheduler scheduler = Scheduler.newFixedDelaySchedule(1, INTERVAL, INTERVAL_UNIT);
		System.out.println("Scheduling night-night task with intermittent delays of " + INTERVAL + " " +
				INTERVAL_UNIT.name().toLowerCase() + ".");
		return scheduler;
	}
}
