package greymerk.roguelike.treasure.loot.provider;

import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import greymerk.roguelike.treasure.loot.Enchant;

public class ItemEnchBook extends ItemBase {

    public ItemEnchBook(int weight, int level) {
        super(weight, level);
    }

    @Override
    public ItemStack getLootItem(Random rand, int level) {
        ItemStack book = new ItemStack(Items.book);
        Enchant.enchantItem(rand, book, Enchant.getLevel(rand, level));
        return book;
    }

}
