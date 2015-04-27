/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.ChromatiCraft.Base;

import java.util.Random;

import net.minecraft.world.World;
import Reika.ChromatiCraft.ChromatiCraft;
import Reika.ChromatiCraft.Registry.CrystalElement;
import Reika.ChromatiCraft.World.Dimension.Structure.AltarGenerator;
import Reika.ChromatiCraft.World.Dimension.Structure.LocksGenerator;
import Reika.ChromatiCraft.World.Dimension.Structure.ShiftMazeGenerator;
import Reika.ChromatiCraft.World.Dimension.Structure.ThreeDMazeGenerator;
import Reika.DragonAPI.Exception.RegistrationException;

public abstract class DimensionStructureGenerator {

	public DimensionStructureGenerator() {

	}

	/** chunk X and Z are already *16 */
	public abstract void generate(World world, int chunkX, int chunkZ, CrystalElement e, Random rand);

	public static class StructurePair {

		public final DimensionStructureType generator;
		public final CrystalElement color;

		public StructurePair(DimensionStructureType gen, CrystalElement e) {
			generator = gen;
			color = e;
		}

	}

	public static enum DimensionStructureType {

		TDMAZE(ThreeDMazeGenerator.class),
		ALTAR(AltarGenerator.class),
		SHIFTMAZE(ShiftMazeGenerator.class),
		LOCKS(LocksGenerator.class);

		private final Class generatorClass;
		private DimensionStructureGenerator generator;

		public static final DimensionStructureType[] types = values();

		private DimensionStructureType(Class<? extends DimensionStructureGenerator> c) {
			generatorClass = c;
		}

		public DimensionStructureGenerator getGenerator() {
			if (generator == null) {
				try {
					generator = (DimensionStructureGenerator)generatorClass.newInstance();
				}
				catch (Exception e) {
					throw new RegistrationException(ChromatiCraft.instance, "Error initializing structure type "+this);
				}
			}
			return generator;
		}

	}

}