package greymerk.roguelike.dungeon.segment.alcove;

import java.util.Random;

import greymerk.roguelike.dungeon.segment.IAlcove;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Door;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class PrisonCell implements IAlcove {

    private static final int RECESSED = 5;

    @Override
    public void generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal dir) {

        ITheme theme = settings.getTheme();
        IBlockFactory walls = theme.getPrimaryWall();
        MetaBlock air = BlockType.get(BlockType.AIR);
        MetaBlock plate = BlockType.get(BlockType.PRESSURE_PLATE_STONE);

        Coord start = new Coord(origin);
        start.add(dir, RECESSED);
        Coord end = new Coord(start);
        start.add(-2, -1, -2);
        end.add(2, 3, 2);
        RectHollow.fill(editor, rand, start, end, walls);

        start = new Coord(origin);
        end = new Coord(origin);
        end.add(dir, RECESSED);
        end.add(Cardinal.UP);
        RectSolid.fill(editor, rand, start, end, air);

        Coord cursor = new Coord(origin);
        cursor.add(dir, RECESSED - 1);
        plate.set(editor, cursor);
        cursor.add(Cardinal.DOWN);
        if (rand.nextBoolean()) Spawner.generate(editor, rand, settings, cursor, Spawner.ZOMBIE);

        cursor = new Coord(origin);
        cursor.add(dir, 3);
        Door.generate(editor, cursor, Cardinal.reverse(dir), Door.IRON);

    }

    @Override
    public boolean isValidLocation(IWorldEditor editor, Coord origin, Cardinal dir) {

        Coord centre = new Coord(origin);
        centre.add(dir, RECESSED);
        int x = centre.getX();
        int y = centre.getY();
        int z = centre.getZ();

        for (Coord c : new RectSolid(new Coord(x - 2, y, z - 2), new Coord(x + 2, y, z + 2))) {
            if (editor.isAirBlock(c)) return false;
        }

        return true;
    }
}
