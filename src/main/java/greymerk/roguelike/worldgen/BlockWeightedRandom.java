package greymerk.roguelike.worldgen;

import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

public class BlockWeightedRandom extends BlockBase {

    private final WeightedRandomizer<IBlockFactory> blocks;

    public BlockWeightedRandom() {
        blocks = new WeightedRandomizer<>();
    }

    public BlockWeightedRandom(JsonElement data) {
        this();
        for (JsonElement entry : (JsonArray) data) {
            JsonObject d = entry.getAsJsonObject();
            int weight = d.get("weight").getAsInt();
            IBlockFactory toAdd = BlockProvider.create(d);
            this.addBlock(toAdd, weight);
        }
    }

    public void addBlock(IBlockFactory toAdd, int weight) {
        blocks.add(new WeightedChoice<>(toAdd, weight));
    }

    @Override
    public boolean set(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid) {
        IBlockFactory block = blocks.get(rand);
        return block.set(editor, rand, pos, fillAir, replaceSolid);
    }
}
