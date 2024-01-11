package greymerk.roguelike.dungeon.segment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;

public class SegmentGenerator implements ISegmentGenerator {

    protected Segment arch;
    protected WeightedRandomizer<Segment> segments;

    public SegmentGenerator(Segment arch) {
        this.segments = new WeightedRandomizer<>();
        this.arch = arch;
    }

    public SegmentGenerator(SegmentGenerator toCopy) {
        this.arch = toCopy.arch;
        this.segments = new WeightedRandomizer<>(toCopy.segments);
    }

    public SegmentGenerator(JsonObject json) {
        String archType = json.get("arch").getAsString();
        arch = Segment.valueOf(archType);

        this.segments = new WeightedRandomizer<>();
        JsonArray segmentList = json.get("segments").getAsJsonArray();
        for (JsonElement e : segmentList) {
            JsonObject segData = e.getAsJsonObject();
            String segType = segData.get("type").getAsString();
            int weight = segData.get("weight").getAsInt();
            Segment type = Segment.valueOf(segType);
            this.segments.add(new WeightedChoice<>(type, weight));
        }
    }

    public void add(Segment toAdd, int weight) {
        this.segments.add(new WeightedChoice<>(toAdd, weight));
    }

    @Override
    public List<ISegment> genSegment(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, Coord pos) {

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        List<ISegment> segs = new ArrayList<>();

        Coord coord = new Coord(pos);

        for (Cardinal orth : Cardinal.orthogonal(dir)) {
            ISegment seg = pickSegment(editor, rand, level, dir, pos);
            if (seg == null) return segs;
            seg.generate(editor, rand, level, orth, level.getSettings().getTheme(), coord);
            segs.add(seg);
        }

        if (!level.hasNearbyNode(pos) && rand.nextInt(3) == 0)
            addSupport(editor, rand, level.getSettings().getTheme(), x, y, z);

        return segs;
    }

    private ISegment pickSegment(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, Coord pos) {
        int x = pos.getX();
        int z = pos.getZ();

        return ((dir == Cardinal.NORTH || dir == Cardinal.SOUTH) && z % 3 == 0) ?
            ((z % 6 == 0) ? Segment.getSegment(arch) : Segment.getSegment(segments.get(rand))) :
            ((dir == Cardinal.WEST || dir == Cardinal.EAST) && x % 3 == 0) ?
                ((x % 6 == 0) ? Segment.getSegment(arch) : Segment.getSegment(segments.get(rand))) :
                null;
    }

    private void addSupport(IWorldEditor editor, Random rand, ITheme theme, int x, int y, int z) {
        if (!editor.isAirBlock(new Coord(x, y - 2, z))) return;

        editor.fillDown(rand, new Coord(x, y - 2, z), theme.getPrimaryPillar());

        IStair stair = theme.getPrimaryStair();
        stair.setOrientation(Cardinal.WEST, true);
        stair.set(editor, new Coord(x - 1, y - 2, z));

        stair.setOrientation(Cardinal.EAST, true);
        stair.set(editor, new Coord(x + 1, y - 2, z));

        stair.setOrientation(Cardinal.SOUTH, true);
        stair.set(editor, new Coord(x, y - 2, z + 1));

        stair.setOrientation(Cardinal.NORTH, true);
        stair.set(editor, new Coord(x, y - 2, z - 1));
    }
}
