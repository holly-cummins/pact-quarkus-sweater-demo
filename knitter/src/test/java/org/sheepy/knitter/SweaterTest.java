package org.sheepy.knitter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SweaterTest {

    @Test
    public void sweaterColourShouldMatchWoolColour() {
        String colour = "mauve";
        Skein skein = new Skein(colour);
        Sweater sweater = new Sweater(skein, 10);
        assertEquals(colour, sweater.colour());
    }

}
