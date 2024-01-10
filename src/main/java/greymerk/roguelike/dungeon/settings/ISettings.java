package greymerk.roguelike.dungeon.settings;

import java.util.List;

import greymerk.roguelike.treasure.loot.LootRuleManager;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public interface ISettings {

    boolean isValid(IWorldEditor editor, Coord pos);

    LevelSettings getLevelSettings(int level);

    TowerSettings getTower();

    LootRuleManager getLootRules();

    int getNumLevels();

    List<SettingsType> getOverrides();
}
