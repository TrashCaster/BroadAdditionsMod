package com.trashcaster.bam.util;

import java.util.function.Consumer;
import java.util.function.Function;

import com.trashcaster.bam.BroadAdditionsMod;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Util {

	private static final String FLUID_MODEL_PATH = BroadAdditionsMod.MODID+":fluid";
	
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

	
	public static Fluid createFluid(String name, boolean hasFlowIcon, Consumer<Fluid> fluidPropertyApplier, Function<Fluid, BlockFluidBase> blockFactory) {
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

		Content.Liquid.REGISTERED_FLUIDS.add(fluid);

		return fluid;
	}

	private static <T extends Block & IFluidBlock> T registerFluidBlock(T block) {
		block.setRegistryName("fluid." +block.getFluid().getName());
		block.setUnlocalizedName(BroadAdditionsMod.MODID+ ":" + block.getFluid().getUnlocalizedName());
		block.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerBlock(block);

		Content.Liquid.REGISTERED_FLUID_BLOCKS.add(block);
		return block;
	}
	

	@SideOnly(Side.CLIENT)
	public static void registerFluidModel(IFluidBlock fluidBlock) {
		Item item = Item.getItemFromBlock((Block) fluidBlock);

		ModelBakery.registerItemVariants(item);

		final ModelResourceLocation modelResourceLocation = new ModelResourceLocation(FLUID_MODEL_PATH, fluidBlock.getFluid().getName());

		ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				return modelResourceLocation;
			}
		});

		ModelLoader.setCustomStateMapper((Block) fluidBlock, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState p_178132_1_) {
				return modelResourceLocation;
			}
		});
	}
	
	public static int getBoost(EntityPlayer player) {
		if (player != null && player.ridingEntity == null && !player.onGround && !player.isInWater()&& player.getEquipmentInSlot(1) != null) {
			ItemStack boots = player.getEquipmentInSlot(1);
			if (boots.getItemDamage() >= (double)boots.getMaxDamage()*0.75d) return 0;
			
			int level = EnchantmentHelper.getEnchantmentLevel(15, boots);
			return level;
		}
		return 0;
	}
}
