package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonsNetherBrick extends DungeonBase {

    @Override
    public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances,
                            Coord origin) {
        generatestatic(editor, rand, settings, entrances, origin);
        return false;
    }
    public boolean generatestatic(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances,
                                  Coord origin) {        int x = origin.getX();
        int y = origin.getY();
        int z = origin.getZ();
        ITheme theme = settings.getTheme();

        int height = 3;
        int length = 2 + rand.nextInt(3);
        int width = 2 + rand.nextInt(3);

        // Define area coordinates
        Coord lowerBounds = new Coord(x - length - 1, y - 1, z - width - 1);
        Coord upperBounds = new Coord(x + length + 1, y + height + 1, z + width + 1);

        // Create walls
        IBlockFactory walls = theme.getPrimaryWall();
        RectHollow.fill(editor, rand, lowerBounds, upperBounds, walls, false, true);

        // Create floor
        IBlockFactory floor = theme.getPrimaryFloor();
        Coord lowerBoundsCopy = new Coord(lowerBounds.getX(), y - 1, lowerBounds.getZ());
        Coord upperBoundsCopy = new Coord(upperBounds.getX(), y - 1, upperBounds.getZ());
        RectSolid.fill(editor, rand, lowerBoundsCopy, upperBoundsCopy, floor);

        // Create sub-floor with lava
        BlockWeightedRandom subFloor = new BlockWeightedRandom();
        subFloor.addBlock(BlockType.get(BlockType.LAVA_FLOWING), 8);
        subFloor.addBlock(BlockType.get(BlockType.OBSIDIAN), 3);
        RectSolid.fill(editor, rand, new Coord(x - length, y - 5, z - width), new Coord(x + length, y - 2, z + width), subFloor);

        // Create ceiling
        BlockWeightedRandom ceiling = new BlockWeightedRandom();
        ceiling.addBlock(BlockType.get(BlockType.FENCE_NETHER_BRICK), 10);
        ceiling.addBlock(BlockType.get(BlockType.AIR), 5);
        RectSolid.fill(editor, rand, new Coord(x - length, y + height, z - width), new Coord(x + length, y + height, z + width), ceiling);

        // Create chests
        Treasure.createChests(editor, rand, 1, new RectSolid(new Coord(x - length, y, z - width), new Coord(x + length, y, z + width)).get(), Dungeon.getLevel(y));

        // Generate spawners at entrances
        for (Cardinal entrance : entrances) {
            int offsetX = (entrance == Cardinal.WEST) ? -1 : (entrance == Cardinal.EAST) ? 1 : 0;
            int offsetZ = (entrance == Cardinal.NORTH) ? -1 : (entrance == Cardinal.SOUTH) ? 1 : 0;

            Spawner.generate(editor, rand, settings, new Coord(x + offsetX * (length + 1), y + rand.nextInt(2), z + offsetZ * (width + 1)));
        }

        return true;
    }

    public int getSize() {
        return 6;
    }
}
