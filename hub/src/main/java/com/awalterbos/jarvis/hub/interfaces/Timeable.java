package com.awalterbos.jarvis.hub.interfaces;

import java.time.LocalTime;

public interface Timeable {

	public LocalTime getStartTime();

	public void setStartTime(LocalTime time);

	public LocalTime getEndTime();

	public void setEndTime(LocalTime time);

}
