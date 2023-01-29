package greymerk.roguelike.dungeon;

import java.util.List;

import greymerk.roguelike.worldgen.Coord;

public interface ILevelGenerator {

    public void generate(Coord start, DungeonNode oldEnd);

    public List<DungeonNode> getNodes();

    public List<DungeonTunnel> getTunnels();

    public DungeonNode getEnd();

}
