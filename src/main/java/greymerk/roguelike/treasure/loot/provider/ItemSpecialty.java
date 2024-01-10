package greymerk.roguelike.treasure.loot.provider;

import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.util.TextFormat;

public class ItemSpecialty extends ItemBase {

    private Equipment type;
    private Quality quality;

    public ItemSpecialty(int weight, int level) {
        super(weight, level);
    }

    public ItemSpecialty(int weight, int level, Equipment type, Quality q) {
        super(weight, level);
        this.type = type;
        this.quality = q;
    }

    public ItemSpecialty(int weight, int level, Quality q) {
        super(weight, level);
        this.quality = q;
    }

    @Override
    public ItemStack get(Random rand) {
        if (this.type == null && quality != null) {
            return getRandomItem(Equipment.values()[rand.nextInt(Equipment.values().length)], rand, this.quality);
        }
        if (this.type == null || quality == null) {
            return getRandomItem(rand, this.level);
        }
        return getRandomItem(this.type, rand, this.quality);
    }

    public static ItemStack getRandomItem(Random rand, int level) {
        return getRandomItem(Equipment.values()[rand.nextInt(Equipment.values().length)], rand, level);
    }

    public static ItemStack getRandomItem(Equipment type, Random rand, int level) {
        return getRandomItem(type, rand, Quality.get(rand, level, type));
    }

    public static ItemStack getRandomItem(Equipment type, Random rand, Quality quality) {

        switch (type) {

            case SWORD:
                return getSword(rand, quality);
            case BOW:
                return getBow(rand, quality);
            case HELMET:
                return getHelmet(rand, quality);
            case CHEST:
                return getChest(rand, quality);
            case LEGS:
                return getLegs(rand, quality);
            case FEET:
                return getBoots(rand, quality);
            case PICK:
                return getPick(rand, quality);
            case AXE:
                return getAxe(rand, quality);
            case SHOVEL:
                return getShovel(rand, quality);
            default:
                return null;
        }
    }

    public static ItemStack getRandomArmour(Random rand, Quality quality) {
        switch (rand.nextInt(4)) {
            case 0:
                return getRandomItem(Equipment.HELMET, rand, quality);
            case 1:
                return getRandomItem(Equipment.CHEST, rand, quality);
            case 2:
                return getRandomItem(Equipment.LEGS, rand, quality);
            case 3:
                return getRandomItem(Equipment.FEET, rand, quality);
            default:
                return null;
        }
    }

    public static ItemStack getRandomTool(Random rand, Quality quality) {
        switch (rand.nextInt(3)) {
            case 0:
                return getRandomItem(Equipment.PICK, rand, quality);
            case 1:
                return getRandomItem(Equipment.AXE, rand, quality);
            case 2:
                return getRandomItem(Equipment.SHOVEL, rand, quality);
            default:
                return null;
        }
    }

    private static ItemStack getShovel(Random rand, Quality quality) {
        ItemStack item;
        if (quality == Quality.DIAMOND) {
            item = new ItemStack(Items.diamond_shovel);
            item.addEnchantment(Enchantment.efficiency, 3 + rand.nextInt(3));
            item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));
            Loot.setItemName(item, "Soulsand Spade");
        } else {
            item = new ItemStack(Items.iron_shovel);
            item.addEnchantment(Enchantment.efficiency, 1 + rand.nextInt(2));
            item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));
            Loot.setItemName(item, "Grave Spade");
        }
        return item;
    }

    private static ItemStack getAxe(Random rand, Quality quality) {

        ItemStack item;
        if (quality == Quality.DIAMOND) {
            item = new ItemStack(Items.diamond_axe);
            item.addEnchantment(Enchantment.efficiency, 3 + rand.nextInt(3));
            item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));
            Loot.setItemName(item, "Hellsteel Axe");
        } else {
            item = new ItemStack(Items.iron_axe);
            item.addEnchantment(Enchantment.efficiency, 1 + rand.nextInt(2));
            item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));
            Loot.setItemName(item, "Lumberjack's Hatchet");
        }
        return item;
    }

    private static ItemStack getPick(Random rand, Quality quality) {

        ItemStack item;

        if (quality == Quality.DIAMOND) {
            item = new ItemStack(Items.diamond_pickaxe);
            item.addEnchantment(Enchantment.efficiency, 3 + rand.nextInt(3));
            item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));
            if (rand.nextInt(10) == 0) {
                item.addEnchantment(Enchantment.silkTouch, 1);
                Loot.setItemName(item, "Crystal Pick of Precision");
                return item;
            }
            if (rand.nextInt(10) == 0) {
                item.addEnchantment(Enchantment.fortune, 2 + rand.nextInt(2));
                Loot.setItemName(item, "Crystal Pick of Prospecting");
                return item;
            }
            Loot.setItemName(item, "Crystal Pick");
        } else {
            item = new ItemStack(Items.iron_pickaxe);
            item.addEnchantment(Enchantment.efficiency, 1 + rand.nextInt(2));
            item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));
            if (rand.nextInt(10) == 0) {
                item.addEnchantment(Enchantment.silkTouch, 1);
                Loot.setItemName(item, "Case Hardened Pick of Precision");
                return item;
            }
            if (rand.nextInt(10) == 0) {
                item.addEnchantment(Enchantment.fortune, 1 + rand.nextInt(3));
                Loot.setItemName(item, "Case Hardened Pick of Prospecting");
                return item;
            }
            Loot.setItemName(item, "Case Hardened Pick");
        }
        return item;

    }

    private static ItemStack getSword(Random rand, Quality quality) {

        ItemStack item;
        if (quality == Quality.DIAMOND) {
            item = new ItemStack(Items.diamond_sword);
            item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 3 + rand.nextInt(3));
            if (rand.nextInt(10) == 0) {
                item.addEnchantment(Enchantment.looting, 2 + rand.nextInt(2));
                item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));
                Loot.setItemName(item, "Eldritch Blade of Plundering");
                Loot.setItemLore(item, "The loot taker", TextFormat.DARKGREEN);
                return item;
            }
            if (rand.nextInt(10) == 0) {
                item.addEnchantment(Enchantment.fireAspect, 2 + rand.nextInt(2));
                item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));
                Loot.setItemName(item, "Eldritch Blade of the Inferno");
                Loot.setItemLore(item, "From the fiery depths", TextFormat.DARKGREEN);
                return item;
            }
            item.addEnchantment(Enchantment.unbreaking, 3);
            Loot.setItemName(item, "Eldritch Blade");
            Loot.setItemLore(item, "Rune Etched", TextFormat.DARKGREEN);
        } else {
            item = new ItemStack(Items.iron_sword);
            if (rand.nextBoolean()) {
                item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 1);
            }
            item.addEnchantment(Enchantment.unbreaking, 3);
            Loot.setItemName(item, "Tempered Blade");
            Loot.setItemLore(item, "Highly Durable", TextFormat.DARKGREEN);
        }
        return item;

    }

    private static ItemStack getBow(Random rand, Quality quality) {

        ItemStack item = new ItemStack(Items.bow);

        switch (quality) {
            case WOOD:
            case STONE:
                item.addEnchantment(Enchantment.power, 1 + rand.nextInt(3));
                item.addEnchantment(Enchantment.unbreaking, 1);
                Loot.setItemName(item, "Yew Longbow");
                Loot.setItemLore(item, "Superior craftsmanship", TextFormat.DARKGREEN);
                return item;
            case IRON:
                item.addEnchantment(Enchantment.power, 1 + rand.nextInt(3));
                item.addEnchantment(Enchantment.unbreaking, 1 + rand.nextInt(3));
                Loot.setItemName(item, "Laminated Bow");
                Loot.setItemLore(item, "Highly polished", TextFormat.DARKGREEN);
                return item;
            case GOLD:
                item.addEnchantment(Enchantment.power, 3 + rand.nextInt(3));
                if (rand.nextBoolean()) {
                    item.addEnchantment(Enchantment.infinity, 1);
                }
                item.addEnchantment(Enchantment.unbreaking, 1 + rand.nextInt(3));
                Loot.setItemName(item, "Recurve Bow");
                Loot.setItemLore(item, "Beautifully crafted", TextFormat.DARKGREEN);
                return item;
            case DIAMOND:
                item.addEnchantment(Enchantment.power, 3 + rand.nextInt(3));
                item.addEnchantment(Enchantment.flame, 1);
                item.addEnchantment(Enchantment.infinity, 1);
                item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));
                Loot.setItemName(item, "Eldritch Bow");
                Loot.setItemLore(item, "Warm to the touch", TextFormat.DARKGREEN);
                return item;
            default:
                return null;
        }
    }

    private static ItemStack getHelmet(Random rand, Quality quality) {
        ItemStack item;

        String canonical = "";

        switch (quality) {
            case WOOD:
                item = new ItemStack(Items.leather_helmet);
                ItemArmour.dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
                canonical = "Bonnet";
                break;
            case STONE:
                item = new ItemStack(Items.chainmail_helmet);
                canonical = "Coif";
                break;
            case IRON:
            case GOLD:
                item = new ItemStack(Items.iron_helmet);
                canonical = "Sallet";
                break;
            case DIAMOND:
                item = new ItemStack(Items.diamond_helmet);
                canonical = "Helm";
                break;
            default:
                item = new ItemStack(Items.leather_helmet);
        }

        String suffix;

        if (rand.nextInt(20) == 0) {
            item.addEnchantment(Enchant.getEnchant(Enchant.PROTECTION), getProtectionLevel(quality, rand));
            item.addEnchantment(Enchant.getEnchant(Enchant.RESPIRATION), 3);
            item.addEnchantment(Enchantment.aquaAffinity, 1);
            suffix = "of Diving";
        } else if (rand.nextInt(3) == 0) {
            item.addEnchantment(Enchant.getEnchant(Enchant.PROJECTILEPROTECTION), getProtectionLevel(quality, rand));
            suffix = "of Deflection";
        } else {
            item.addEnchantment(Enchant.getEnchant(Enchant.PROTECTION), getProtectionLevel(quality, rand));
            suffix = "of Defense";
        }

        item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));

        String name = getArmourPrefix(quality) + " " + canonical + " " + suffix;
        Loot.setItemName(item, name);
        return item;
    }

    private static ItemStack getBoots(Random rand, Quality quality) {
        ItemStack item;

        String canonical;

        switch (quality) {
            case WOOD:
                item = new ItemStack(Items.leather_boots);
                ItemArmour.dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
                canonical = "Shoes";
                break;
            case STONE:
                item = new ItemStack(Items.chainmail_boots);
                canonical = "Greaves";
                break;
            case IRON:
            case GOLD:
                item = new ItemStack(Items.iron_boots);
                canonical = "Sabatons";
                break;
            case DIAMOND:
                item = new ItemStack(Items.diamond_boots);
                canonical = "Boots";
                break;
            default:
                item = new ItemStack(Items.leather_boots);
                canonical = "Shoes";
        }

        String suffix;

        if (rand.nextInt(10) == 0) {
            item.addEnchantment(Enchantment.fireProtection, getProtectionLevel(quality, rand));
            suffix = "of Warding";
        } else if (rand.nextInt(5) == 0) {
            item.addEnchantment(Enchant.getEnchant(Enchant.PROTECTION), getProtectionLevel(quality, rand));
            item.addEnchantment(
                    Enchant.getEnchant(Enchant.FEATHERFALLING),
                    quality == Quality.DIAMOND ? 4 : 1 + rand.nextInt(3));
            suffix = "of Lightness";
        } else if (rand.nextInt(3) == 0) {
            item.addEnchantment(Enchant.getEnchant(Enchant.PROJECTILEPROTECTION), getProtectionLevel(quality, rand));
            suffix = "of Deflection";
        } else {
            item.addEnchantment(Enchant.getEnchant(Enchant.PROTECTION), getProtectionLevel(quality, rand));
            suffix = "of Defense";
        }

        item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));

        String name = getArmourPrefix(quality) + " " + canonical + " " + suffix;
        Loot.setItemName(item, name);
        return item;
    }

    private static ItemStack getLegs(Random rand, Quality quality) {
        ItemStack item;

        String canonical = "";

        switch (quality) {
            case WOOD:
                item = new ItemStack(Items.leather_leggings);
                ItemArmour.dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
                canonical = "Pantaloons";
                break;
            case STONE:
                item = new ItemStack(Items.chainmail_leggings);
                canonical = "Chausses";
                break;
            case IRON:
            case GOLD:
                item = new ItemStack(Items.iron_leggings);
                canonical = "Leg-plates";
                break;
            case DIAMOND:
                item = new ItemStack(Items.diamond_leggings);
                canonical = "Leggings";
                break;
            default:
                item = new ItemStack(Items.leather_leggings);
        }

        String suffix;

        if (rand.nextInt(10) == 0) {
            item.addEnchantment(Enchantment.fireProtection, getProtectionLevel(quality, rand));
            suffix = "of Warding";
        } else if (rand.nextInt(10) == 0) {
            item.addEnchantment(Enchantment.blastProtection, getProtectionLevel(quality, rand));
            suffix = "of Integrity";
        } else if (rand.nextInt(3) == 0) {
            item.addEnchantment(Enchant.getEnchant(Enchant.PROJECTILEPROTECTION), getProtectionLevel(quality, rand));
            suffix = "of Deflection";
        } else {
            item.addEnchantment(Enchant.getEnchant(Enchant.PROTECTION), getProtectionLevel(quality, rand));
            suffix = "of Defense";
        }

        item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));

        String name = getArmourPrefix(quality) + " " + canonical + " " + suffix;
        Loot.setItemName(item, name);
        return item;
    }

    private static ItemStack getChest(Random rand, Quality quality) {
        ItemStack item;

        String canonical;

        switch (quality) {
            case WOOD:
                item = new ItemStack(Items.leather_chestplate);
                ItemArmour.dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
                canonical = "Tunic";
                break;
            case STONE:
                item = new ItemStack(Items.chainmail_chestplate);
                canonical = "Hauberk";
                break;
            case IRON:
            case GOLD:
                item = new ItemStack(Items.iron_chestplate);
                canonical = "Cuirass";
                break;
            case DIAMOND:
                item = new ItemStack(Items.diamond_chestplate);
                canonical = "Plate";
                break;
            default:
                item = new ItemStack(Items.leather_chestplate);
                canonical = "Tunic";
        }

        String suffix;

        if (rand.nextInt(10) == 0) {
            item.addEnchantment(Enchantment.fireProtection, getProtectionLevel(quality, rand));
            suffix = "of Flamewarding";
        } else if (rand.nextInt(10) == 0) {
            item.addEnchantment(Enchantment.blastProtection, getProtectionLevel(quality, rand));
            suffix = "of Integrity";
        } else if (rand.nextInt(3) == 0) {
            item.addEnchantment(Enchant.getEnchant(Enchant.PROJECTILEPROTECTION), getProtectionLevel(quality, rand));
            suffix = "of Deflection";
        } else {
            item.addEnchantment(Enchant.getEnchant(Enchant.PROTECTION), getProtectionLevel(quality, rand));
            suffix = "of Defense";
        }

        item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));

        String name = getArmourPrefix(quality) + " " + canonical + " " + suffix;
        Loot.setItemName(item, name);
        return item;
    }

    private static int getUnbreakingLevel(Quality quality, Random rand) {
        return quality == Quality.DIAMOND ? 3 : 1 + rand.nextInt(2);
    }

    private static int getProtectionLevel(Quality quality, Random rand) {

        int value = 1;

        switch (quality) {
            case WOOD:
                if (rand.nextInt(3) == 0) {
                    value++;
                }
                break;
            case STONE:
                if (rand.nextBoolean()) {
                    value++;
                }
                break;
            case IRON:
            case GOLD:
                value += rand.nextInt(3);
                break;
            case DIAMOND:
                value += 2 + rand.nextInt(2);
                break;
        }

        return value;
    }

    private static String getArmourPrefix(Quality quality) {

        switch (quality) {
            case WOOD:
                return "Surplus";
            case STONE:
                return "Riveted";
            case IRON:
                return "Gothic";
            case GOLD:
                return "Jewelled";
            case DIAMOND:
                return "Shiny";
            default:
                return "Strange";
        }
    }

    @Override
    public ItemStack getLootItem(Random rand, int level) {

        Quality q;
        switch (level) {
            case 0:
                q = Quality.WOOD;
                break;
            case 1:
                q = Quality.STONE;
                break;
            case 2:
                q = Quality.IRON;
                break;
            case 3:
                q = Quality.GOLD;
                break;
            case 4:
                q = Quality.DIAMOND;
                break;
            default:
                q = Quality.WOOD;
                break;
        }

        return getRandomItem(Equipment.values()[rand.nextInt(Equipment.values().length)], rand, q);
    }
}
