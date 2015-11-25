package com.awalterbos.antenna;

public interface Protocol {
	Waveform get0();
	Waveform get1();
	Waveform getSync();
}
