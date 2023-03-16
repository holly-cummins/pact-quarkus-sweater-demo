package org.sheepy.knitter;

public record Sweater(String colour, int orderNumber, Style style) {
	public Sweater(Skein wool, int orderNumber) {
		this(wool.colour(), orderNumber, Style.LongSleeved);
	}
}
