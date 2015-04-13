package com.awalterbos.antenna;

import static com.pi4j.wiringpi.Gpio.digitalWrite;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.awalterbos.antenna.protocol.Protocol;
import com.awalterbos.antenna.protocol.ProtocolOne;
import com.awalterbos.antenna.protocol.ProtocolTwo;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;

public class Antenna {

	private static final int REPEAT_SEND = 10;
	private static final int CODEWORD_LENGTH = 24;
	private Protocol protocol;
	private Pin transmitterPin;

	public Antenna() {
		setProtocol(1);
		transmitterPin = RaspiPin.GPIO_00;

		GpioFactory.getDefaultProvider().export(transmitterPin, PinMode.DIGITAL_OUTPUT, PinState.LOW);
	}

	public void setProtocol(int prot) {
		if (prot == 1) {
			protocol = new ProtocolOne(this);
		}
		else if (prot == 2) {
			protocol = new ProtocolTwo(this);
		}
	}

	public void send(int code) {
		char[] binaryChars = dec2binWzerofill(code, CODEWORD_LENGTH);
		System.out.println(String.format("Sending '%d' as '%s' ", code, String.valueOf(binaryChars)));
		send(binaryChars);
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

	public void send(char[] codeWord) {
		try {
			for (int i = 0; i < REPEAT_SEND; i++) {
				for (char aCodeWord : codeWord) {
					switch (aCodeWord) {
						case '0':
							protocol.send0();
							break;
						case '1':
							protocol.send1();
							break;
					}
				}

				protocol.sendSync();
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void transmit(int highPulseLength, int lowPulseLength) throws InterruptedException {
		// TODO disable receive

		digitalWrite(transmitterPin.getAddress(), Gpio.HIGH);
		delayMicro(highPulseLength);
		digitalWrite(transmitterPin.getAddress(), Gpio.LOW);
		delayMicro(lowPulseLength);

		// TODO enable receive (in finally block)
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
