package com.trashcaster.bam.client;

import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.client.renderer.entity.RenderFlyingCarpet;
import com.trashcaster.bam.client.renderer.entity.RenderModPlayer;
import com.trashcaster.bam.client.renderer.entity.RenderStaticItem;
import com.trashcaster.bam.client.renderer.tileentity.TileEntityGravestoneRenderer;
import com.trashcaster.bam.common.CommonProxy;
import com.trashcaster.bam.entity.EntityFlyingCarpet;
import com.trashcaster.bam.entity.item.EntityStaticItem;
import com.trashcaster.bam.tileentity.TileEntityGravestone;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	public static ClientEventHandler EVENT_HANDLER_CLIENT = new ClientEventHandler();
	public static ClientGuiHandler GUI_HANDLER = new ClientGuiHandler();
	
	public static ResourceLocation HIGHLIGHT_TINT = new ResourceLocation(BroadAdditionsMod.MODID, "textures/misc/highlight_tint.png");
	private static final String FLUID_MODEL_PATH = BroadAdditionsMod.MODID+":fluid";
	
	public static RenderModPlayer RENDER_MOD_PLAYER_DEFAULT;
	public static RenderModPlayer RENDER_MOD_PLAYER_SLIM;
	
	@Override
	public void preInit() {
		super.preInit();

		RenderingRegistry.registerEntityRenderingHandler(EntityStaticItem.class, RenderStaticItem::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityFlyingCarpet.class, RenderFlyingCarpet::new);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGravestone.class, new TileEntityGravestoneRenderer());

		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BroadAdditionsMod.Content.Chairs.OAK), 0, new ModelResourceLocation("bam:oak_chair", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BroadAdditionsMod.Content.Chairs.SPRUCE), 0, new ModelResourceLocation("bam:spruce_chair", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BroadAdditionsMod.Content.Chairs.BIRCH), 0, new ModelResourceLocation("bam:birch_chair", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BroadAdditionsMod.Content.Chairs.JUNGLE), 0, new ModelResourceLocation("bam:jungle_chair", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BroadAdditionsMod.Content.Chairs.ACACIA), 0, new ModelResourceLocation("bam:acacia_chair", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BroadAdditionsMod.Content.Chairs.DARK_OAK), 0, new ModelResourceLocation("bam:dark_oak_chair", "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BroadAdditionsMod.Content.Tables.OAK), 0, new ModelResourceLocation("bam:oak_table", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BroadAdditionsMod.Content.Tables.SPRUCE), 0, new ModelResourceLocation("bam:spruce_table", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BroadAdditionsMod.Content.Tables.BIRCH), 0, new ModelResourceLocation("bam:birch_table", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BroadAdditionsMod.Content.Tables.JUNGLE), 0, new ModelResourceLocation("bam:jungle_table", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BroadAdditionsMod.Content.Tables.ACACIA), 0, new ModelResourceLocation("bam:acacia_table", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BroadAdditionsMod.Content.Tables.DARK_OAK), 0, new ModelResourceLocation("bam:dark_oak_table", "inventory"));

		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BroadAdditionsMod.Content.Misc.GRAVESTONE), 0, new ModelResourceLocation("bam:gravestone", "inventory"));
		ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(BroadAdditionsMod.Content.Misc.GRAVESTONE), 0, TileEntityGravestone.class);

		ModelLoader.setCustomModelResourceLocation(BroadAdditionsMod.Content.Accessories.AMULET, 0, new ModelResourceLocation("bam:amulet", "inventory"));

		ModelLoader.setCustomModelResourceLocation(BroadAdditionsMod.Content.Misc.FLYING_CARPET, 0, new ModelResourceLocation("bam:flying_carpet", "inventory"));

		BroadAdditionsMod.Content.Liquid.REGISTERED_FLUID_BLOCKS.forEach(this::registerFluidModel);
	}

	@Override
	public void init() {
		super.init();
		MinecraftForge.EVENT_BUS.register(EVENT_HANDLER_CLIENT);
		FMLCommonHandler.instance().bus().register(EVENT_HANDLER_CLIENT);
		NetworkRegistry.INSTANCE.registerGuiHandler(BroadAdditionsMod.INSTANCE, GUI_HANDLER);

		RENDER_MOD_PLAYER_DEFAULT = new RenderModPlayer(Minecraft.getMinecraft().getRenderManager(), false);
		RENDER_MOD_PLAYER_SLIM = new RenderModPlayer(Minecraft.getMinecraft().getRenderManager(), true);
	}

	@Override
	public void postInit() {
		super.postInit();
	}
	
	private void registerFluidModel(IFluidBlock fluidBlock) {
		Item item = Item.getItemFromBlock((Block) fluidBlock);

		ModelBakery.registerItemVariants(item);

		ModelResourceLocation modelResourceLocation = new ModelResourceLocation(FLUID_MODEL_PATH, fluidBlock.getFluid().getName());

		ModelLoader.setCustomMeshDefinition(item, MeshDefinitionFix.create(stack -> modelResourceLocation));

		ModelLoader.setCustomStateMapper((Block) fluidBlock, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState p_178132_1_) {
				return modelResourceLocation;
			}
		});
	}
}
