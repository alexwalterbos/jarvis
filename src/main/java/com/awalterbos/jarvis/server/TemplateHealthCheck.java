package com.awalterbos.jarvis.server;

import com.codahale.metrics.health.HealthCheck;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class TemplateHealthCheck extends HealthCheck {

	private final String template;

	@Override
	@SneakyThrows
	protected Result check() {
		final String saying = String.format(template, "Test");
		if (!saying.contains("Test")) {
			return Result.unhealthy("Template doesn't include a name.");
		}
		return Result.healthy();
	}
}
