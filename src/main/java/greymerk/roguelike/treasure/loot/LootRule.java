package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.item.ItemStack;

import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.util.IWeighted;

public class LootRule {

    private final Treasure type;
    private final IWeighted<ItemStack> item;
    int level;
    private final boolean toEach;
    int amount;

    public LootRule(Treasure type, IWeighted<ItemStack> item, int level, boolean toEach, int amount) {
        this.type = type;
        this.item = item;
        this.level = level;
        this.toEach = toEach;
        this.amount = amount;
    }

    public void process(Random rand, ILoot loot, TreasureManager treasure) {
        if (toEach && type != null) treasure.addItemToAll(rand, type, level, item, amount);
        if (toEach && type == null) treasure.addItemToAll(rand, level, item, amount);
        if (!toEach && type != null) treasure.addItem(rand, type, level, item, amount);
        if (!toEach && type == null) treasure.addItem(rand, level, item, amount);
    }
}
