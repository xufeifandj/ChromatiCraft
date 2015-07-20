package Reika.ChromatiCraft.Auxiliary.Interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import Reika.ChromatiCraft.Base.DimensionStructureGenerator;
import Reika.ChromatiCraft.Base.DimensionStructureGenerator.DimensionStructureType;

public abstract class StructureData {

	protected final DimensionStructureGenerator generator;

	protected StructureData(DimensionStructureGenerator gen) {
		generator = gen;
	}

	public final DimensionStructureType getType() {
		return generator.getType();
	}

	public abstract void load();

	public void onInteract(World world, int x, int y, int z, EntityPlayer ep, int s) {

	}

}
