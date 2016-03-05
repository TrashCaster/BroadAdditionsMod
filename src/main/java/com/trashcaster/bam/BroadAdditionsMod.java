package com.trashcaster.bam;

import java.util.HashSet;
import java.util.Set;

import com.trashcaster.bam.block.BlockChair;
import com.trashcaster.bam.common.CommonProxy;
import com.trashcaster.bam.network.messages.MessageRidingJumpKey;
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
		NETWORK.registerMessage(MessageRidingJumpKey.ServerHandler.class, MessageRidingJumpKey.class, ++packetID, Side.SERVER);
		NETWORK.registerMessage(MessageRidingJumpKey.ClientHandler.class, MessageRidingJumpKey.class, packetID, Side.CLIENT);
		NETWORK.registerMessage(MessageSwitchInventory.ServerHandler.class, MessageSwitchInventory.class, ++packetID, Side.SERVER);
		NETWORK.registerMessage(MessageSwitchInventory.ClientHandler.class, MessageSwitchInventory.class, packetID, Side.CLIENT);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		PROXY.init();
		GameRegistry.registerWorldGenerator(WORLD_GEN, 20);
	}

	@EventHandler
	public void postInit(FMLInitializationEvent event) {
		PROXY.postInit();
	}
	
	public static boolean isChair(Block block) {
		if (block.equals(Content.Chairs.OAK)) {
			return true;
		}
		if (block.equals(Content.Chairs.BIRCH)) {
			return true;
		}
		if (block.equals(Content.Chairs.SPRUCE)) {
			return true;
		}
		if (block.equals(Content.Chairs.JUNGLE)) {
			return true;
		}
		if (block.equals(Content.Chairs.ACACIA)) {
			return true;
		}
		if (block.equals(Content.Chairs.DARK_OAK)) {
			return true;
		}
		return false;
	}
	
	public static boolean isTable(Block block) {
		if (block.equals(Content.Tables.OAK)) {
			return true;
		}
		if (block.equals(Content.Tables.BIRCH)) {
			return true;
		}
		if (block.equals(Content.Tables.SPRUCE)) {
			return true;
		}
		if (block.equals(Content.Tables.JUNGLE)) {
			return true;
		}
		if (block.equals(Content.Tables.ACACIA)) {
			return true;
		}
		if (block.equals(Content.Tables.DARK_OAK)) {
			return true;
		}
		return false;
	}
	
	
	public static class Content {
		public static class Chairs {
			public static Block OAK;
			public static Block BIRCH;
			public static Block SPRUCE;
			public static Block JUNGLE;
			public static Block ACACIA;
			public static Block DARK_OAK;
		}
		
		public static class Tables {
			public static Block OAK;
			public static Block BIRCH;
			public static Block SPRUCE;
			public static Block JUNGLE;
			public static Block ACACIA;
			public static Block DARK_OAK;
		}

		
		public static class Accessories {
			public static Item AMULET;
		}
		
		public static class Misc {
			public static Block GRAVESTONE;
			public static Block NETHER_CHEST;
			public static Item FLYING_CARPET;
		}
		
		public static class Liquid {
			public static final Set<Fluid> REGISTERED_FLUIDS = new HashSet<Fluid>();
			public static final Set<IFluidBlock> REGISTERED_FLUID_BLOCKS = new HashSet<IFluidBlock>();
			public static Fluid REDWATER;
		}
	}
}
