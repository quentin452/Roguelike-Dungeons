package greymerk.roguelike.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class BlockLayers extends BlockBase {

    private final List<IBlockFactory> blocks;

    public BlockLayers() {
        blocks = new ArrayList<>();
    }

    public BlockLayers(JsonElement data) {
        this();
        for (JsonElement entry : (JsonArray) data) {
            this.addBlock(BlockProvider.create(entry.getAsJsonObject()));
        }
    }

    public void addBlock(IBlockFactory toAdd) {
        blocks.add(toAdd);
    }

    @Override
    public boolean set(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid) {
        IBlockFactory block = this.blocks.get(pos.getY() % this.blocks.size());
        return block.set(editor, rand, pos, fillAir, replaceSolid);
    }

}
