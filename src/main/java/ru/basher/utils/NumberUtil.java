package ru.basher.utils;

import java.util.Random;

public class NumberUtil {
    private static final Random random = new Random();

    public static int getRandomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static double getRandomDouble(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    public static boolean isInt(String number) {
        try {
            Integer.parseInt(number);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isLong(String number) {
        try {
            Long.parseLong(number);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String number) {
        try {
            Double.parseDouble(number);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isFloat(String number) {
        try {
            Float.parseFloat(number);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public static int square(int number) {
        return number * number;
    }

    public static long square(long number) {
        return number * number;
    }

    public static double square(double number) {
        return number * number;
    }

    public static float square(float number) {
        return number * number;
    }

    public static double sqrt(double number) {
        return Math.sqrt(number);
    }
}
