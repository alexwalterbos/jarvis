package com.awalterbos.antenna.protocol;

import com.awalterbos.antenna.Antenna;

public class ProtocolOne implements Protocol {

	private static final int PULSE_LENGTH_MICROSECONDS = 350; // microseconds = 1/1,000,000 second
	private final Antenna antenna;

	public ProtocolOne(Antenna antenna) {
		this.antenna = antenna;
	}

	/**
	 * <pre>
	 * Sends a "0" Bit
	 *            _
	 * Waveform: | |___
	 * </pre>
	 */
	public void send0() throws InterruptedException {
		int highPulseLength = PULSE_LENGTH_MICROSECONDS;
		int lowPulseLength = 3 * PULSE_LENGTH_MICROSECONDS;
		antenna.transmit(highPulseLength, lowPulseLength);
	}

	/**
	 * <pre>
	 * Sends a "1" Bit
	 *            ___
	 * Waveform: |   |_
	 * </pre>
	 */
	public void send1() throws InterruptedException {
		int highPulseLength = 3 * PULSE_LENGTH_MICROSECONDS;
		int lowPulseLength = PULSE_LENGTH_MICROSECONDS;
		antenna.transmit(highPulseLength, lowPulseLength);
	}

	/**
	 * <pre>
	 * Sends a "sync" Bit
	 *            _
	 * Waveform: | |_______________________________
	 * </pre>
	 */
	public void sendSync() throws InterruptedException {
		int highPulseLength = PULSE_LENGTH_MICROSECONDS;
		int lowPulseLength = 31 * PULSE_LENGTH_MICROSECONDS;
		antenna.transmit(highPulseLength, lowPulseLength);
	}
}
