package com.awalterbos.jarvis.server.interfaces;

import com.awalterbos.antenna.Antenna;

public interface Receiver {

	public void activate(Antenna antenna);

	public void deactivate(Antenna antenna);

}
