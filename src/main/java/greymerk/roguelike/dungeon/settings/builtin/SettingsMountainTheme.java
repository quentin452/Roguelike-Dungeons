package greymerk.roguelike.dungeon.settings.builtin;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.BiomeDictionary;

import greymerk.roguelike.dungeon.base.DungeonFactory;
import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.dungeon.segment.Segment;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SpawnCriteria;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;

public class SettingsMountainTheme extends DungeonSettings {

    public SettingsMountainTheme() {

        this.criteria = new SpawnCriteria();
        List<BiomeDictionary.Type> biomes = new ArrayList<>();
        biomes.add(BiomeDictionary.Type.MOUNTAIN);
        this.criteria.setBiomeTypes(biomes);

        this.towerSettings = new TowerSettings(Tower.ENIKO, Theme.getTheme(Theme.OAK));

        Theme[] themes = { Theme.ENIKO, Theme.ENIKO2, Theme.SEWER, Theme.MOSSY, Theme.NETHER };

        for (int i = 0; i < 5; ++i) {
            LevelSettings level = new LevelSettings();
            level.setTheme(Theme.getTheme(themes[i]));

            if (i == 0) {
                level.setScatter(16);
                level.setRange(60);
                level.setNumRooms(10);

                DungeonFactory factory;

                factory = new DungeonFactory();
                factory.addSingle(DungeonRoom.LIBRARY);
                factory.addSingle(DungeonRoom.FIRE);
                factory.addRandom(DungeonRoom.ENIKO, 10);
                factory.addRandom(DungeonRoom.CORNER, 3);
                level.setRooms(factory);

                SecretFactory secrets = new SecretFactory();
                secrets.addRoom(DungeonRoom.BEDROOM, 2);
                secrets.addRoom(DungeonRoom.SMITH);
                level.setSecrets(secrets);

                SegmentGenerator segments = new SegmentGenerator(Segment.ARCH);
                segments.add(Segment.DOOR, 7);
                segments.add(Segment.ANKH, 2);
                segments.add(Segment.PLANT, 3);
                segments.add(Segment.LAMP, 1);
                segments.add(Segment.FLOWERS, 1);
                level.setSegments(segments);
            }

            if (i == 1) {
                level.setScatter(16);
                level.setRange(80);
                level.setNumRooms(20);

                DungeonFactory factory;
                factory = new DungeonFactory();
                factory.addSingle(DungeonRoom.FIRE);
                factory.addSingle(DungeonRoom.MESS);
                factory.addSingle(DungeonRoom.LIBRARY);
                factory.addSingle(DungeonRoom.LAB);
                factory.addRandom(DungeonRoom.ENIKO, 10);
                factory.addRandom(DungeonRoom.CORNER, 3);
                level.setRooms(factory);

                SecretFactory secrets = new SecretFactory();
                secrets.addRoom(DungeonRoom.ENCHANT);
                level.setSecrets(secrets);

            }

            if (i == 2) {
                level.setDifficulty(4);

                SegmentGenerator segments = new SegmentGenerator(Segment.SEWERARCH);
                segments.add(Segment.SEWER, 7);
                segments.add(Segment.SEWERDRAIN, 4);
                segments.add(Segment.SEWERDOOR, 2);
                level.setSegments(segments);

                DungeonFactory factory;
                factory = new DungeonFactory();
                factory.addRandom(DungeonRoom.BRICK, 4);
                factory.addRandom(DungeonRoom.SLIME, 7);
                factory.addRandom(DungeonRoom.CORNER, 3);
                factory.addRandom(DungeonRoom.SPIDER, 2);
                factory.addRandom(DungeonRoom.PIT, 2);
                factory.addRandom(DungeonRoom.PRISON, 3);
                level.setRooms(factory);
            }

            levels.put(i, level);
        }
    }
}
