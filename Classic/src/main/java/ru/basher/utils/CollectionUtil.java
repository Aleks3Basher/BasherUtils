package ru.basher.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class CollectionUtil {

    public static @NotNull <T> T getRandomFromList(@NotNull List<T> list, @Nullable Function<T, Integer> getRate) {
        if (getRate == null) return list.get(NumberUtil.getRandomInt(0, list.size() - 1));

        int totalWeight = 0;
        for (T item : list) {
            int weight = getRate.apply(item);
            if (weight < 0) throw new IllegalArgumentException("Weight cannot be negative: " + weight);
            totalWeight += weight;
        }

        if (totalWeight == 0) return list.get(0);

        int randomWeight = NumberUtil.getRandomInt(0, totalWeight - 1);
        int currentWeight = 0;

        for (T item : list) {
            currentWeight += getRate.apply(item);
            if (randomWeight < currentWeight) {
                return item;
            }
        }

        return list.get(NumberUtil.getRandomInt(0, list.size() - 1));
    }

}
