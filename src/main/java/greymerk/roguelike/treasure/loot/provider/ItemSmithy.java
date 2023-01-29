package greymerk.roguelike.treasure.loot.provider;

import java.util.Random;

import net.minecraft.item.ItemStack;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;

public class ItemSmithy extends ItemBase {

    public ItemSmithy(int weight, int level) {
        super(weight, level);
    }

    @Override
    public ItemStack getLootItem(Random rand, int level) {
        return ItemSpecialty.getRandomItem(Equipment.SWORD, rand, Quality.IRON);
    }
}
