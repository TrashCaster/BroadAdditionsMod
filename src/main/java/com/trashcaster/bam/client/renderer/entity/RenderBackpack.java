package com.trashcaster.bam.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;

import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.client.ClientProxy;
import com.trashcaster.bam.client.model.ModelBackpack;
import com.trashcaster.bam.entity.item.EntityBackpack;
import com.trashcaster.bam.entity.item.EntityStaticItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;

public class RenderBackpack extends Render<EntityBackpack> {

	private final ModelBackpack model = new ModelBackpack();
	
	private final static ResourceLocation texture = new ResourceLocation(BroadAdditionsMod.MODID, "textures/entity/accessories/backpack.png");
	

	public RenderBackpack(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBackpack entity) {
		return texture;
	}

	public void doRender(EntityBackpack entity, double x, double y, double z, float p_76986_8_, float partialTicks) {
		float yaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw)*partialTicks;
		GlStateManager.pushMatrix();
		GlStateManager.disableCull();
		GlStateManager.translate((float) x, (float) y+0.775d, (float) z);
		GlStateManager.scale(-1d, -1d, 1d);
		GlStateManager.rotate(yaw+180f, 0, 1, 0);
		GlStateManager.translate(0, 0, -0.125f);
		this.bindEntityTexture(entity);
		model.render(entity, 0, 0, 0, 0, 0, 0.0625f);
		GlStateManager.enableCull();
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, p_76986_8_, partialTicks);
	}
}
