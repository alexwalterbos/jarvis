package com.awalterbos.antenna.test;

import java.util.Arrays;
import java.util.List;

import com.awalterbos.antenna.Antenna;

public class Sender {

	public static void main(String[] args) {
		System.out.println("[Sender online]");

		List<String> argList = Arrays.asList(args);
		int codeIndex = argList.indexOf("--code");
		Integer code;

		if (codeIndex >= 0) {
			code = Integer.parseInt(argList.get(codeIndex + 1));
		}
		else {
			System.out.println("Provide a code to send.");
			return;
		}

		new Antenna().send(code);
		System.out.println("[Sender offline]");
		System.exit(0);
	}
}
