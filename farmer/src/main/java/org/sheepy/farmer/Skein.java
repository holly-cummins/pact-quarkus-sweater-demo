package org.sheepy.farmer;

public class Skein {
    private String colour;
    private String weight;

    public Skein() {
    }

    /**
     * Given a sheep, get usable wool from the sheep.
     *
     * @param sheep the sheep whose wool will be spun into a skein of yarn
     */
    public Skein(Sheep sheep) {
        this.colour = sheep.name();
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }


    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

}
