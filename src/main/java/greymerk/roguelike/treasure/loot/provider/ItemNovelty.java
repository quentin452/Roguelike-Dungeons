package greymerk.roguelike.treasure.loot.provider;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.TextFormat;
import greymerk.roguelike.util.WeightedChoice;

public enum ItemNovelty {

    GREYMERK,
    NEBRISCROWN,
    NULL,
    MANPANTS,
    ZISTEAUSIGN,
    AVIDYA,
    ASHLEA,
    KURT,
    AMLP,
    CLEO,
    ENIKOSWORD,
    ENIKOBOW,
    BDOUBLEO,
    GUUDE,
    RLEAHY,
    ETHO,
    BAJ,
    DOCM,
    GINGER,
    VECHS,
    NOTCH,
    QUANTUMLEAP,
    GENERIKB,
    FOURLES,
    DINNERBONE,
    GRIM,
    MMILLSS,
    VALANDRAH;

    public static final Map<String, ItemNovelty> names;
    static {
        names = new HashMap<>();
        names.put("greymerk", ItemNovelty.GREYMERK);
        names.put("nebriscrown", ItemNovelty.NEBRISCROWN);
        names.put("nebrissword", ItemNovelty.NULL);
        names.put("zisteaupants", ItemNovelty.MANPANTS);
        names.put("zisteausign", ItemNovelty.ZISTEAUSIGN);
        names.put("avidya", ItemNovelty.AVIDYA);
        names.put("ashlea", ItemNovelty.ASHLEA);
        names.put("kurt", ItemNovelty.KURT);
        names.put("amlp", ItemNovelty.AMLP);
        names.put("cleo", ItemNovelty.CLEO);
        names.put("enikosword", ItemNovelty.ENIKOSWORD);
        names.put("enikobow", ItemNovelty.ENIKOBOW);
        names.put("bdoubleo", ItemNovelty.BDOUBLEO);
        names.put("guude", ItemNovelty.GUUDE);
        names.put("rleahy", ItemNovelty.RLEAHY);
        names.put("etho", ItemNovelty.ETHO);
        names.put("baj", ItemNovelty.BAJ);
        names.put("docm", ItemNovelty.DOCM);
        names.put("ginger", ItemNovelty.GINGER);
        names.put("vechs", ItemNovelty.VECHS);
        names.put("notch", ItemNovelty.NOTCH);
        names.put("quantumleap", ItemNovelty.QUANTUMLEAP);
        names.put("generikb", ItemNovelty.GENERIKB);
        names.put("fourles", ItemNovelty.FOURLES);
        names.put("dinnerbone", ItemNovelty.DINNERBONE);
        names.put("grim", ItemNovelty.GRIM);
        names.put("mmillss", ItemNovelty.MMILLSS);
        names.put("valandrah", ItemNovelty.VALANDRAH);
    }

    public static ItemStack getItemByName(String name) {
        if (!names.containsKey(name)) return null;
        return getItem(names.get(name));
    }

    public static IWeighted<ItemStack> get(ItemNovelty choice) {
        return new WeightedChoice<>(getItem(choice), 0);
    }

    public static ItemStack getItem(ItemNovelty choice) {

        ItemStack item;

        switch (choice) {

            case GREYMERK:
                item = new ItemStack(Items.iron_axe);
                Loot.setItemName(item, "Greymerk's Hatchet", null);
                Loot.setItemLore(item, "Pointlessly sharp", TextFormat.DARKGREEN);
                item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 3);
                item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
                item.addEnchantment(Enchantment.unbreaking, 2);
                return item;
            case NEBRISCROWN:
                item = new ItemStack(Items.golden_helmet);
                Loot.setItemName(item, "Nebrian Crown of Justice", null);
                Loot.setItemLore(item, "Adorned with precious gemstones", TextFormat.DARKGREEN);
                item.addEnchantment(Enchant.getEnchant(Enchant.PROTECTION), 4);
                item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
                return item;
            case NULL:
                item = new ItemStack(Items.diamond_sword);
                Loot.setItemName(item, "Null Pointer", null);
                Loot.setItemLore(item, "Exceptional", TextFormat.DARKGREEN);
                item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 5);
                item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 2);
                item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
                return item;
            case MANPANTS:
                item = new ItemStack(Items.leather_leggings);
                Loot.setItemName(item, "Man Pants", null);
                Loot.setItemLore(item, "Yessss, Manpants!", TextFormat.DARKGREEN);
                item.addEnchantment(Enchant.getEnchant(Enchant.FIREPROTECTION), 4);
                item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
                ItemArmour.dyeArmor(item, 250, 128, 114);
                return item;
            case ZISTEAUSIGN:
                item = new ItemStack(Items.sign);
                Loot.setItemName(item, "Battle Sign", TextFormat.DARKPURPLE);
                Loot.setItemLore(item, "\"That's what you get!\"", TextFormat.DARKGREEN);
                item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 5);
                item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 3);
                item.addEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), 1);
                return item;
            case AVIDYA:
                item = new ItemStack(Items.milk_bucket);
                Loot.setItemName(item, "White Russian", TextFormat.DARKPURPLE);
                Loot.setItemLore(item, "The dude's favourite", TextFormat.DARKGREEN);
                item.addEnchantment(Enchant.getEnchant(Enchant.ARTHOPODS), 4);
                item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
                item.addEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), 1);
                return item;
            case ASHLEA:
                item = new ItemStack(Items.cookie);
                Loot.setItemName(item, "Ashlea's Oatmeal Cookie", TextFormat.DARKPURPLE);
                Loot.setItemLore(item, "Perfect for elevensies", TextFormat.DARKGREEN);
                item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 2);
                item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
                return item;
            case KURT:
                item = new ItemStack(Items.leather_boots);
                Loot.setItemName(item, "Farland Travellers", null);
                Loot.setItemLore(item, "Indeed!", TextFormat.DARKGREEN);
                item.addEnchantment(Enchant.getEnchant(Enchant.PROTECTION), 3);
                item.addEnchantment(Enchant.getEnchant(Enchant.FEATHERFALLING), 2);
                item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
                ItemArmour.dyeArmor(item, 165, 42, 42);
                return item;
            case AMLP:
                item = new ItemStack(Items.shears);
                Loot.setItemName(item, "Lascerator", null);
                Loot.setItemLore(item, "The wool collector", TextFormat.DARKGREEN);
                item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 3);
                item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 2);
                item.addEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), 1);
                return item;
            case CLEO:
                item = new ItemStack(Items.fish);
                Loot.setItemName(item, "Cleophian Digging Feesh", TextFormat.DARKPURPLE);
                Loot.setItemLore(item, "Feesh are not efeeshent for digging", TextFormat.DARKGREEN);
                item.addEnchantment(Enchantment.efficiency, 10);
                item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 5);
                item.addEnchantment(Enchantment.fortune, 5);
                item.addEnchantment(Enchantment.unbreaking, 10);
                return item;
            case BDOUBLEO:
                item = new ItemStack(Items.diamond_shovel);
                Loot.setItemName(item, "Dig Job", null);
                Loot.setItemLore(item, "Recovered from hell's blazes", TextFormat.DARKGREEN);
                item.addEnchantment(Enchantment.efficiency, 5);
                item.addEnchantment(Enchantment.unbreaking, 3);
                return item;
            case GUUDE:
                item = new ItemStack(Items.record_13);
                Loot.setItemName(item, "Boulderfistian Golden Record", TextFormat.DARKPURPLE);
                Loot.setItemLore(item, "\"You're Watching Guude Boulderfist...\"", TextFormat.DARKGREEN);
                item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 3);
                item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
                item.addEnchantment(Enchantment.blastProtection, 3);
                return item;
            case RLEAHY:
                item = new ItemStack(Items.bread);
                Loot.setItemName(item, "Rleahian battle sub", TextFormat.DARKPURPLE);
                Loot.setItemLore(item, "With extra pastrami", TextFormat.DARKGREEN);
                item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 2);
                item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
                item.addEnchantment(Enchantment.fireAspect, 2);
                return item;
            case ETHO:
                item = new ItemStack(Items.wooden_pickaxe);
                Loot.setItemName(item, "Your Mum", null);
                Loot.setItemLore(item, "The original", TextFormat.DARKGREEN);
                item.addEnchantment(Enchantment.efficiency, 5);
                item.addEnchantment(Enchantment.unbreaking, 3);
                return item;
            case ENIKOBOW:
                item = new ItemStack(Items.bow);
                Loot.setItemName(item, "Eniko's String Theory", null);
                Loot.setItemLore(item, "For Science!", TextFormat.DARKGREEN);
                item.addEnchantment(Enchantment.power, 5);
                item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 2);
                item.addEnchantment(Enchantment.infinity, 1);
                item.addEnchantment(Enchantment.unbreaking, 3);
                return item;
            case ENIKOSWORD:
                item = new ItemStack(Items.diamond_sword);
                Loot.setItemName(item, "Eniko's Earring", null);
                Loot.setItemLore(item, "\"She do the loot take boogie\"", TextFormat.DARKGREEN);
                item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 5);
                item.addEnchantment(Enchantment.looting, 3);
                item.addEnchantment(Enchantment.unbreaking, 3);
                return item;
            case BAJ:
                item = new ItemStack(Items.golden_hoe);
                Loot.setItemName(item, "Baj's Last Resort", TextFormat.DARKPURPLE);
                Loot.setItemLore(item, "\"Starvation could be fatal\"", TextFormat.DARKGREEN);
                item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 2);
                item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
                item.addEnchantment(Enchantment.fortune, 5);
                return item;
            case DOCM:
                item = new ItemStack(Items.fishing_rod);
                Loot.setItemName(item, "Rod of Command", TextFormat.DARKPURPLE);
                Loot.setItemLore(item, "\"Get to the dang land!\"", TextFormat.DARKGREEN);
                item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 3);
                item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
                return item;
            case GINGER:
                item = new ItemStack(Items.cooked_chicken);
                Loot.setItemName(item, "Spice Chicken", TextFormat.DARKPURPLE);
                Loot.setItemLore(item, "\"Kung Pao!\"", TextFormat.DARKGREEN);
                item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
                item.addEnchantment(Enchantment.fireAspect, 3);
                item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 1);
                return item;
            case VECHS:
                item = new ItemStack(Items.stick);
                Loot.setItemName(item, "Legendary Stick", TextFormat.DARKPURPLE);
                Loot.setItemLore(item, "\"Really?!\"", TextFormat.DARKGREEN);
                item.addEnchantment(Enchantment.unbreaking, 1);
                return item;
            case NOTCH:
                item = new ItemStack(Items.apple);
                Loot.setItemName(item, "Notch's apple", TextFormat.DARKPURPLE);
                Loot.setItemLore(item, "Imbued with the creator's power", TextFormat.DARKGREEN);
                item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 10);
                item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 10);
                return item;
            case QUANTUMLEAP:
                item = new ItemStack(Blocks.sponge);
                Loot.setItemName(item, "QuantumLeap's Swiss Cheese", TextFormat.DARKPURPLE);
                Loot.setItemLore(item, "\"Oh boy\"", TextFormat.DARKGREEN);
                item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 4);
                return item;
            case GENERIKB:
                item = new ItemStack(Items.baked_potato);
                Loot.setItemName(item, "Hot Potato", TextFormat.DARKPURPLE);
                Loot.setItemLore(item, "All a hermit needs", TextFormat.DARKGREEN);
                item.addEnchantment(Enchantment.fireAspect, 3);
                item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 2);
                item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
                return item;
            case FOURLES:
                item = new ItemStack(Items.dye, 1, 3);
                Loot.setItemName(item, "Fourles Darkroast Beans", TextFormat.DARKPURPLE);
                Loot.setItemLore(item, "\"Mmmm... Dark Roast\"", TextFormat.DARKGREEN);
                item.addEnchantment(Enchantment.fireAspect, 2);
                item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 2);
                return item;
            case DINNERBONE:
                item = new ItemStack(Items.bone, 1);
                Loot.setItemName(item, "Old Dinnerbone", TextFormat.DARKPURPLE);
                Loot.setItemLore(item, "\"Dang Skellies!\"", TextFormat.DARKGREEN);
                item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 3);
                item.addEnchantment(Enchantment.fireAspect, 2);
                return item;
            case GRIM:
                item = new ItemStack(Items.rotten_flesh);
                Loot.setItemName(item, "Grim chew-toy", TextFormat.DARKPURPLE);
                Loot.setItemLore(item, "\"Come on Grim, let's do this!\"", TextFormat.DARKGREEN);
                item.addEnchantment(Enchant.getEnchant(Enchant.SMITE), 2);
                item.addEnchantment(Enchantment.looting, 1);
                return item;
            case MMILLSS:
                item = new ItemStack(Blocks.cactus);
                Loot.setItemName(item, "MMillssian spider bane", TextFormat.DARKPURPLE);
                Loot.setItemLore(item, "\"I really don't need anymore string...\"", TextFormat.DARKGREEN);
                item.addEnchantment(Enchant.getEnchant(Enchant.ARTHOPODS), 4);
                item.addEnchantment(Enchantment.thorns, 2);
                item.addEnchantment(Enchantment.looting, 1);
                return item;
            case VALANDRAH:
                item = new ItemStack(Items.iron_sword);
                Loot.setItemName(item, "Valandrah's Kiss", null);
                Loot.setItemLore(item, "\"Feel the kiss of my blade\"", TextFormat.DARKGREEN);
                item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 4);
                item.addEnchantment(Enchantment.fireAspect, 1);
                item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
                item.addEnchantment(Enchantment.unbreaking, 2);
                return item;
            default:
                return null;

        }
    }

}
