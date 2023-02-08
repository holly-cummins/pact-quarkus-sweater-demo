package org.sheepy.knitter;

public class Sweater {
    private String colour;
    private int orderNumber;

    /**
     * Constructor for deserialization
     */
    public Sweater() {
    }

    public Sweater(Skein wool, int orderNumber) {
        this.colour = wool.getColour();
        this.orderNumber = orderNumber;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
}
