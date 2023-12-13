package greymerk.roguelike.worldgen;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import net.minecraft.world.chunk.Chunk;

public class WorldEditor implements IWorldEditor {

    private static final Block CHEST_BLOCK = Blocks.chest;
    private static final Block TRAPPED_CHEST_BLOCK = Blocks.trapped_chest;
    private static final Block MOB_SPAWNER_BLOCK = Blocks.mob_spawner;

    World world;
    private final Map<Coord, MetaBlock> blockCache;
    private final Map<Block, Integer> stats;
    private final TreasureManager chests;

    public WorldEditor(World world) {
        this.world = world;
        stats = new HashMap<>();
        this.blockCache = new HashMap<>();
        this.chests = new TreasureManager();
    }

    public boolean setBlock(Coord pos, MetaBlock block, boolean fillAir, boolean replaceSolid) {
        if (!isChunkLoaded(pos)) {
            return false;
        }

        if (isInvalidBlock(pos)) {
            return false;
        }

        if (!canSetBlock(pos, fillAir, replaceSolid)) {
            return false;
        }

        performBlockPlacement(pos, block);
        updateStats(block.getBlock());

        return true;
    }

    private boolean isChunkLoaded(Coord pos) {
        int chunkX = pos.getX() >> 4;
        int chunkZ = pos.getZ() >> 4;
        return world.getChunkProvider().chunkExists(chunkX, chunkZ);
    }

    private boolean isInvalidBlock(Coord pos) {
        MetaBlock currentBlock = getBlock(pos);
        return currentBlock.getBlock() == CHEST_BLOCK || currentBlock.getBlock() == TRAPPED_CHEST_BLOCK || currentBlock.getBlock() == MOB_SPAWNER_BLOCK;
    }

    private boolean canSetBlock(Coord pos, boolean fillAir, boolean replaceSolid) {
        boolean isAir = world.isAirBlock(pos.getX(), pos.getY(), pos.getZ());
        return (fillAir || !isAir) && (replaceSolid || isAir);
    }

    private void performBlockPlacement(Coord pos, MetaBlock block) {
        int chunkX = pos.getX() >> 4;
        int chunkZ = pos.getZ() >> 4;
        Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);

        if (chunk != null && chunk.isChunkLoaded) {
            world.setBlock(pos.getX(), pos.getY(), pos.getZ(), block.getBlock(), block.getMeta(), block.getFlag());
        }
    }

    private void updateStats(Block block) {
        stats.merge(block, 1, Integer::sum);
    }

    public boolean isAirBlock(Coord pos) {
        int chunkX = pos.getX() >> 4;
        int chunkZ = pos.getZ() >> 4;

        if (!world.getChunkProvider().chunkExists(chunkX, chunkZ)) {
            return true;
        }

        return world.isAirBlock(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public long getSeed() {
        return this.world.getSeed();
    }

    @Override
    public BiomeGenBase getBiome(Coord pos) {
        return world.getBiomeGenForCoords(pos.getX(), pos.getZ());
    }

    @Override
    public int getDimension() {
        return world.provider.dimensionId;
    }

    @Override
    public Random getSeededRandom(int a, int b, int c) {
        return world.setRandomSeed(a, b, c);
    }

    @Override
    public void spiralStairStep(Random rand, Coord origin, IStair stair, IBlockFactory fill) {

        MetaBlock air = new MetaBlock(Blocks.air);
        Coord cursor;
        Coord start;
        Coord end;

        start = new Coord(origin);
        start.add(new Coord(-1, 0, -1));
        end = new Coord(origin);
        end.add(new Coord(1, 0, 1));

        RectSolid.fill(this, rand, start, end, air);
        fill.set(this, rand, origin);

        Cardinal dir = Cardinal.directions[origin.getY() % 4];
        cursor = new Coord(origin);
        cursor.add(dir);
        stair.setOrientation(Cardinal.left(dir), false).set(this, cursor);
        cursor.add(Cardinal.right(dir));
        stair.setOrientation(Cardinal.right(dir), true).set(this, cursor);
        cursor.add(Cardinal.reverse(dir));
        stair.setOrientation(Cardinal.reverse(dir), true).set(this, cursor);
    }

    @Override
    public void fillDown(Random rand, Coord origin, IBlockFactory blocks) {

        Coord cursor = new Coord(origin);

        while (!getBlock(cursor).getBlock().isOpaqueCube() && cursor.getY() > 1) {
            blocks.set(this, rand, cursor);
            cursor.add(Cardinal.DOWN);
        }
    }

    @Override
    public MetaBlock getBlock(Coord pos) {
        int chunkX = pos.getX() >> 4;
        int chunkZ = pos.getZ() >> 4;

        if (!world.getChunkProvider().chunkExists(chunkX, chunkZ)) {
            return new MetaBlock(Blocks.air);
        }

        return blockCache.computeIfAbsent(pos, p -> {
            Block block = world.getBlock(p.getX(), p.getY(), p.getZ());
            return new MetaBlock(block);
        });
    }

    @Override
    public TileEntity getTileEntity(Coord pos) {
        return world.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public boolean validGroundBlock(Coord pos) {

        if (isAirBlock(pos)) return false;

        MetaBlock block = this.getBlock(pos);

        if (block.getMaterial() == Material.wood) return false;
        if (block.getMaterial() == Material.water) return false;
        if (block.getMaterial() == Material.cactus) return false;
        if (block.getMaterial() == Material.snow) return false;
        if (block.getMaterial() == Material.grass) return false;
        if (block.getMaterial() == Material.gourd) return false;
        if (block.getMaterial() == Material.leaves) return false;
        return block.getMaterial() != Material.plants;
    }

    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder();

        for (Map.Entry<Block, Integer> pair : stats.entrySet()) {
            toReturn.append(pair.getKey().getLocalizedName()).append(": ").append(pair.getValue()).append("\n");
        }

        return toReturn.toString();
    }

    @Override
    public int getStat(Block type) {
        if (!this.stats.containsKey(type)) return 0;
        return this.stats.get(type);
    }

    @Override
    public void addChest(ITreasureChest toAdd) {
        this.chests.add(toAdd);
    }

    @Override
    public TreasureManager getTreasure() {
        return this.chests;
    }

    @Override
    public boolean canPlace(MetaBlock block, Coord pos, Cardinal dir) {
        if (!this.isAirBlock(pos)) return false;
        Coord cursor = new Coord(pos);
        cursor.add(dir);
        Material m = this.getBlock(cursor).getMaterial();
        return !m.isReplaceable();
    }

    @Override
    public void setBlockMetadata(Coord pos, int meta) {
        world.setBlockMetadataWithNotify(pos.getX(), pos.getY(), pos.getZ(), meta, 2);
    }
}
