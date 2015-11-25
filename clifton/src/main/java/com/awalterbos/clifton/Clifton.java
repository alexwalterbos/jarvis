package com.awalterbos.clifton;

import java.util.Arrays;
import java.util.List;

import com.awalterbos.antenna.Antenna;
import com.awalterbos.protocol.ProtocolOne;

public class Clifton {
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

		try {
			new Antenna().send(new ProtocolOne(), code);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("[Sender offline]");
		System.exit(0);
	}
}
