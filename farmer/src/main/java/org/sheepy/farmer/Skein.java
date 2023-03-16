package org.sheepy.farmer;

public record Skein(String colour, int orderNumber, Weight weight) {

    /**
     * Given a wookieColor, get usable wool from the wookieColor.
     *
     * @param wookieColor the wookieColor whose wool will be spun into a skein of yarn
     */
    public Skein(WookieColor wookieColor, int orderNumber) {
			this(wookieColor.name().toLowerCase(), orderNumber, Weight.Worsted);
    }
}
