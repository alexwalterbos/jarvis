package com.awalterbos.jarvis.server.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(name = "sayings")
@Accessors(chain = true)
public class Saying {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@Setter(AccessLevel.NONE)
	@JsonProperty
	private Long id;

	@Column(name = "format")
	@NotNull
	@JsonProperty(required = true)
	private String format;

	@Column(name = "default_text")
	@JsonProperty
	private String defaultText;

	public String formatDefault() {
		return format(null);
	}

	public String format(Optional<String> text) {
		return String.format(format, text.or(defaultText));
	}
}
