package greymerk.roguelike.worldgen;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

public class SpawnerSettings {

    private final Map<Spawner, IWeighted<Spawnable>> spawners;

    public SpawnerSettings(JsonObject data) {
        this.spawners = new HashMap<>();

        for (Spawner type : Spawner.values()) {
            if (data.has(type.name())) {
                JsonArray categoryData = data.get(type.name()).getAsJsonArray();
                WeightedRandomizer<Spawnable> spawns = new WeightedRandomizer<>();
                this.spawners.put(type, spawns);
                for (JsonElement spawnData : categoryData) {
                    JsonObject spawnObj = spawnData.getAsJsonObject();
                    int weight = spawnObj.has("weight") ? spawnObj.get("weight").getAsInt() : 1;
                    spawns.add(new WeightedChoice<>(new Spawnable(spawnObj), weight));
                }
            }
        }
    }

    public SpawnerSettings(SpawnerSettings toCopy) {
        this.spawners = new HashMap<>();
        this.spawners.putAll(toCopy.spawners);
    }

    public SpawnerSettings(SpawnerSettings base, SpawnerSettings override) {
        this.spawners = new HashMap<>();
        if (base != null) this.spawners.putAll(base.spawners);
        if (override != null) this.spawners.putAll(override.spawners);
    }

    public void generate(IWorldEditor editor, Random rand, Coord cursor, Spawner type, int level) {

        if (!spawners.containsKey(type)) {
            Spawner.generate(editor, rand, level, cursor, type);
            return;
        }

        Spawnable toSpawn = spawners.get(type).get(rand);

        toSpawn.generate(editor, rand, cursor, level);

    }

}
