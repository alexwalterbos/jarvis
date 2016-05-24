package com.awalterbos.jarvis.hub.interfaces;

public interface Radio<T> {

	Integer getSignalOn();
	Integer getSignalOff();

	T setSignalOn(Integer signalOn);
	T setSignalOff(Integer signalOff);

}
