package com.trashcaster.bam.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;

import com.trashcaster.bam.entity.EntityFlyingCarpet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

public class RenderFlyingCarpet extends Render<EntityFlyingCarpet> {
	public RenderFlyingCarpet(RenderManager renderManager) {
		super(renderManager);
		this.shadowSize = 1.25f;
	}

	public void doRender(EntityFlyingCarpet entity, double x, double y, double z, float p_76986_8_, float partialTicks) {
        float f5 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks;
        double d6 = entity.prevOffsetY + (entity.offsetY - entity.prevOffsetY)*partialTicks;
		
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y+0.35f+d6, (float) z);
		
		GlStateManager.scale(-1d, -1d, 1d);
		GlStateManager.rotate(f5-180f, 0, 1, 0);
		this.bindTexture(TextureMap.locationBlocksTexture);
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		GL11.glRotatef((float)Math.sin(Math.toRadians(System.currentTimeMillis()/4 % 360))*2f, 0, 0, 1);
		GL11.glRotatef((float)Math.cos(Math.toRadians(System.currentTimeMillis()/4 % 360))*2f, 1, 0, 0);
		if (entity.getEntityItem() != null && entity.deathTime == 0) {
		    renderItem.renderItem(entity.getEntityItem(), TransformType.HEAD);
		}
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityFlyingCarpet entity) {
		return null;
	}
}
