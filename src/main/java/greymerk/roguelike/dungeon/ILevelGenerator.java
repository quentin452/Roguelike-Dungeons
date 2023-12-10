package greymerk.roguelike.dungeon;

import java.util.List;

import greymerk.roguelike.worldgen.Coord;

public interface ILevelGenerator {

    void generate(Coord start, DungeonNode oldEnd);

    List<DungeonNode> getNodes();

    List<DungeonTunnel> getTunnels();

    DungeonNode getEnd();

}
