package org.sheepy.farmer;

public class Skein {
    private String colour;
    private Weight weight;
    private int orderNumber;

    public Skein() {
    }

    /**
     * Given a sheep, get usable wool from the sheep.
     *
     * @param sheep the sheep whose wool will be spun into a skein of yarn
     */
    public Skein(Sheep sheep, int orderNumber) {
        this.colour = sheep.name();
        this.orderNumber = orderNumber;
        this.weight = Weight.Worsted;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }


    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
}
