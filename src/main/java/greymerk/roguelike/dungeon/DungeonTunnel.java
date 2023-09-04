package greymerk.roguelike.dungeon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.segment.ISegment;
import greymerk.roguelike.dungeon.segment.ISegmentGenerator;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonTunnel implements Iterable<Coord> {

    private Coord start;
    private Coord end;
    private Cardinal dir;
    private List<ISegment> segments;
    private List<Coord> tunnel;

    public DungeonTunnel(IWorldEditor editor, Coord start, Coord end, Cardinal dir) {
        this.start = start;
        this.end = end;
        this.tunnel = new RectSolid(start, end).get();
        this.dir = dir;
        this.segments = new ArrayList<>();
    }

    @Override
    public Iterator<Coord> iterator() {
        return tunnel.iterator();
    }

    public void construct(IWorldEditor editor, Random rand, LevelSettings settings) {

        MetaBlock air = BlockType.get(BlockType.AIR);

        IBlockFactory wallBlocks = settings.getTheme().getPrimaryWall();
        IBlockFactory floor = settings.getTheme().getPrimaryFloor();
        BlockJumble bridgeBlocks = new BlockJumble();
        bridgeBlocks.addBlock(floor);
        bridgeBlocks.addBlock(air);

        Coord s = new Coord(this.start);
        s.add(Cardinal.NORTH);
        s.add(Cardinal.EAST);
        Coord e = new Coord(this.end);
        e.add(Cardinal.SOUTH);
        e.add(Cardinal.WEST);
        e.add(Cardinal.UP, 2);
        RectSolid.fill(editor, rand, s, e, air);

        s.add(Cardinal.NORTH);
        s.add(Cardinal.EAST);
        s.add(Cardinal.DOWN);
        e.add(Cardinal.SOUTH);
        e.add(Cardinal.WEST);
        e.add(Cardinal.UP);
        RectHollow.fill(editor, rand, s, e, wallBlocks, false, true);

        s = new Coord(this.start);
        s.add(Cardinal.NORTH);
        s.add(Cardinal.EAST);
        s.add(Cardinal.DOWN);
        e = new Coord(this.end);
        e.add(Cardinal.SOUTH);
        e.add(Cardinal.WEST);
        e.add(Cardinal.DOWN);
        RectSolid.fill(editor, rand, s, e, floor, false, true);
        RectSolid.fill(editor, rand, s, e, bridgeBlocks, true, false);

        // end of the tunnel;
        Coord location = new Coord(end);
        location.add(dir, 1);

        Coord start = new Coord(location);
        Cardinal[] orth = Cardinal.orthogonal(dir);
        start.add(orth[0], 2);
        start.add(Cardinal.UP, 2);
        Coord end = new Coord(location);
        end.add(orth[1], 2);
        end.add(Cardinal.DOWN, 2);

        RectSolid.fill(editor, rand, start, end, wallBlocks, false, true);

    }

    public Coord[] getEnds() {
        Coord[] toReturn = new Coord[2];
        toReturn[0] = new Coord(start);
        toReturn[1] = new Coord(end);
        return toReturn;
    }

    public Cardinal getDirection() {
        return this.dir;
    }

    public void genSegments(IWorldEditor editor, Random rand, IDungeonLevel level) {
        LevelSettings settings = level.getSettings();
        ISegmentGenerator segGen = settings.getSegments();
        for (Coord c : this) {
            this.segments.addAll(segGen.genSegment(editor, rand, level, dir, c));
        }

    }

    public List<ISegment> getSegments() {
        return this.segments;
    }
}
