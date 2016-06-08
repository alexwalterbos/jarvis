package com.awalterbos.jarvis.antenna;

public class Waveform {
	private final int[] lengths;
	private final boolean startHigh;

	public Waveform(int[] lengths, boolean startHigh){
		this.lengths = lengths;
		this.startHigh = startHigh;
	}

	public int[] getLengths() {
		return lengths;
	}

	public boolean isStartHigh() {
		return startHigh;
	}
}