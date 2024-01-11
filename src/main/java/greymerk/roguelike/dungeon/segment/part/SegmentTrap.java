package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.loot.Potion;
import greymerk.roguelike.treasure.loot.PotionType;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.redstone.Dispenser;
import greymerk.roguelike.worldgen.redstone.Torch;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentTrap extends SegmentBase {

    @Override
    protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme,
                           Coord origin) {
        genWallstatic(editor, rand, level, dir, theme, origin);
    }
    private static void genWallstatic(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme,
                                      Coord origin) {

        MetaBlock plate = BlockType.get(BlockType.PRESSURE_PLATE_STONE);
        MetaBlock wire = BlockType.get(BlockType.REDSTONE_WIRE);
        MetaBlock vine = BlockType.get(BlockType.VINE);
        MetaBlock air = BlockType.get(BlockType.AIR);
        IStair stair = theme.getPrimaryStair();
        IBlockFactory wall = theme.getPrimaryWall();

        Cardinal[] orth = Cardinal.orthogonal(dir);

        Coord cursor;
        Coord start;
        Coord end;

        start = new Coord(origin);
        start.add(dir, 2);
        end = new Coord(start);
        start.add(orth[0]);
        end.add(orth[1]);
        end.add(Cardinal.UP, 2);
        RectSolid.fill(editor, rand, start, end, vine);
        start.add(dir);
        end.add(dir);
        RectSolid.fill(editor, rand, start, end, wall);

        cursor = new Coord(origin);
        cursor.add(Cardinal.UP);
        cursor.add(dir, 3);
        air.set(editor, cursor);

        for (Cardinal side : orth) {
            cursor = new Coord(origin);
            cursor.add(dir, 2);
            cursor.add(side);
            stair.setOrientation(Cardinal.reverse(side), false).set(editor, cursor);
            cursor.add(Cardinal.UP, 2);
            stair.setOrientation(Cardinal.reverse(side), true).set(editor, cursor);
        }

        start = new Coord(origin);
        end = new Coord(start);
        start.add(dir);
        end.add(Cardinal.reverse(dir));

        RectSolid.fill(editor, rand, start, end, plate);

        end.add(Cardinal.DOWN, 2);
        start = new Coord(end);
        start.add(dir, 3);

        RectSolid.fill(editor, rand, start, end, wire);

        cursor = new Coord(start);
        cursor.add(dir, 2);
        Torch.generate(editor, Torch.REDSTONE, dir, cursor);
        cursor.add(Cardinal.UP, 2);
        Torch.generate(editor, Torch.REDSTONE, Cardinal.UP, cursor);
        cursor.add(Cardinal.UP);
        Dispenser.generate(editor, Cardinal.reverse(dir), cursor);

        for (int i = 0; i < 5; i++) {
            Dispenser.add(editor, cursor, rand.nextInt(9), new ItemStack(Items.arrow, rand.nextInt(4) + 1));
        }

        Dispenser.add(editor, cursor, 5, getPayload(rand));
    }

    private static ItemStack getPayload(Random rand) {

        switch (rand.nextInt(3)) {
            case 0:
                return BlockType.getItem(BlockType.TNT);
            case 1:
                return Potion.getSpecific(rand, PotionType.SPLASH, Potion.POISON, false, false);
            case 2:
                return Potion.getSpecific(rand, PotionType.SPLASH, Potion.HARM, false, false);
            default:
                return BlockType.getItem(BlockType.TNT);
        }
    }
}
