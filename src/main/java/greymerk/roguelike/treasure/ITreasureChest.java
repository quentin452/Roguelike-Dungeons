package greymerk.roguelike.treasure;

import java.util.Random;

import net.minecraft.item.ItemStack;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public interface ITreasureChest {

    public ITreasureChest generate(IWorldEditor editor, Random rand, Coord pos, int level, boolean trapped);

    public boolean setSlot(int slot, ItemStack item);

    public boolean setRandomEmptySlot(ItemStack item);

    public boolean isEmptySlot(int slot);

    public Treasure getType();

    public int getSize();

    public int getLevel();
}
