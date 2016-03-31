package com.trashcaster.bam;

import java.util.HashSet;
import java.util.Set;

import com.trashcaster.bam.block.BlockChair;
import com.trashcaster.bam.common.CommonProxy;
import com.trashcaster.bam.network.messages.MessageBoostKey;
import com.trashcaster.bam.network.messages.MessageSpawnBoostParticles;
import com.trashcaster.bam.network.messages.MessageSwitchInventory;
import com.trashcaster.bam.network.messages.MessageUpatePlayerData;
import com.trashcaster.bam.network.messages.MessageUpdateGravestone;
import com.trashcaster.bam.world.BroadWorldGenerator;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = BroadAdditionsMod.MODID, version = BroadAdditionsMod.VERSION)
public class BroadAdditionsMod {
	public static final String MODID = "bam";
	public static final String VERSION = "1.0";
	
	@SidedProxy(serverSide="com.trashcaster.bam.common.CommonProxy",clientSide="com.trashcaster.bam.client.ClientProxy")
	public static CommonProxy PROXY;
	
	@Instance
	public static BroadAdditionsMod INSTANCE;
	
	public static BroadWorldGenerator WORLD_GEN = new BroadWorldGenerator();

    public static SimpleNetworkWrapper NETWORK;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		PROXY.preInit();
		NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID+".net");
		int packetID = -1; // "unassigned" for now because...
		
		// we have it set up to register with incrementing IDs rather than fixed values to maintain flexibility
		NETWORK.registerMessage(MessageUpatePlayerData.ServerHandler.class, MessageUpatePlayerData.class, ++packetID, Side.SERVER);
		NETWORK.registerMessage(MessageUpatePlayerData.ClientHandler.class, MessageUpatePlayerData.class, packetID, Side.CLIENT);
		NETWORK.registerMessage(MessageUpdateGravestone.ServerHandler.class, MessageUpdateGravestone.class, ++packetID, Side.SERVER);
		NETWORK.registerMessage(MessageUpdateGravestone.ClientHandler.class, MessageUpdateGravestone.class, packetID, Side.CLIENT);
		NETWORK.registerMessage(MessageSpawnBoostParticles.ServerHandler.class, MessageSpawnBoostParticles.class, ++packetID, Side.SERVER);
		NETWORK.registerMessage(MessageSpawnBoostParticles.ClientHandler.class, MessageSpawnBoostParticles.class, packetID, Side.CLIENT);
		NETWORK.registerMessage(MessageBoostKey.ServerHandler.class, MessageBoostKey.class, ++packetID, Side.SERVER);
		NETWORK.registerMessage(MessageBoostKey.ClientHandler.class, MessageBoostKey.class, packetID, Side.CLIENT);
		NETWORK.registerMessage(MessageSwitchInventory.ServerHandler.class, MessageSwitchInventory.class, ++packetID, Side.SERVER);
		NETWORK.registerMessage(MessageSwitchInventory.ClientHandler.class, MessageSwitchInventory.class, packetID, Side.CLIENT);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		PROXY.init();
		GameRegistry.registerWorldGenerator(WORLD_GEN, 20);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		PROXY.postInit();
	}
}
