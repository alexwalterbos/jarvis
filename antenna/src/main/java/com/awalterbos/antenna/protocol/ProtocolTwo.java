package com.awalterbos.antenna.protocol;

import com.awalterbos.antenna.Antenna;

public class ProtocolTwo implements Protocol {

	private static final int PULSE_LENGTH_MICROSECONDS = 650;
	private final Antenna antenna;

	public ProtocolTwo(Antenna antenna) {
		this.antenna = antenna;
	}

	/**
	 * <pre>
	 * Sends a "0" Bit
	 *            _
	 * Waveform: | |__
	 * </pre>
	 */
	public void send0() throws InterruptedException {
		int highPulseLength = PULSE_LENGTH_MICROSECONDS;
		int lowPulseLength = 2 * PULSE_LENGTH_MICROSECONDS;
		antenna.transmit(highPulseLength, lowPulseLength);
	}

	/**
	 * <pre>
	 * Sends a "1" Bit
	 *            __
	 * Waveform: |  |_
	 * </pre>
	 */
	public void send1() throws InterruptedException {
		int highPulseLength = 2 * PULSE_LENGTH_MICROSECONDS;
		int lowPulseLength = PULSE_LENGTH_MICROSECONDS;
		antenna.transmit(highPulseLength, lowPulseLength);
	}

	/**
	 * <pre>
	 * Sends a "sync" Bit
	 *            _
	 * Waveform: | |__________
	 * </pre>
	 */
	public void sendSync() throws InterruptedException {
		int highPulseLength = PULSE_LENGTH_MICROSECONDS;
		int lowPulseLength = 10 * PULSE_LENGTH_MICROSECONDS;
		antenna.transmit(highPulseLength, lowPulseLength);
	}
}
