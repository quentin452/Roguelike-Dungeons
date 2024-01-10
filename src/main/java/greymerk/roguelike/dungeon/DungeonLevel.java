package greymerk.roguelike.dungeon;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public class DungeonLevel implements IDungeonLevel {

    private final Coord origin;
    private final LevelSettings settings;
    private ILevelGenerator generator;

    public DungeonLevel(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin) {
        this.settings = settings;
        this.origin = origin;
    }

    public void generate(ILevelGenerator generator, Coord start, DungeonNode oldEnd) {
        this.generator = generator;
        generator.generate(start, oldEnd);
    }

    public int nodeCount() {
        return this.getNodes().size();
    }

    @Override
    public LevelSettings getSettings() {
        return this.settings;
    }

    @Override
    public List<DungeonNode> getNodes() {
        return this.generator.getNodes();
    }

    @Override
    public List<DungeonTunnel> getTunnels() {
        return this.generator.getTunnels();
    }

    @Override
    public boolean hasNearbyNode(Coord pos) {
        return getNodes().stream()
            .anyMatch(node -> node.getPosition().distance(pos) < node.getSize());
    }

    @Override
    public boolean inRange(Coord pos) {
        int dist = (int) this.origin.distance(pos);
        return dist < this.settings.getRange();
    }

}
