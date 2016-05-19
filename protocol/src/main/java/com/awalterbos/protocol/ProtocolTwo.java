package com.awalterbos.protocol;

import com.awalterbos.antenna.Protocol;
import com.awalterbos.antenna.Waveform;

public class ProtocolTwo implements Protocol {

	private static final int PULSE = 650; // microseconds = 1/1,000,000 second

	/**
	 * <pre>
	 * '0' Bit
	 *            _
	 * Waveform: | |__
	 * </pre>
	 */
	private static final Waveform SIGNAL_0 = new Waveform(new int[] { PULSE, 2 * PULSE }, true);
	/**
	 * <pre>
	 * '1' Bit
	 *            __
	 * Waveform: |  |_
	 * </pre>
	 */
	private static final Waveform SIGNAL_1 = new Waveform(new int[] { 2 * PULSE, PULSE }, true);
	/**
	 * <pre>
	 * Sync waveform
	 *            _
	 * Waveform: | |__________
	 * </pre>
	 */
	private static final Waveform SYNC = new Waveform(new int[] { PULSE, 10 * PULSE }, true);

	@Override
	public Waveform get0() {
		return SIGNAL_0;
	}

	@Override
	public Waveform get1() {
		return SIGNAL_1;
	}

	@Override
	public Waveform getSync() {
		return SYNC;
	}
}
