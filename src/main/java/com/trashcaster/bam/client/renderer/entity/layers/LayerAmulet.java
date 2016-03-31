package com.trashcaster.bam.client.renderer.entity.layers;

import java.awt.Color;

import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.client.model.ModelBackpack;
import com.trashcaster.bam.entity.PlayerData;
import com.trashcaster.bam.inventory.InventoryAccessories;
import com.trashcaster.bam.item.ItemAccessory;
import com.trashcaster.bam.util.Content;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class LayerAmulet implements LayerRenderer<EntityPlayer> {
	// bring me 1-1-5!
	private final static double EPSILON = 0.00115d;
	private final RenderPlayer renderer;
	private static final ResourceLocation texture = new ResourceLocation(BroadAdditionsMod.MODID, "textures/entity/accessories/backpack.png");

	public LayerAmulet(RenderPlayer renderer) {
		this.renderer = renderer;
	}

	@Override
	// entity, limb stuff, limb stuff, partial ticks, yaw stuff, pitch stuff, 0.0625f
	public void doRenderLayer(EntityPlayer entity, float f, float f1, float partialTicks,
			float f2, float f3, float f4, float scale) {
		
		if (PlayerData.get(entity) != null) {
			InventoryAccessories inv = PlayerData.get(entity).inventory;
			
			for (int i=0; i<inv.getSizeInventory(); i++) {
				ItemStack item = inv.getStackInSlot(i);
				if (item == null || !item.getItem().equals(Content.Accessories.AMULET)) continue;
				
				GlStateManager.pushMatrix();
	            if(entity.isSneaking())
	            {
	                GlStateManager.translate(0.0F, 0.25F, 0.0F);
		            GlStateManager.rotate(15f, 1, 0, 0);
	            }
	            GlStateManager.scale(1,-1,1);
	            GlStateManager.translate(0, -0.0625f, -0.125f);
	            GlStateManager.scale(0.5f,0.5f,0.5f);
				
	            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
				RenderItem render = Minecraft.getMinecraft().getRenderItem();
				render.renderItem(item, TransformType.NONE);
				GlStateManager.popMatrix();
			}
		}
		
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

}
