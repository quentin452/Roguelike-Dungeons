package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;

public interface ITheme {

    IBlockFactory getPrimaryFloor();

    IBlockFactory getPrimaryWall();

    IStair getPrimaryStair();

    IBlockFactory getPrimaryPillar();

    IBlockFactory getSecondaryFloor();

    IBlockFactory getSecondaryWall();

    IStair getSecondaryStair();

    IBlockFactory getSecondaryPillar();

}
