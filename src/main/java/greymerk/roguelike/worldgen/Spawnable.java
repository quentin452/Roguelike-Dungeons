package greymerk.roguelike.worldgen;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntityMobSpawner;

import com.google.gson.JsonObject;
import greymerk.roguelike.util.JsonNBT;

public class Spawnable {

    private final boolean equip;
    private final String name;
    private NBTTagCompound meta;

    public Spawnable(JsonObject data) {
        name = data.get("name").getAsString();
        equip = !data.has("equip") || data.get("equip").getAsBoolean();
        if (data.has("meta")) {
            JsonObject metadata = data.get("meta").getAsJsonObject();
            this.meta = JsonNBT.jsonToCompound(metadata);
        }
    }

    public void generate(IWorldEditor editor, Random rand, Coord cursor, int level) {

        if (!new MetaBlock(Blocks.mob_spawner).set(editor, cursor)) return;

        TileEntityMobSpawner spawner = (TileEntityMobSpawner) editor.getTileEntity(cursor);

        if (spawner == null) return;

        MobSpawnerBaseLogic logic = spawner.func_145881_a();
        logic.setEntityName(this.name);

        if (meta == null) {
            if (equip) Spawner.setRoguelike(logic, level, this.name);
            return;
        }

        Spawner.setMeta(logic, meta);
    }
}
