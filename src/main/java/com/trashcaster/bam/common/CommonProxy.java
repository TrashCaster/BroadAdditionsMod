package com.trashcaster.bam.common;

import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.init.common.CommonInit;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {
	
	public static CommonEventHandler EVENT_HANDLER_COMMON = new CommonEventHandler();
	public static CommonGuiHandler GUI_HANDLER = new CommonGuiHandler();
	
	public void preInit() {
		CommonInit.registerBlocks();
		CommonInit.registerFluids();
		CommonInit.registerItems();
		CommonInit.registerTileEntities();
		CommonInit.registerRecipes();
		CommonInit.registerEntities();
		CommonInit.registerEnchantments();
	}
	
	public void init() {
		MinecraftForge.EVENT_BUS.register(EVENT_HANDLER_COMMON);
		MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
		NetworkRegistry.INSTANCE.registerGuiHandler(BroadAdditionsMod.INSTANCE, GUI_HANDLER);
	}
	
	public void postInit() {
		
	}
}
