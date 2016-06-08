package com.awalterbos.jarvis.antenna;

import static com.pi4j.wiringpi.Gpio.HIGH;
import static com.pi4j.wiringpi.Gpio.LOW;
import static com.pi4j.wiringpi.Gpio.digitalWrite;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class Antenna {

	private static final int REPEAT_SEND = 10;
	private static final int CODEWORD_LENGTH = 24;
	private static final Pin transmitterPin = RaspiPin.GPIO_00; // TODO make settable?
	private final boolean test;

	public Antenna() {
		boolean test = false;
		try {
			GpioFactory.getDefaultProvider().export(transmitterPin, PinMode.DIGITAL_OUTPUT, PinState.LOW);
		}
		catch (NoClassDefFoundError | UnsatisfiedLinkError e) {
			System.out.println("Started in test mode due to missing low-level dependencies for Antenna");
			// Assume test mode
			test = true;
		}

		this.test = test;
	}

	public void send(Protocol protocol, int code) throws InterruptedException {
		char[] binaryChars = dec2binWzerofill(code, CODEWORD_LENGTH);
		System.out.println(String.format("Sending '%d' as '%s' ", code, String.valueOf(binaryChars)));
		send(protocol, binaryChars);
	}

	private char[] dec2binWzerofill(int code, int bitLength) {
		int i = 0;
		char[] bin = new char[64];

		// Use the latter half of the bin array to build the (max 32 bit long because int) binary representation
		while (code > 0) {
			bin[32 + i++] = (code & 1) > 0 ? '1' : '0';
			code = code >> 1;
		}

		// Copy it to the first half, but fill with zeros
		for (int j = 0; j < bitLength; j++) {
			if (j >= bitLength - i) {
				bin[j] = bin[31 + i - (j - (bitLength - i))];
			}
			else {
				bin[j] = '0';
			}
		}

		return Arrays.copyOfRange(bin, 0, bitLength);
	}

	public void send(Protocol protocol, char[] codeWord) throws InterruptedException {
		for (int i = 0; i < REPEAT_SEND; i++) {
			for (char aCodeWord : codeWord) {
				switch (aCodeWord) {
					case '0':
						transmit(protocol.get0());
						break;
					case '1':
						transmit(protocol.get1());
						break;
				}
			}

			transmit(protocol.getSync());
		}
	}

	private void transmit(Waveform waveform) throws InterruptedException {
		if (test) {
			return;
		}
		int current = waveform.isStartHigh() ? HIGH : LOW;

		for (int i = 0; i < waveform.getLengths().length; i++) {
			digitalWrite(transmitterPin.getAddress(), current);
			delayMicro(waveform.getLengths()[i]);
			current = current == HIGH ? LOW : HIGH;
		}
	}

	private void delayMicro(int durationInMicrosecs) {
		long start = System.nanoTime();
		long durNano = TimeUnit.MICROSECONDS.toNanos(durationInMicrosecs);
		//noinspection StatementWithEmptyBody
		while(System.nanoTime() < start + durNano) {
			// WAIT FOR IT
		}
	}
}
