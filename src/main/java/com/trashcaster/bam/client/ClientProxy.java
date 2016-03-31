package com.trashcaster.bam.client;

import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.common.CommonProxy;
import com.trashcaster.bam.init.client.ClientInit;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	public static ClientEventHandler EVENT_HANDLER_CLIENT = new ClientEventHandler();
	public static ClientGuiHandler GUI_HANDLER = new ClientGuiHandler();
	
	@Override
	public void preInit() {
		super.preInit();

		ClientInit.registerBlockModels();
		ClientInit.registerItemModels();
		ClientInit.registerTileEntityRenderers();
		ClientInit.registerFluidModels();
	}

	@Override
	public void init() {
		super.init();
		
		ClientInit.registerEntityRenderers();
		
		MinecraftForge.EVENT_BUS.register(EVENT_HANDLER_CLIENT);
		NetworkRegistry.INSTANCE.registerGuiHandler(BroadAdditionsMod.INSTANCE, GUI_HANDLER);

	}

	@Override
	public void postInit() {
		super.postInit();
	}
	
}
