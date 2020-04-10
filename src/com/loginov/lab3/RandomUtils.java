package com.loginov.lab3;

import java.util.Random;

public final class RandomUtils {
    private static final Random random = new Random();

    public static int index(Object[] array){
        return index(array.length);
    }

    public static int index(int length){
        return random.nextInt(length);
    }
}
