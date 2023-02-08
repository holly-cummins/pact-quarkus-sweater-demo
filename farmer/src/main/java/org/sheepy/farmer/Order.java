package org.sheepy.farmer;

public class Order {
    private String colour;
    private int orderNumber;

    public Order() {
    }

    public Order(String colour, int orderNumber) {
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
