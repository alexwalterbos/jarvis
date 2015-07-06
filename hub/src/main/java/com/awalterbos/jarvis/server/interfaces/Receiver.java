package com.awalterbos.jarvis.server.interfaces;

import com.awalterbos.antenna.Antenna;

public interface Receiver {

	void activate(Antenna antenna);

	void deactivate(Antenna antenna);

}
