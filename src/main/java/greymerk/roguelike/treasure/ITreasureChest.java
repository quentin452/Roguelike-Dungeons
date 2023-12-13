package greymerk.roguelike.treasure;

import java.util.Random;

import net.minecraft.item.ItemStack;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public interface ITreasureChest {

    ITreasureChest generate(IWorldEditor editor, Random rand, Coord pos, int level, boolean trapped);

    boolean setSlot(int slot, ItemStack item);

    boolean setRandomEmptySlot(ItemStack item);

    boolean isEmptySlot(int slot);

    Treasure getType();

    int getSize();

    int getLevel();
}
