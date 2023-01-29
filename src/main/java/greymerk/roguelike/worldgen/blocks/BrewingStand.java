package greymerk.roguelike.worldgen.blocks;

import net.minecraft.init.Blocks;

import greymerk.roguelike.worldgen.MetaBlock;

public class BrewingStand {

    public static MetaBlock get() {
        return new MetaBlock(Blocks.brewing_stand);
    }

}
