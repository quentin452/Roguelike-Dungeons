package greymerk.roguelike.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import net.minecraft.util.Tuple;

public enum RogueConfig {

    DONATURALSPAWN,
    LEVELRANGE,
    LEVELMAXROOMS,
    LEVELSCATTER,
    SPAWNFREQUENCY,
    GENEROUS,
    MOBDROPS,
    DIMENSIONWL,
    DIMENSIONBL,
    PRECIOUSBLOCKS,
    LOOTING,
    DONOVELTYSPAWN,
    UPPERLIMIT,
    LOWERLIMIT,
    ROGUESPAWNERS;

    public static final String configDirName = "config/roguelike_dungeons";
    public static final String configFileName = "roguelike.cfg";

    private static ConfigFile instance = null;

    static {
        init();
    }

    public static String getName(RogueConfig option) {
        switch (option) {
            case DONATURALSPAWN:
                return "doNaturalSpawn";
            case DONOVELTYSPAWN:
                return "doNoveltySpawn";
            case LEVELRANGE:
                return "levelRange";
            case LEVELMAXROOMS:
                return "levelMaxRooms";
            case LEVELSCATTER:
                return "levelScatter";
            case SPAWNFREQUENCY:
                return "spawnFrequency";
            case GENEROUS:
                return "generous";
            case DIMENSIONWL:
                return "dimensionWL";
            case DIMENSIONBL:
                return "dimensionBL";
            case PRECIOUSBLOCKS:
                return "preciousBlocks";
            case LOOTING:
                return "looting";
            case UPPERLIMIT:
                return "upperLimit";
            case LOWERLIMIT:
                return "lowerLimit";
            case ROGUESPAWNERS:
                return "rogueSpawners";
            default:
                return null;
        }
    }

    public static Tuple getDefault(RogueConfig option) {
        switch (option) {
            case DONATURALSPAWN:
                return new Tuple(getName(option), true);
            case DONOVELTYSPAWN:
                return new Tuple(getName(option), true);
            case LEVELRANGE:
                return new Tuple(getName(option), 80);
            case LEVELMAXROOMS:
                return new Tuple(getName(option), 30);
            case LEVELSCATTER:
                return new Tuple(getName(option), 10);
            case SPAWNFREQUENCY:
                return new Tuple(getName(option), 10);
            case GENEROUS:
                return new Tuple(getName(option), true);
            case DIMENSIONWL:
                List<Integer> wl = new ArrayList<>();
                wl.add(0);
                return new Tuple(getName(option), wl);
            case DIMENSIONBL:
                return new Tuple(getName(option), new ArrayList<Integer>());
            case PRECIOUSBLOCKS:
                return new Tuple(getName(option), true);
            case LOOTING:
                return new Tuple(getName(option), 0.085D);
            case UPPERLIMIT:
                return new Tuple(getName(option), 100);
            case LOWERLIMIT:
                return new Tuple(getName(option), 60);
            case ROGUESPAWNERS:
                return new Tuple(getName(option), true);
            default:
                return null;
        }
    }

    @SuppressWarnings("unchecked")
    private static void setDefaults() {
        if (instance.ContainsKey(getName(DONATURALSPAWN)))
            setBoolean(DONATURALSPAWN, (Boolean) Objects.requireNonNull(getDefault(DONATURALSPAWN)).getSecond());
        if (instance.ContainsKey(getName(DONOVELTYSPAWN)))
            setBoolean(DONOVELTYSPAWN, (Boolean) Objects.requireNonNull(getDefault(DONOVELTYSPAWN)).getSecond());
        if (instance.ContainsKey(getName(LEVELRANGE)))
            setInt(LEVELRANGE, (Integer) Objects.requireNonNull(getDefault(LEVELRANGE)).getSecond());
        if (instance.ContainsKey(getName(LEVELMAXROOMS)))
            setInt(LEVELMAXROOMS, (Integer) Objects.requireNonNull(getDefault(LEVELMAXROOMS)).getSecond());
        if (instance.ContainsKey(getName(LEVELSCATTER)))
            setInt(LEVELSCATTER, (Integer) Objects.requireNonNull(getDefault(LEVELSCATTER)).getSecond());
        if (instance.ContainsKey(getName(SPAWNFREQUENCY)))
            setInt(SPAWNFREQUENCY, (Integer) Objects.requireNonNull(getDefault(SPAWNFREQUENCY)).getSecond());
        if (instance.ContainsKey(getName(GENEROUS))) setBoolean(GENEROUS, (Boolean) Objects.requireNonNull(getDefault(GENEROUS)).getSecond());
        if (instance.ContainsKey(getName(DIMENSIONWL)))
            setIntList(DIMENSIONWL, (List<Integer>) Objects.requireNonNull(getDefault(DIMENSIONWL)).getSecond());
        if (instance.ContainsKey(getName(DIMENSIONBL)))
            setIntList(DIMENSIONBL, (List<Integer>) Objects.requireNonNull(getDefault(DIMENSIONBL)).getSecond());
        if (instance.ContainsKey(getName(PRECIOUSBLOCKS)))
            setBoolean(PRECIOUSBLOCKS, (Boolean) Objects.requireNonNull(getDefault(PRECIOUSBLOCKS)).getSecond());
        if (instance.ContainsKey(getName(LOOTING))) setDouble(LOOTING, (Double) Objects.requireNonNull(getDefault(LOOTING)).getSecond());
        if (instance.ContainsKey(getName(UPPERLIMIT)))
            setInt(UPPERLIMIT, (Integer) Objects.requireNonNull(getDefault(UPPERLIMIT)).getSecond());
        if (instance.ContainsKey(getName(LOWERLIMIT)))
            setInt(LOWERLIMIT, (Integer) Objects.requireNonNull(getDefault(LOWERLIMIT)).getSecond());
        if (instance.ContainsKey(getName(ROGUESPAWNERS)))
            setBoolean(ROGUESPAWNERS, (Boolean) Objects.requireNonNull(getDefault(ROGUESPAWNERS)).getSecond());
    }

    public static double getDouble(RogueConfig option) {
        reload(false);
        Tuple def = getDefault(option);
        assert def != null;
        return instance.GetDouble(getName(option), (Double) def.getSecond());
    }

    public static void setDouble(RogueConfig option, double value) {
        reload(false);
        instance.Set(getName(option), value);
    }

    public static boolean getBoolean(RogueConfig option) {
        reload(false);
        Tuple def = getDefault(option);
        assert def != null;
        return instance.GetBoolean(getName(option), (Boolean) def.getSecond());
    }

    public static void setBoolean(RogueConfig option, Boolean value) {
        reload(false);
        instance.Set(getName(option), value);
    }

    public static int getInt(RogueConfig option) {
        reload(false);
        Tuple def = getDefault(option);
        assert def != null;
        return instance.GetInteger((String) def.getFirst(), (Integer) def.getSecond());
    }

    public static void setInt(RogueConfig option, int value) {
        reload(false);
        Tuple def = getDefault(option);
        assert def != null;
        instance.Set((String) def.getFirst(), value);
    }

    @SuppressWarnings("unchecked")
    public static List<Integer> getIntList(RogueConfig option) {
        reload(false);
        Tuple def = getDefault(option);
        assert def != null;
        return instance.GetListInteger((String) def.getFirst(), (ArrayList<Integer>) def.getSecond());
    }

    public static void setIntList(RogueConfig option, List<Integer> value) {
        reload(false);
        Tuple def = getDefault(option);
        assert def != null;
        instance.Set((String) def.getFirst(), value);
    }

    private static void init() {

        // make sure file exists
        File configDir = new File(configDirName);
        if (!configDir.exists()) {
            configDir.mkdir();
        }

        File cfile = new File(configDirName + "/" + configFileName);

        if (!cfile.exists()) {
            try {
                cfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // read in configs
        try {
            instance = new ConfigFile(configDirName + "/" + configFileName, new INIParser());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setDefaults();

        try {
            instance.Write();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reload(boolean force) {
        if (instance == null || force) {
            init();
        }
    }

}
