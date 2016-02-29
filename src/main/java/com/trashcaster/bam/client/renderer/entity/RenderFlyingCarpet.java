package com.trashcaster.bam.client.renderer.entity;

import java.awt.Point;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.glu.GLU;

import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.client.model.ModelFlyingCarpet;
import com.trashcaster.bam.entity.EntityFlyingCarpet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderFlyingCarpet extends Render<EntityFlyingCarpet> {
	public RenderFlyingCarpet() {
		super(Minecraft.getMinecraft().getRenderManager());
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
