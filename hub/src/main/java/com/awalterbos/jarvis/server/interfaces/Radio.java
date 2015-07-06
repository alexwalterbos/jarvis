package com.awalterbos.jarvis.server.interfaces;

public interface Radio<T> {

	Integer getSignalOn();
	Integer getSignalOff();

	T setSignalOn(Integer signalOn);
	T setSignalOff(Integer signalOff);

}
