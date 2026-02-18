package ru.netology.web.utils;

public class AmountGenerator {
    private static int currentBalance;

    public static int generateValidAmount(int currentBalance) {
        if (currentBalance <= 0) {
            throw new IllegalArgumentException("Баланс не может быть нулевым или отрицательным");
        }
        return (int) (Math.random() * currentBalance) + 1;
    }

    public static int generateInvalidAmount(int currentBalance) {
        int excess = (int) (Math.random() * 100) + 1;
        return currentBalance + excess;
    }

}
