package org.sheepy.farmer;

public record Skein(String colour, int orderNumber, Weight weight) {

    /**
     * Given a sheep, get usable wool from the sheep.
     *
     * @param sheep the sheep whose wool will be spun into a skein of yarn
     */
    public Skein(Sheep sheep, int orderNumber) {
			this(sheep.name().toLowerCase(), orderNumber, Weight.Worsted);
    }
}
