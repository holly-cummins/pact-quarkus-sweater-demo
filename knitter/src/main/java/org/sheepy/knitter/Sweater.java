package org.sheepy.knitter;

public class Sweater {
    private String colour;
    private int size;

    /**
     * Constructor for deserialization
     */
    public Sweater() {
    }

    public Sweater(Skein wool, int size) {
        this.colour = wool.getColour();
        this.size = size;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
