package greymerk.roguelike.util;

import java.util.Random;

public class WeightedChoice<T> implements IWeighted<T> {

    private final T item;
    private final int weight;

    public WeightedChoice(T toAdd, int weight) {
        item = toAdd;
        this.weight = weight;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }

    @Override
    public T get(Random rand) {
        return item;
    }
}
