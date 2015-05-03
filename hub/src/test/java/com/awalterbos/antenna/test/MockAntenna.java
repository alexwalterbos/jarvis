package com.awalterbos.antenna.test;

import com.awalterbos.antenna.Antenna;

class MockAntenna extends Antenna {

	@Override
	public void init(int i) {
		//shhhh
	}

	@Override
	public void setProtocol(int prot) {
		System.out.println("Protocol set to v" + prot);
	}

	@Override
	public void send(int code) {
		System.out.println("MOCK Sent " + code);
	}

	@Override
	public void transmit(int highPulseLength, int lowPulseLength) throws InterruptedException {
	}
}
