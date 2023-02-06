package org.sheepy.coldperson;

public class Sweater {
    private String colour;
    private int size;
    private int orderNumber;

    /**
     * Constructor for deserialization
     */
    public Sweater() {
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

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
}
