package greymerk.roguelike.dungeon.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

public class DungeonFactory implements IDungeonFactory {

    private final List<DungeonRoom> singles;
    private final WeightedRandomizer<DungeonRoom> multiple;
    private DungeonRoom base;

    public DungeonFactory(DungeonRoom base) {
        singles = new ArrayList<>();
        multiple = new WeightedRandomizer<>();
        this.base = base;
    }

    public DungeonFactory() {
        this(DungeonRoom.CORNER);
    }

    public DungeonFactory(JsonArray json) {
        this();

        for (JsonElement e : json) {
            JsonObject entry = e.getAsJsonObject();
            String mode = entry.get("mode").getAsString();
            if (mode.equals("single")) {
                this.addSingle(DungeonRoom.valueOf(entry.get("type").getAsString()));
            }

            if (mode.equals("random")) {
                this.addRandom(DungeonRoom.valueOf(entry.get("type").getAsString()), entry.get("weight").getAsInt());
            }
        }
    }

    public DungeonFactory(DungeonFactory toCopy) {
        singles = new ArrayList<>();
        singles.addAll(toCopy.singles);

        multiple = new WeightedRandomizer<>(toCopy.multiple);
    }

    public void addSingle(DungeonRoom type) {
        singles.add(type);
    }

    public void addByRatio(DungeonRoom toAdd, int rate) {
        if (rate <= 0) return;

        int max = RogueConfig.getInt(RogueConfig.LEVELMAXROOMS);
        int numRooms = max / rate;

        if (numRooms == 0) {
            addSingle(toAdd);
            return;
        }

        for (int i = 0; i < numRooms; ++i) {
            addSingle(toAdd);
        }
    }

    public void addRandom(DungeonRoom type, int weight) {
        multiple.add(new WeightedChoice<>(type, weight));
    }

    @Override
    public IDungeonRoom get(Random rand) {
        if (!singles.isEmpty()) return DungeonRoom.getInstance(singles.remove(0));
        if (!multiple.isEmpty()) return DungeonRoom.getInstance(multiple.get(rand));
        return DungeonRoom.getInstance(base);
    }
}
