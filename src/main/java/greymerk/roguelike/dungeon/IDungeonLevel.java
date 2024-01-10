package greymerk.roguelike.dungeon;

import java.util.List;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;

public interface IDungeonLevel {

    LevelSettings getSettings();

    List<DungeonNode> getNodes();

    List<DungeonTunnel> getTunnels();

    boolean inRange(Coord pos);

    boolean hasNearbyNode(Coord pos);

}
