package greymerk.roguelike.treasure.loot;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.treasure.loot.provider.ItemArmour;
import greymerk.roguelike.treasure.loot.provider.ItemBlock;
import greymerk.roguelike.treasure.loot.provider.ItemEnchBonus;
import greymerk.roguelike.treasure.loot.provider.ItemEnchBook;
import greymerk.roguelike.treasure.loot.provider.ItemFood;
import greymerk.roguelike.treasure.loot.provider.ItemJunk;
import greymerk.roguelike.treasure.loot.provider.ItemOre;
import greymerk.roguelike.treasure.loot.provider.ItemPotion;
import greymerk.roguelike.treasure.loot.provider.ItemRecord;
import greymerk.roguelike.treasure.loot.provider.ItemSmithy;
import greymerk.roguelike.treasure.loot.provider.ItemSpecialty;
import greymerk.roguelike.treasure.loot.provider.ItemSupply;
import greymerk.roguelike.treasure.loot.provider.ItemTool;
import greymerk.roguelike.treasure.loot.provider.ItemWeapon;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedRandomizer;

public class LootSettings {

    private final Map<Loot, IWeighted<ItemStack>> loot;

    public LootSettings(int level) {
        loot = new HashMap<>();
        loot.put(Loot.WEAPON, new ItemWeapon(0, level));
        loot.put(Loot.ARMOUR, new ItemArmour(0, level));
        loot.put(Loot.BLOCK, new ItemBlock(0, level));
        loot.put(Loot.JUNK, new ItemJunk(0, level));
        loot.put(Loot.ORE, new ItemOre(0, level));
        loot.put(Loot.TOOL, new ItemTool(0, level));
        loot.put(Loot.POTION, new ItemPotion(0, level));
        loot.put(Loot.FOOD, new ItemFood(0, level));
        loot.put(Loot.ENCHANTBOOK, new ItemEnchBook(0, level));
        loot.put(Loot.ENCHANTBONUS, new ItemEnchBonus(0, level));
        loot.put(Loot.SUPPLY, new ItemSupply(0, level));
        loot.put(Loot.MUSIC, new ItemRecord(0, level));
        loot.put(Loot.SMITHY, new ItemSmithy(0, level));
        loot.put(Loot.SPECIAL, new ItemSpecialty(0, level));
        loot.put(Loot.REWARD, new WeightedRandomLoot(Items.stick, 0, 1));
    }

    public LootSettings(LootSettings toCopy) {
        this.loot = new HashMap<>();
        this.loot.putAll(toCopy.loot);
    }

    public LootSettings(LootSettings base, LootSettings override) {
        this.loot = new HashMap<>();
        if (base != null) this.loot.putAll(base.loot);
        if (override != null) this.loot.putAll(override.loot);
    }

    public LootSettings(JsonObject data) {
        this.loot = new HashMap<>();
        for (Loot type : Loot.values()) {
            if (data.has(type.toString())) {
                JsonElement providerData = data.get(type.toString());

                if (providerData.isJsonObject()) {
                    try {
                        loot.put(type, new WeightedRandomLoot(providerData.getAsJsonObject(), 0));
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (providerData.isJsonArray()) {

                    WeightedRandomizer<ItemStack> items = new WeightedRandomizer<>(0);
                    JsonArray lootList = providerData.getAsJsonArray();

                    for (JsonElement e : lootList) {
                        items.add(parseProvider(e.getAsJsonObject()));
                    }

                    loot.put(type, items);
                }
            }
        }
    }

    private IWeighted<ItemStack> parseProvider(JsonObject data) {

        int weight = data.has("weight") ? data.get("weight").getAsInt() : 1;
        JsonElement loot = data.get("data");

        if (loot.isJsonObject()) {
            try {
                return new WeightedRandomLoot(loot.getAsJsonObject(), weight);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        JsonArray lootList = loot.getAsJsonArray();

        WeightedRandomizer<ItemStack> items = new WeightedRandomizer<>(weight);

        for (JsonElement e : lootList) {
            items.add(parseProvider(e.getAsJsonObject()));
        }

        return items;
    }

    public ItemStack get(Loot type, Random rand) {
        IWeighted<ItemStack> provider = loot.get(type);
        return provider.get(rand);
    }

    public IWeighted<ItemStack> get(Loot type) {
        return this.loot.get(type);
    }

    public void set(Loot type, IWeighted<ItemStack> provider) {
        this.loot.put(type, provider);
    }
}
