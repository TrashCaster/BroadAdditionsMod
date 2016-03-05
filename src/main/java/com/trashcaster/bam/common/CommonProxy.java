package com.trashcaster.bam.common;

import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.block.BlockChair;
import com.trashcaster.bam.block.BlockGravestone;
import com.trashcaster.bam.block.BlockTable;
import com.trashcaster.bam.entity.EntityFlyingCarpet;
import com.trashcaster.bam.entity.EntitySittable;
import com.trashcaster.bam.entity.item.EntityStaticItem;
import com.trashcaster.bam.item.ItemAccessory;
import com.trashcaster.bam.item.ItemFlyingCarpet;
import com.trashcaster.bam.tileentity.TileEntityGravestone;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.function.Consumer;
import java.util.function.Function;

public class CommonProxy {
	
	public static CommonEventHandler EVENT_HANDLER_COMMON = new CommonEventHandler();
	public static CommonGuiHandler GUI_HANDLER = new CommonGuiHandler();
	
	public void preInit() {
		BroadAdditionsMod.Content.Chairs.OAK = new BlockChair(Material.wood, "oak").setHardness(2f).setResistance(5.0F);
		BroadAdditionsMod.Content.Chairs.SPRUCE = new BlockChair(Material.wood, "spruce").setHardness(2f).setResistance(5.0F);
		BroadAdditionsMod.Content.Chairs.BIRCH = new BlockChair(Material.wood, "birch").setHardness(2f).setResistance(5.0F);
		BroadAdditionsMod.Content.Chairs.JUNGLE = new BlockChair(Material.wood, "jungle").setHardness(2f).setResistance(5.0F);
		BroadAdditionsMod.Content.Chairs.ACACIA = new BlockChair(Material.wood, "acacia").setHardness(2f).setResistance(5.0F);
		BroadAdditionsMod.Content.Chairs.DARK_OAK = new BlockChair(Material.wood, "dark_oak").setHardness(2f).setResistance(5.0F);
		
		BroadAdditionsMod.Content.Tables.OAK = new BlockTable(Material.wood, "oak").setHardness(2f).setResistance(5.0F);
		BroadAdditionsMod.Content.Tables.SPRUCE = new BlockTable(Material.wood, "spruce").setHardness(2f).setResistance(5.0F);
		BroadAdditionsMod.Content.Tables.BIRCH = new BlockTable(Material.wood, "birch").setHardness(2f).setResistance(5.0F);
		BroadAdditionsMod.Content.Tables.JUNGLE = new BlockTable(Material.wood, "jungle").setHardness(2f).setResistance(5.0F);
		BroadAdditionsMod.Content.Tables.ACACIA = new BlockTable(Material.wood, "acacia").setHardness(2f).setResistance(5.0F);
		BroadAdditionsMod.Content.Tables.DARK_OAK = new BlockTable(Material.wood, "dark_oak").setHardness(2f).setResistance(5.0F);

		BroadAdditionsMod.Content.Misc.GRAVESTONE = new BlockGravestone().setHardness(5f).setResistance(10f);
		BroadAdditionsMod.Content.Accessories.AMULET = new ItemAccessory("amulet", ItemAccessory.AccessoryType.AMULET);
		BroadAdditionsMod.Content.Misc.FLYING_CARPET = new ItemFlyingCarpet();
		
		BroadAdditionsMod.Content.Liquid.REDWATER = createFluid("redwater", true,
				fluid -> fluid.setLuminosity(10).setDensity(1600).setViscosity(100),
				fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.redColor)));
		
		GameRegistry.registerTileEntity(TileEntityGravestone.class, "tombstone");
		
		Block[] chairs = {BroadAdditionsMod.Content.Chairs.OAK,
				BroadAdditionsMod.Content.Chairs.SPRUCE,
				BroadAdditionsMod.Content.Chairs.BIRCH,
				BroadAdditionsMod.Content.Chairs.JUNGLE,
				BroadAdditionsMod.Content.Chairs.ACACIA,
				BroadAdditionsMod.Content.Chairs.DARK_OAK};

		Block[] tables = {BroadAdditionsMod.Content.Tables.OAK,
				BroadAdditionsMod.Content.Tables.SPRUCE,
				BroadAdditionsMod.Content.Tables.BIRCH,
				BroadAdditionsMod.Content.Tables.JUNGLE,
				BroadAdditionsMod.Content.Tables.ACACIA,
				BroadAdditionsMod.Content.Tables.DARK_OAK};
		
		ItemStack[] logs = {new ItemStack(Blocks.log, 1, 0),new ItemStack(Blocks.log, 1, 1), new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.log, 1, 3), new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.log2, 1, 1)};
		
		for (int i=0; i<chairs.length; i++) {
			GameRegistry.addShapedRecipe(new ItemStack(chairs[i], 8), "P ", "PP", "LL", 'P', new ItemStack(Blocks.planks, 1, (byte)i), 'L', logs[i]);
		}
		
		for (int i=0; i<tables.length; i++) {
			GameRegistry.addShapedRecipe(new ItemStack(tables[i], 4), "PP", "LL", 'P', new ItemStack(Blocks.planks, 1, (byte)i), 'L', logs[i]);
		}

		EntityRegistry.registerModEntity(EntitySittable.class, "seat", 0, BroadAdditionsMod.INSTANCE, 64, 1, true);
		EntityRegistry.registerModEntity(EntityStaticItem.class, "staticItem", 1, BroadAdditionsMod.INSTANCE, 64, 1, true);
		EntityRegistry.registerModEntity(EntityFlyingCarpet.class, "flyingCarpet", 2, BroadAdditionsMod.INSTANCE, 64, 1, true);
	}
	
	public void init() {
		MinecraftForge.EVENT_BUS.register(EVENT_HANDLER_COMMON);
		MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
		NetworkRegistry.INSTANCE.registerGuiHandler(BroadAdditionsMod.INSTANCE, GUI_HANDLER);
	}
	
	public void postInit() {
		
	}
	
	private static <T extends Block & IFluidBlock> Fluid createFluid(String name, boolean hasFlowIcon, Consumer<Fluid> fluidPropertyApplier, Function<Fluid, T> blockFactory) {
		final String texturePrefix = BroadAdditionsMod.MODID + ":blocks/";

		ResourceLocation still = new ResourceLocation(texturePrefix + name + "_still");
		ResourceLocation flowing = hasFlowIcon ? new ResourceLocation(texturePrefix + name + "_flow") : still;

		Fluid fluid = new Fluid(name, still, flowing);
		boolean useOwnFluid = FluidRegistry.registerFluid(fluid);

		if (useOwnFluid) {
			fluidPropertyApplier.accept(fluid);
			registerFluidBlock(blockFactory.apply(fluid));
		} else {
			fluid = FluidRegistry.getFluid(name);
		}

		BroadAdditionsMod.Content.Liquid.REGISTERED_FLUIDS.add(fluid);

		return fluid;
	}

	private static <T extends Block & IFluidBlock> T registerFluidBlock(T block) {
		block.setRegistryName("fluid." +block.getFluid().getName());
		block.setUnlocalizedName(BroadAdditionsMod.MODID+ ":" + block.getFluid().getUnlocalizedName());
		block.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerBlock(block);

		BroadAdditionsMod.Content.Liquid.REGISTERED_FLUID_BLOCKS.add(block);
		return block;
	}
}
