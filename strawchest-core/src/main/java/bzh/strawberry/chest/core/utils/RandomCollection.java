package bzh.strawberry.chest.core.utils;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

/*
 * This file RandomCollection is part of a project StrawChest.strawchest-core.
 * It was created on 07/07/2020 01:10 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class RandomCollection<ItemStack> {

    private final NavigableMap<Double, ItemStack> map = new TreeMap<>();
    private final Random random;
    private double total = 0;

    public RandomCollection() {
        this(new Random());
    }

    public RandomCollection(Random random) {
        this.random = random;
    }

    public RandomCollection<ItemStack> add(double weight, ItemStack result) {
        if (weight <= 0) return this;
        total += weight;
        map.put(total, result);
        return this;
    }

    public ItemStack random() {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }
}