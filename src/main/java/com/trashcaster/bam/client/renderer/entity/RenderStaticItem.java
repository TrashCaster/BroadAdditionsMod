package com.trashcaster.bam.client.renderer.entity;

import java.awt.Color;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.trashcaster.bam.client.ClientProxy;
import com.trashcaster.bam.entity.item.EntityStaticItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;

public class RenderStaticItem extends Render {

	RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

	public RenderStaticItem() {
		super(Minecraft.getMinecraft().getRenderManager());
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}

	public void doRender(EntityStaticItem entity, double x, double y, double z, float p_76986_8_, float partialTicks) {
		MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y, (float) z);
		GlStateManager.scale(-1d, -1d, 1d);
		GlStateManager.disableLighting();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		if (entity.getItem() != null) {
	        float f5 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
	        //float f6 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks;
			//GlStateManager.rotate(-f6, 0f, 1f, 0f);
	        GlStateManager.rotate(p_76986_8_, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(-f5, 1f, 0f, 0f);
			if (!this.renderItem.getItemModelMesher().getItemModel(entity.getItem()).isGui3d()) {
				GlStateManager.rotate(90f, 1f, 0f, 0f);
			} else {
				GlStateManager.translate(0, 0.125f, 0);
			}

			IBakedModel ibakedmodel = renderItem.getItemModelMesher().getItemModel(entity.getItem());

			this.bindTexture(TextureMap.locationBlocksTexture);
			GlStateManager.pushMatrix();
			this.renderItem.renderItem(entity.getItem(), ibakedmodel);
			GlStateManager.popMatrix();

			if (mop != null && mop.entityHit == entity) {
				GlStateManager.pushMatrix();
				GlStateManager.disableLighting();
				GlStateManager.depthMask(false);
				GlStateManager.depthFunc(514);
				GlStateManager.blendFunc(768, 774);
				this.bindTexture(ClientProxy.HIGHLIGHT_TINT);
				GL11.glScalef(2f, 2f, 2f);
				this.renderItem.renderItem(entity.getItem(), ibakedmodel);
				GlStateManager.enableLighting();
				GlStateManager.blendFunc(770, 771);
				GlStateManager.depthFunc(515);
				GlStateManager.depthMask(true);
				GlStateManager.popMatrix();
			}
		}
		GlStateManager.enableLighting();
		GlStateManager.disableAlpha();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks) {
		this.doRender((EntityStaticItem) entity, x, y, z, p_76986_8_, partialTicks);
		super.doRender(entity, x, y, z, p_76986_8_, partialTicks);
	}
}
