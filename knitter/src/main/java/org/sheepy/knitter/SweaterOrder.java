package org.sheepy.knitter;

public class SweaterOrder {
    private String colour;
    private int orderNumber;

    public SweaterOrder(String colour, int orderNumber) {
        this.colour = colour;
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
