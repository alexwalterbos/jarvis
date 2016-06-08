package com.awalterbos.jarvis.protocol;

import com.awalterbos.jarvis.antenna.Protocol;
import com.awalterbos.jarvis.antenna.Waveform;

public class ProtocolOne implements Protocol {

	private static final int PULSE = 350; // microseconds = 1/1,000,000 second

	/**
	 * <pre>
	 * '0' Bit
	 *            _
	 * Waveform: | |___
	 * </pre>
	 */
	private static final Waveform SIGNAL_0 = new Waveform(new int[] { PULSE, 3 * PULSE }, true);
	/**
	 * <pre>
	 * '1' Bit
	 *            ___
	 * Waveform: |   |_
	 * </pre>
	 */
	private static final Waveform SIGNAL_1 = new Waveform(new int[] { 3 * PULSE, PULSE }, true);
	/**
	 * <pre>
	 * Sync waveform
	 *            _
	 * Waveform: | |_______________________________
	 * </pre>
	 */
	private static final Waveform SYNC = new Waveform(new int[] { PULSE, 31 * PULSE }, true);

	public Waveform get0() {
		return SIGNAL_0;
	}

	public Waveform get1() {
		return SIGNAL_1;
	}

	public Waveform getSync() {
		return SYNC;
	}
}
