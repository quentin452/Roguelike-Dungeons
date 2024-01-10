package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;

public interface IBlockSet {

    IBlockFactory getFloor();

    IBlockFactory getFill();

    IStair getStair();

    IBlockFactory getPillar();

}
