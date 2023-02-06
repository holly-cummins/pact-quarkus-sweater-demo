package org.sheepy.coldperson;

import javax.inject.Singleton;

@Singleton
public class OrderNumber {
    int number = 0;

    public int get() {
        number++;
        return number;
    }
}
