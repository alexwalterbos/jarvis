package com.awalterbos.antenna.test;

import com.awalterbos.antenna.Antenna;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class JarvisTestModule extends AbstractModule {

	@Override
	protected void configure() {
	}

	@Provides
	public Antenna antenna() {
		return new MockAntenna();
	}
}
