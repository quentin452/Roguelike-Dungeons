package greymerk.roguelike.treasure.loot;

import net.minecraft.item.ItemStack;

import greymerk.roguelike.util.IWeighted;

public interface ILoot {

    public IWeighted<ItemStack> get(Loot type, int level);

}
