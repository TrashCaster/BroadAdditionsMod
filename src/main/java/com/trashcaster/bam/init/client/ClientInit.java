package com.trashcaster.bam.init.client;

import com.trashcaster.bam.client.renderer.entity.RenderBackpack;
import com.trashcaster.bam.client.renderer.entity.RenderStaticItem;
import com.trashcaster.bam.client.renderer.entity.layers.LayerAmulet;
import com.trashcaster.bam.client.renderer.entity.layers.LayerBackpack;
import com.trashcaster.bam.client.renderer.tileentity.TileEntityGravestoneRenderer;
import com.trashcaster.bam.entity.item.EntityBackpack;
import com.trashcaster.bam.entity.item.EntityStaticItem;
import com.trashcaster.bam.tileentity.TileEntityGravestone;
import com.trashcaster.bam.util.Content;
import com.trashcaster.bam.util.Util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientInit {
	
	public static void registerEntityRenderers() {
		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();

		RenderingRegistry.registerEntityRenderingHandler(EntityStaticItem.class, new RenderStaticItem(renderManager));
		RenderingRegistry.registerEntityRenderingHandler(EntityBackpack.class, new RenderBackpack(renderManager));

		RenderPlayer defaultRender = ((RenderPlayer)Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default"));
		RenderPlayer slimRender = ((RenderPlayer)Minecraft.getMinecraft().getRenderManager().getSkinMap().get("slim"));

		defaultRender.addLayer(new LayerBackpack(defaultRender));
		slimRender.addLayer(new LayerBackpack(slimRender));
		

		defaultRender.addLayer(new LayerAmulet(defaultRender));
		slimRender.addLayer(new LayerAmulet(slimRender));
	}
	
	public static void registerTileEntityRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGravestone.class, new TileEntityGravestoneRenderer());
	}
	
	public static void registerBlockModels() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Content.Chairs.OAK), 0, new ModelResourceLocation("bam:oak_chair", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Content.Chairs.SPRUCE), 0, new ModelResourceLocation("bam:spruce_chair", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Content.Chairs.BIRCH), 0, new ModelResourceLocation("bam:birch_chair", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Content.Chairs.JUNGLE), 0, new ModelResourceLocation("bam:jungle_chair", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Content.Chairs.ACACIA), 0, new ModelResourceLocation("bam:acacia_chair", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Content.Chairs.DARK_OAK), 0, new ModelResourceLocation("bam:dark_oak_chair", "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Content.Tables.OAK), 0, new ModelResourceLocation("bam:oak_table", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Content.Tables.SPRUCE), 0, new ModelResourceLocation("bam:spruce_table", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Content.Tables.BIRCH), 0, new ModelResourceLocation("bam:birch_table", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Content.Tables.JUNGLE), 0, new ModelResourceLocation("bam:jungle_table", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Content.Tables.ACACIA), 0, new ModelResourceLocation("bam:acacia_table", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Content.Tables.DARK_OAK), 0, new ModelResourceLocation("bam:dark_oak_table", "inventory"));
		

		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Content.Misc.GRAVESTONE), 0, new ModelResourceLocation("bam:gravestone", "inventory"));
		ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(Content.Misc.GRAVESTONE), 0, TileEntityGravestone.class);

	}
	
	public static void registerItemModels() {
		ModelLoader.setCustomModelResourceLocation(Content.Accessories.AMULET, 0, new ModelResourceLocation("bam:amulet", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Content.Accessories.BACKPACK, 0, new ModelResourceLocation("bam:backpack", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Content.Liquid.REDWATER_BUCKET, 0, new ModelResourceLocation("bam:bucket_redwater", "inventory"));
	}
	
	public static void registerFluidModels() {
		for (IFluidBlock f:Content.Liquid.REGISTERED_FLUID_BLOCKS) {
		    Util.registerFluidModel(f);
		}
	}

}
