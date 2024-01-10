package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;

public class Firework {

    public static ItemStack get(Random rand, int stackSize) {

        ItemStack rocket = new ItemStack(Items.fireworks, stackSize);

        NBTTagCompound tag = new NBTTagCompound();
        NBTTagCompound fireworks = new NBTTagCompound();

        fireworks.setByte("Flight", (byte) (rand.nextInt(3) + 1));

        NBTTagList explosion = new NBTTagList();

        NBTTagCompound properties = new NBTTagCompound();
        properties.setByte("Flicker", (byte) (rand.nextBoolean() ? 1 : 0));
        properties.setByte("Trail", (byte) (rand.nextBoolean() ? 1 : 0));
        properties.setByte("Type", (byte) (rand.nextInt(5)));

        int size = rand.nextInt(4) + 1;
        int[] colorArr = new int[size];
        for (int i = 0; i < size; ++i) {
            colorArr[i] = rand.nextInt(256) << 16 | rand.nextInt(255) << 8 | rand.nextInt(255);
        }

        NBTTagIntArray colors = new NBTTagIntArray(colorArr);
        properties.setTag("Colors", colors);

        explosion.appendTag(properties);
        fireworks.setTag("Explosions", explosion);
        tag.setTag("Fireworks", fireworks);

        rocket.setTagCompound(tag);

        return rocket;
    }
}
