package org.sheepy.farmer;

/**
 * The different colors of Wookies we can have
 */
public enum WookieColor {
	// The different colours of Wookies we can have
	BROWN, BLACK, GRAY, WHITE;

	@Override
	public String toString() {
		return name().toLowerCase();
	}
}
