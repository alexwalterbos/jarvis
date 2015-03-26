package com.awalterbos.jarvis.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
public class Saying {
	@JsonProperty
	private long id;

	@JsonProperty
	@Length(max = 3)
	private String content;

	public Saying() {
		// Jackson deserialization
	}
}
