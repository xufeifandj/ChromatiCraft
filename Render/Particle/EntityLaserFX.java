/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.ChromatiCraft.Render.Particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import Reika.ChromatiCraft.ChromatiCraft;
import Reika.ChromatiCraft.Registry.CrystalElement;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaGLHelper.BlendMode;

public class EntityLaserFX extends EntityFX {

	private final CrystalElement element;

	public EntityLaserFX(CrystalElement e, World world, double x, double y, double z) {
		this(e, world, x, y, z, 0, 0, 0);
	}

	public EntityLaserFX(CrystalElement e, World world, double x, double y, double z, double vx, double vy, double vz) {
		super(world, x, y, z);
		element = e;
		particleMaxAge = 63;
		noClip = true;
		motionX = vx;
		motionY = vy;
		motionZ = vz;
		particleScale = 1F;
		particleGravity = 0.0005F;
		particleTextureIndexY = 0;
		particleRed = e.color.getRed()/192F;
		particleGreen = e.color.getGreen()/192F;
		particleBlue = e.color.getBlue()/192F;
	}

	public EntityLaserFX setScale(float f) {
		particleScale = f;
		return this;
	}

	@Override
	public void renderParticle(Tessellator v5, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		v5.draw();
		ReikaTextureHelper.bindTexture(ChromatiCraft.class, "Textures/Particle/64x.png");
		BlendMode.ADDITIVEDARK.apply();
		GL11.glColor4f(1, 1, 1, 1);
		v5.startDrawingQuads();
		v5.setBrightness(this.getBrightnessForRender(0));
		super.renderParticle(v5, par2, par3, par4, par5, par6, par7);
		v5.draw();
		BlendMode.DEFAULT.apply();
		v5.startDrawingQuads();
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (particleAge < 16) {
			particleTextureIndexX = particleAge;
			particleTextureIndexY = 0;
		}
		else if (particleAge < 32) {
			particleTextureIndexX = particleAge-16;
			particleTextureIndexY = 1;
		}
		else if (particleAge < 48) {
			particleTextureIndexX = 15-particleAge%16;
			particleTextureIndexY = 1;
		}
		else {
			particleTextureIndexX = 15-particleAge%16;
			particleTextureIndexY = 0;
		}
	}

	@Override
	public int getBrightnessForRender(float par1)
	{
		return 240;
	}

	@Override
	public int getFXLayer()
	{
		return 2;
	}

}
