package greymerk.roguelike.dungeon.settings.builtin;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BiomeDictionary;

import greymerk.roguelike.dungeon.base.DungeonFactory;
import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.segment.Segment;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SpawnCriteria;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.loot.LootRuleManager;
import greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import greymerk.roguelike.util.WeightedRandomizer;

public class SettingsSwampTheme extends DungeonSettings {

    public SettingsSwampTheme() {

        this.criteria = new SpawnCriteria();
        List<BiomeDictionary.Type> biomes = new ArrayList<>();
        biomes.add(BiomeDictionary.Type.SWAMP);
        this.criteria.setBiomeTypes(biomes);

        this.towerSettings = new TowerSettings(Tower.WITCH, Theme.getTheme(Theme.DARKOAK));

        Theme[] themes = { Theme.DARKHALL, Theme.DARKHALL, Theme.MUDDY, Theme.MOSSY, Theme.NETHER };

        WeightedRandomizer<ItemStack> brewing = new WeightedRandomizer<ItemStack>();
        brewing.add(new WeightedRandomLoot(Items.glass_bottle, 0, 1, 3, 3));
        brewing.add(new WeightedRandomLoot(Items.magma_cream, 0, 1, 2, 1));
        brewing.add(new WeightedRandomLoot(Items.speckled_melon, 0, 1, 3, 1));
        brewing.add(new WeightedRandomLoot(Items.blaze_powder, 0, 1, 3, 1));
        brewing.add(new WeightedRandomLoot(Items.sugar, 0, 1, 3, 1));
        this.lootRules = new LootRuleManager();
        for (int i = 0; i < 5; ++i) {
            this.lootRules.add(null, brewing, i, true, 2);
            this.lootRules.add(null, new WeightedRandomLoot(Items.slime_ball, 0, 1, 1 + i, 1), i, false, 4 + i * 3);
        }
        for (int i = 0; i < 5; ++i) {

            LevelSettings level = new LevelSettings();
            level.setTheme(Theme.getTheme(themes[i]));

            if (i == 0) {

                SegmentGenerator segments = new SegmentGenerator(Segment.ARCH);
                segments.add(Segment.DOOR, 8);
                segments.add(Segment.LAMP, 2);
                segments.add(Segment.FLOWERS, 1);
                segments.add(Segment.MUSHROOM, 2);
                level.setSegments(segments);

                DungeonFactory factory = new DungeonFactory();
                factory.addSingle(DungeonRoom.CAKE);
                factory.addSingle(DungeonRoom.DARKHALL);
                factory.addRandom(DungeonRoom.BRICK, 10);
                factory.addRandom(DungeonRoom.CORNER, 3);
                level.setRooms(factory);
            }

            if (i == 1) {

                SegmentGenerator segments = new SegmentGenerator(Segment.ARCH);
                segments.add(Segment.DOOR, 8);
                segments.add(Segment.SHELF, 4);
                segments.add(Segment.INSET, 4);
                segments.add(Segment.MUSHROOM, 3);
                level.setSegments(segments);

                DungeonFactory factory = new DungeonFactory();
                factory.addSingle(DungeonRoom.CAKE);
                factory.addSingle(DungeonRoom.LAB);
                factory.addSingle(DungeonRoom.SPIDER);
                factory.addSingle(DungeonRoom.PIT);
                factory.addSingle(DungeonRoom.PRISON);
                factory.addRandom(DungeonRoom.BRICK, 10);
                factory.addRandom(DungeonRoom.CORNER, 3);
                level.setRooms(factory);

            }

            levels.put(i, level);
        }
    }
}
