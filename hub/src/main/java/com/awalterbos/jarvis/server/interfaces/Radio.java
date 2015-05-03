package com.awalterbos.jarvis.server.interfaces;

public interface Radio<T> {

	int getSignalOn();
	int getSignalOff();

	T setSignalOn(int signalOn);
	T setSignalOff(int signalOff);

}
