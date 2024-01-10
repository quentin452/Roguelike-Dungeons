package greymerk.roguelike.dungeon.settings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.settings.builtin.SettingsCustomBase;
import greymerk.roguelike.dungeon.settings.builtin.SettingsDesertTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsForestTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsGenerator;
import greymerk.roguelike.dungeon.settings.builtin.SettingsGrasslandTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsJungleTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsLootRules;
import greymerk.roguelike.dungeon.settings.builtin.SettingsMesaTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsMountainTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsRooms;
import greymerk.roguelike.dungeon.settings.builtin.SettingsSecrets;
import greymerk.roguelike.dungeon.settings.builtin.SettingsSegments;
import greymerk.roguelike.dungeon.settings.builtin.SettingsSize;
import greymerk.roguelike.dungeon.settings.builtin.SettingsSwampTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsTheme;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public class SettingsResolver {

    private static final String SETTINGS_DIRECTORY = RogueConfig.configDirName + "/settings";
    private final Map<String, DungeonSettings> settings;
    private final List<DungeonSettings> builtin;
    private final DungeonSettings base;

    public SettingsResolver() {
        settings = new HashMap<>();
        this.base = getDungeonSettings();

        this.builtin = new ArrayList<>();
        this.builtin.add(new SettingsDesertTheme());
        this.builtin.add(new SettingsGrasslandTheme());
        this.builtin.add(new SettingsJungleTheme());
        this.builtin.add(new SettingsSwampTheme());
        this.builtin.add(new SettingsMountainTheme());
        this.builtin.add(new SettingsForestTheme());
        this.builtin.add(new SettingsMesaTheme());

        File settingsDir = new File(SETTINGS_DIRECTORY);
        if (!settingsDir.exists() || !settingsDir.isDirectory()) return;
        File[] settingsFiles = settingsDir.listFiles();
        assert settingsFiles != null;
        Arrays.sort(settingsFiles);

        for (File toParse : settingsFiles) {
            DungeonSettings toAdd;
            try {
                toAdd = parseFile(toParse);
            } catch (Exception e) {
                System.err.println("Error found in file " + toParse.getName());
                System.err.println(e.getMessage());
                continue; // skip this setting
            }
            settings.put(toAdd.getName(), toAdd);
        }
    }

    private static DungeonSettings getDungeonSettings() {
        DungeonSettings base = new SettingsBlank();
        base = new DungeonSettings(base, new SettingsRooms());
        base = new DungeonSettings(base, new SettingsSecrets());
        base = new DungeonSettings(base, new SettingsSegments());
        base = new DungeonSettings(base, new SettingsSize());
        base = new DungeonSettings(base, new SettingsTheme());
        base = new DungeonSettings(base, new SettingsGenerator());
        base = new DungeonSettings(base, new SettingsLootRules());
        base.setCriteria(new SpawnCriteria());
        return base;
    }

    private DungeonSettings parseFile(File toParse) throws Exception {
        String content;

        try {
            content = Files.toString(toParse, Charsets.UTF_8);
        } catch (IOException e) {
            throw new Exception("Error reading file");
        }

        JsonParser jParser = new JsonParser();
        JsonObject root;

        try {
            root = (JsonObject) jParser.parse(content);
        } catch (JsonSyntaxException e) {
            Throwable cause = e.getCause();
            throw new Exception(cause.getMessage());
        } catch (Exception e) {
            throw new Exception("An unknown error occurred while parsing json");
        }
        DungeonSettings toAdd;
        try {
            toAdd = new DungeonSettings(settings, root);
        } catch (Exception e) {
            throw new Exception("An error occurred while creating DungeonSettings");
        }

        try {
            return toAdd;
        } catch (Exception e) {
            throw new Exception("An error occurred while adding " + toAdd.getName());
        }
    }

    public DungeonSettings getByName(String name) {
        DungeonSettings override = this.settings.get(name);
        if (override == null) return null;
        return new DungeonSettings(this.base, override);
    }

    public ISettings getSettings(IWorldEditor editor, Random rand, Coord pos) {
        DungeonSettings builtinSettings = getBuiltin(editor, rand, pos);
        DungeonSettings customSettings = getCustom(editor, rand, pos);

        if (customSettings != null) {
            DungeonSettings customBase = createCustomBase(customSettings);
            return new DungeonSettings(customBase, customSettings);
        }

        if (builtinSettings != null && RogueConfig.getBoolean(RogueConfig.DONOVELTYSPAWN)) {
            return new DungeonSettings(base, builtinSettings);
        }

        return base.isValid(editor, pos) ? new DungeonSettings(base) : null;
    }

    private DungeonSettings createCustomBase(DungeonSettings custom) {
        DungeonSettings customBase = new SettingsCustomBase();
        List<SettingsType> overrides = custom.getOverrides();

        for (SettingsType type : SettingsType.values()) {
            if (!overrides.contains(type)) {
                customBase = addSettingOfType(customBase, type);
            }
        }
        return customBase;
    }

    private DungeonSettings addSettingOfType(DungeonSettings base, SettingsType type) {
        switch (type) {
            case LOOTRULES:
                return new DungeonSettings(base, new SettingsLootRules());
            case SECRETS:
                return new DungeonSettings(base, new SettingsSecrets());
            case ROOMS:
                return new DungeonSettings(base, new SettingsRooms());
            case THEMES:
                return new DungeonSettings(base, new SettingsTheme());
            case SEGMENTS:
                return new DungeonSettings(base, new SettingsSegments());
            case SIZE:
                return new DungeonSettings(base, new SettingsSize());
            case GENERATORS:
                return new DungeonSettings(base, new SettingsGenerator());
            default:
                return base;
        }
    }

    public ISettings getDefaultSettings() {
        return new DungeonSettings(base);
    }

    public ISettings getWithDefault(String name) {
        DungeonSettings custom = settings.get(name);
        if (custom == null) return null;

        DungeonSettings customBase = createCustomBase(custom);
        return new DungeonSettings(customBase, custom);
    }

    private DungeonSettings getBuiltin(IWorldEditor editor, Random rand, Coord pos) {
        WeightedRandomizer<DungeonSettings> settingsRandomizer = new WeightedRandomizer<>();

        for (DungeonSettings setting : this.builtin) {
            if (setting.isValid(editor, pos)) {
                settingsRandomizer.add(new WeightedChoice<>(setting, setting.criteria.weight));
            }
        }

        return settingsRandomizer.get(rand);
    }

    private DungeonSettings getCustom(IWorldEditor editor, Random rand, Coord pos) {
        WeightedRandomizer<DungeonSettings> settingsRandomizer = new WeightedRandomizer<>();

        for (DungeonSettings setting : this.settings.values()) {
            if (setting.isValid(editor, pos)) {
                int weight = setting.criteria.weight;
                settingsRandomizer.add(new WeightedChoice<>(setting, weight));
            }
        }

        return settingsRandomizer.get(rand);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (String key : this.settings.keySet()) {
            s.append(key).append(" ");
        }
        return s.toString();
    }
}
