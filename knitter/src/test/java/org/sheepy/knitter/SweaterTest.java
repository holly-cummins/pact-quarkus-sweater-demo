package org.sheepy.knitter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SweaterTest {

    @Test
    public void sweaterColourShouldMatchWoolColour() {
        String colour = "mauve";
        Skein skein = new Skein(colour);
        Sweater sweater = new Sweater(skein, 10);
        assertEquals(colour, sweater.getColour());
    }

}
