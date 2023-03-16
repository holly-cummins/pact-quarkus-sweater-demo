package org.sheepy.knitter;

public record Skein(String colour, String weight) {
	public Skein(String colour) {
		this(colour, null);
	}
}
