/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.ChromatiCraft.Block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import Reika.ChromatiCraft.Base.BlockChromaTile;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaPlantHelper;

public class BlockChromaPlantTile extends BlockChromaTile {

	private final Icon[][] icons = new Icon[16][8];

	public BlockChromaPlantTile(int par1, Material xMaterial) {
		super(par1, xMaterial);
		this.setLightValue(0.5F);
		stepSound = soundGrassFootstep;
		blockHardness = 0;
		blockResistance = 0.5F;
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z) == 0 ? 9 : 0;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		return ReikaAABBHelper.getBlockAABB(x, y, z);
	}

	@Override
	public final boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return super.canPlaceBlockAt(world, x, y, z) && this.canBlockStay(world, x, y, z);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int par5)
	{
		super.onNeighborBlockChange(world, x, y, z, par5);
		this.checkFlowerChange(world, x, y, z);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random r) {
		this.checkFlowerChange(world, x, y, z);
	}

	private final void checkFlowerChange(World world, int x, int y, int z) {
		if (!this.canBlockStay(world, x, y, z)) {
			this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlock(x, y, z, 0, 0, 2);
		}
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		boolean light = world.getFullBlockLightValue(x, y, z) >= 8 || world.canBlockSeeTheSky(x, y, z);
		return ReikaPlantHelper.FLOWER.canPlantAt(world, x, y, z) && light;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public Icon getIcon(int s, int meta) {
		return icons[meta][0];
	}

	public Icon getPlantTexture(int meta, int offset) {
		return icons[meta][offset];
	}

	@Override
	public void registerIcons(IconRegister ico) {
		for (int i = 0; i < 16; i++) {
			for (int k = 0; k < 8; k++) {
				icons[i][k] = ico.registerIcon("chromaticraft:plant/"+i+"_"+k);
			}
		}
	}

}