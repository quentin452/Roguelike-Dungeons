package greymerk.roguelike.dungeon;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import greymerk.roguelike.Roguelike;
import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.dungeon.settings.SettingsResolver;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.treasure.loot.Book;
import greymerk.roguelike.treasure.loot.ILoot;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class Dungeon implements IDungeon {

    public static SettingsResolver settingsResolver;

    private final DungeonGenerator generator;
    private Coord pos;
    private final IWorldEditor editor;

    static {
        initResolver();
    }

    public static void initResolver() {
        settingsResolver = new SettingsResolver();
    }

    public Dungeon(IWorldEditor editor) {
        this.generator = new DungeonGenerator();
        this.editor = editor;
    }

    public void generateNear(Random rand, int x, int z) {
        int attempts = 50;

        for (int i = 0; i < attempts; i++) {
            Coord location = getNearbyCoord(rand, x, z, 40, 100);

            if (!validLocation(location.getX(), location.getZ())) continue;

            ISettings setting = settingsResolver.getSettings(editor, rand, location);

            if (setting == null) return;

            generate(setting, location.getX(), location.getZ());
            return;
        }
    }

    public void generate(ISettings settings, int inX, int inZ) {
        generator.generate(editor, settings, inX, inZ);
        this.pos = new Coord(inX, 50, inZ);

        Random rand = getRandom(editor, this.pos.getX(), this.pos.getZ());
        TreasureManager treasure = editor.getTreasure();
        ILoot loot = Loot.getLoot();
        settings.getLootRules().process(rand, loot, treasure);

        Book book = new Book("Greymerk", "Statistics");
        book.addPage(
                "~Architect's Resource Notes~\n\n" + "StoneBrick: "
                        + editor.getStat(Blocks.stonebrick)
                        + "\n"
                        + "Cobblestone: "
                        + editor.getStat(Blocks.cobblestone)
                        + "\n"
                        + "Logs: "
                        + (editor.getStat(Blocks.log) + editor.getStat(Blocks.log2))
                        + "\n"
                        + "Iron Bars: "
                        + editor.getStat(Blocks.iron_bars)
                        + "\n"
                        + "Chests: "
                        + (editor.getStat(Blocks.chest) + editor.getStat(Blocks.trapped_chest))
                        + "\n"
                        + "Mob Spawners: "
                        + editor.getStat(Blocks.mob_spawner)
                        + "\n"
                        + "TNT: "
                        + editor.getStat(Blocks.tnt)
                        + "\n"
                        + "\n-Greymerk");
        book.addPage(
                "Roguelike Dungeons v" + Roguelike.version
                        + "\n"
                        + "April 16th 2016\n\n"
                        + "Credits\n\n"
                        + "Author: Greymerk\n\n"
                        + "Bits: Drainedsoul\n\n"
                        + "Ideas: Eniko @enichan");
        treasure.addItemToAll(rand, Treasure.STARTER, new WeightedChoice<>(book.get(), 1), 1);
    }

    public static boolean canSpawnInChunk(int chunkX, int chunkZ, IWorldEditor editor) {

        if (!RogueConfig.getBoolean(RogueConfig.DONATURALSPAWN)) {
            return false;
        }

        int frequency = RogueConfig.getInt(RogueConfig.SPAWNFREQUENCY);
        int min = 8 * frequency / 10;
        int max = 32 * frequency / 10;

        min = Math.max(min, 2);
        max = Math.max(max, 8);

        int tempX = chunkX < 0 ? chunkX - (max - 1) : chunkX;
        int tempZ = chunkZ < 0 ? chunkZ - (max - 1) : chunkZ;

        int m = tempX / max;
        int n = tempZ / max;

        Random r = editor.getSeededRandom(m, n, 10387312);

        m *= max;
        n *= max;

        m += r.nextInt(max - min);
        n += r.nextInt(max - min);

        return chunkX == m && chunkZ == n;
    }

    public void spawnInChunk(Random rand, int chunkX, int chunkZ) {
        if (Dungeon.canSpawnInChunk(chunkX, chunkZ, editor)) {
            int x = chunkX * 16 + 4;
            int z = chunkZ * 16 + 4;
            generateNear(rand, x, z);
        }
    }

    public static int getLevel(int y) {

        if (y < 15) return 4;
        if (y < 25) return 3;
        if (y < 35) return 2;
        if (y < 45) return 1;
        return 0;
    }

    public boolean validLocation(int x, int z) {
        if (!isValidBiome(x, z)) {
            return false;
        }

        int upperLimit = RogueConfig.getInt(RogueConfig.UPPERLIMIT);
        int lowerLimit = RogueConfig.getInt(RogueConfig.LOWERLIMIT);

        Coord cursor = new Coord(x, upperLimit, z);

        if (!isAirBlockValid(cursor) || !isGroundValid(cursor, lowerLimit)) {
            return false;
        }

        return isAreaValid(cursor);
    }

    private boolean isValidBiome(int x, int z) {
        BiomeGenBase biome = editor.getBiome(new Coord(x, 0, z));
        Type[] biomeType = BiomeDictionary.getTypesForBiome(biome);
        Type[] invalidBiomes = new Type[] { BiomeDictionary.Type.RIVER, BiomeDictionary.Type.BEACH,
            BiomeDictionary.Type.MUSHROOM, BiomeDictionary.Type.OCEAN };

        for (Type type : invalidBiomes) {
            if (Arrays.asList(biomeType).contains(type)) {
                return false;
            }
        }
        return true;
    }

    private boolean isAirBlockValid(Coord cursor) {
        return editor.isAirBlock(cursor);
    }

    private boolean isGroundValid(Coord cursor, int lowerLimit) {
        while (!editor.validGroundBlock(cursor)) {
            cursor.add(Cardinal.DOWN);
            if (cursor.getY() < lowerLimit || editor.getBlock(cursor).getMaterial() == Material.water) {
                return false;
            }
        }
        return true;
    }

    private boolean isAreaValid(Coord cursor) {
        int airCount = 0;
        for (Coord c : new RectSolid(
            new Coord(cursor.getX() - 4, cursor.getY() - 3, cursor.getZ() - 4),
            new Coord(cursor.getX() + 4, cursor.getY() - 3, cursor.getZ() + 4))) {
            if (!editor.validGroundBlock(c)) {
                airCount++;
            }
            if (airCount > 8) {
                return false;
            }
        }
        return true;
    }

    public static Coord getNearbyCoord(Random rand, int x, int z, int min, int max) {

        int distance = min + rand.nextInt(max - min);

        double angle = rand.nextDouble() * 2 * Math.PI;

        int xOffset = (int) (Math.cos(angle) * distance);
        int zOffset = (int) (Math.sin(angle) * distance);

        return new Coord(x + xOffset, 0, z + zOffset);
    }

    public static Random getRandom(IWorldEditor editor, int x, int z) {
        long seed = editor.getSeed() * x * z;
        Random rand = new Random();
        rand.setSeed(seed);
        return rand;
    }

    @Override
    public List<ITreasureChest> getChests() {
        return this.editor.getTreasure().getChests();
    }

    public Coord getPosition() throws Exception {
        if (this.pos == null) {
            throw new Exception("Dungeon not yet generated");
        }
        return this.pos;
    }
}
