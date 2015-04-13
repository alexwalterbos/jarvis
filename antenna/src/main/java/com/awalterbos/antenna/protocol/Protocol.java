package com.awalterbos.antenna.protocol;

public interface Protocol {
	void send0() throws InterruptedException;
	void send1() throws InterruptedException;
	void sendSync() throws InterruptedException;
}
