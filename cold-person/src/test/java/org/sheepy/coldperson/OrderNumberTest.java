package org.sheepy.coldperson;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class OrderNumberTest {
    @Inject
    OrderNumber orderNumber;

    @Test
    public void orderNumberGivesANumber() {
        int number = orderNumber.get();
        assertTrue(number > 0);
    }

    @Test
    public void orderNumberIncrements() {
        int firstNumber = orderNumber.get();
        int secondNumber = orderNumber.get();

        assertEquals(firstNumber + 1, secondNumber);
    }
}
