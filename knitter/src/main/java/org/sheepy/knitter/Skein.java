package org.sheepy.knitter;

public class Skein {
    private String colour;
    private String weight;

    public Skein() {
    }

    public Skein(String colour) {
        this.colour = colour;
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
