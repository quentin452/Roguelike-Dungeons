package greymerk.roguelike.worldgen.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import greymerk.roguelike.worldgen.MetaBlock;

public enum ColorBlock {

    CLAY,
    WOOL,
    CARPET,
    GLASS,
    PANE;

    public static MetaBlock get(ColorBlock type, DyeColor color) {
        Block b = getBlock(type);
        int c = DyeColor.get(color);
        return new MetaBlock(b, c);
    }

    public static MetaBlock get(ColorBlock type, Random rand) {
        DyeColor[] colors = DyeColor.values();
        DyeColor choice = colors[rand.nextInt(colors.length)];
        return get(type, choice);
    }

    public static Block getBlock(ColorBlock type) {
        switch (type) {
            case CLAY:
                return Blocks.stained_hardened_clay;
            case WOOL:
                return Blocks.wool;
            case CARPET:
                return Blocks.carpet;
            case GLASS:
                return Blocks.stained_glass;
            case PANE:
                return Blocks.stained_glass_pane;
            default:
                return Blocks.wool;
        }
    }

}
