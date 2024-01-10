package greymerk.roguelike.treasure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public enum Treasure {

    ARMOUR,
    WEAPONS,
    BLOCKS,
    ENCHANTING,
    FOOD,
    ORE,
    POTIONS,
    STARTER,
    TOOLS,
    SUPPLIES,
    SMITH,
    MUSIC,
    REWARD,
    EMPTY;

    private static final List<Treasure> common = new ArrayList<>(Arrays.asList(TOOLS, ARMOUR, WEAPONS));

    public static ITreasureChest generate(IWorldEditor editor, Random rand, Coord pos, int level, boolean trapped) {

        Treasure type = getChestType(rand, level);
        ITreasureChest chest = new TreasureChest(type);

        return chest.generate(editor, rand, pos, level, trapped);
    }

    public static ITreasureChest generate(IWorldEditor editor, Random rand, Coord pos, Treasure type, int level) {
        return generate(editor, rand, pos, type, level, false);
    }

    public static ITreasureChest generate(IWorldEditor editor, Random rand, Coord pos, Treasure type, int level,
            boolean trapped) {
        ITreasureChest chest = new TreasureChest(type);
        chest.generate(editor, rand, pos, level, trapped);
        return chest;
    }

    public static List<ITreasureChest> generate(IWorldEditor editor, Random rand, List<Coord> space, Treasure type,
            int level) {
        return createChests(editor, rand, 1, space, new ArrayList<>(Collections.singletonList(type)), level);
    }

    public static List<ITreasureChest> createChests(IWorldEditor editor, Random rand, int numChests, List<Coord> space,
            int level) {
        return createChests(editor, rand, numChests, space, level, false);
    }

    public static List<ITreasureChest> createChests(IWorldEditor editor, Random rand, int numChests, List<Coord> space,
            int level, boolean trapped) {

        List<ITreasureChest> chests = new ArrayList<>();

        Collections.shuffle(space, rand);

        int count = 0;

        for (Coord block : space) {

            if (count == numChests) {
                break;
            }

            if (isValidChestSpace(editor, block)) {
                ITreasureChest chest = generate(editor, rand, block, getChestType(rand, level), level);
                chests.add(chest);
                count++;
            }
        }

        return chests;
    }

    public static List<ITreasureChest> createChests(IWorldEditor editor, Random rand, int numChests, List<Coord> space,
            List<Treasure> types, int level) {

        List<ITreasureChest> chests = new ArrayList<>();

        Collections.shuffle(space, rand);

        int count = 0;

        for (Coord block : space) {

            if (count == numChests) {
                break;
            }

            if (isValidChestSpace(editor, block)) {
                ITreasureChest chest = generate(editor, rand, block, types.get(rand.nextInt(types.size())), level);
                chests.add(chest);
                count++;
            }
        }

        return chests;
    }

    private static Treasure getChestType(Random rand, int level) {
        return common.get(rand.nextInt(common.size()));
    }

    public static boolean isValidChestSpace(IWorldEditor editor, Coord pos) {

        if (!editor.isAirBlock(pos)) {
            return false;
        }

        Coord cursor;
        cursor = new Coord(pos);
        cursor.add(Cardinal.DOWN);

        if (!editor.getBlock(cursor).getBlock().getMaterial().isSolid()) return false;

        for (Cardinal dir : Cardinal.directions) {
            cursor = new Coord(pos);
            cursor.add(dir);
            if (editor.getBlock(cursor).getBlock() == Blocks.chest) return false;
        }

        return true;
    }
}
