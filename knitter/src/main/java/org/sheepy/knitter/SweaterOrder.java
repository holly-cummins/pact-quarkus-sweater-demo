package org.sheepy.knitter;

public class SweaterOrder {
    private String colour;
    private int size;

    public SweaterOrder(String colour, int size) {
        this.colour = colour;
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
