package com.trashcaster.bam.init.common;

import java.util.function.Consumer;
import java.util.function.Function;

import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.block.BlockChair;
import com.trashcaster.bam.block.BlockGravestone;
import com.trashcaster.bam.block.BlockRedwater;
import com.trashcaster.bam.block.BlockTable;
import com.trashcaster.bam.entity.EntitySittable;
import com.trashcaster.bam.entity.item.EntityBackpack;
import com.trashcaster.bam.entity.item.EntityStaticItem;
import com.trashcaster.bam.item.ItemAccessory;
import com.trashcaster.bam.item.ItemBackpack;
import com.trashcaster.bam.item.ItemBucketRedwater;
import com.trashcaster.bam.tileentity.TileEntityGravestone;
import com.trashcaster.bam.util.Content;
import com.trashcaster.bam.util.Util;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonInit {
	
	public static void registerBlocks() {
		Content.Chairs.OAK = new BlockChair(Material.wood, "oak").setHardness(2f).setResistance(5.0F);
		Content.Chairs.SPRUCE = new BlockChair(Material.wood, "spruce").setHardness(2f).setResistance(5.0F);
		Content.Chairs.BIRCH = new BlockChair(Material.wood, "birch").setHardness(2f).setResistance(5.0F);
		Content.Chairs.JUNGLE = new BlockChair(Material.wood, "jungle").setHardness(2f).setResistance(5.0F);
		Content.Chairs.ACACIA = new BlockChair(Material.wood, "acacia").setHardness(2f).setResistance(5.0F);
		Content.Chairs.DARK_OAK = new BlockChair(Material.wood, "dark_oak").setHardness(2f).setResistance(5.0F);
		
		Content.Tables.OAK = new BlockTable(Material.wood, "oak").setHardness(2f).setResistance(5.0F);
		Content.Tables.SPRUCE = new BlockTable(Material.wood, "spruce").setHardness(2f).setResistance(5.0F);
		Content.Tables.BIRCH = new BlockTable(Material.wood, "birch").setHardness(2f).setResistance(5.0F);
		Content.Tables.JUNGLE = new BlockTable(Material.wood, "jungle").setHardness(2f).setResistance(5.0F);
		Content.Tables.ACACIA = new BlockTable(Material.wood, "acacia").setHardness(2f).setResistance(5.0F);
		Content.Tables.DARK_OAK = new BlockTable(Material.wood, "dark_oak").setHardness(2f).setResistance(5.0F);

		Content.Misc.GRAVESTONE = new BlockGravestone().setHardness(5f).setResistance(10f);
	}

	public static void registerItems() {
		Content.Accessories.AMULET = new ItemAccessory("amulet", ItemAccessory.AccessoryType.AMULET);
		Content.Accessories.BACKPACK = new ItemBackpack();
		Content.Liquid.REDWATER_BUCKET = new ItemBucketRedwater();
	}
	
	public static void registerFluids() {
		Content.Liquid.REDWATER = Util.createFluid("redwater", true,
				new Consumer<Fluid>() {
					@Override
					public void accept(Fluid fluid) {
						fluid.setLuminosity(10).setDensity(5000).setViscosity(3000);
					}
				},
				new Function<Fluid, BlockFluidBase>() {
					@Override
					public BlockFluidBase apply(Fluid fluid) {
						return new BlockRedwater(fluid, new MaterialLiquid(MapColor.redColor));
					}
				});
	}
	
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityGravestone.class, "tombstone");
	}
	
	public static void registerRecipes() {
		Block[] chairs = {Content.Chairs.OAK,
				Content.Chairs.SPRUCE,
				Content.Chairs.BIRCH,
				Content.Chairs.JUNGLE,
				Content.Chairs.ACACIA,
				Content.Chairs.DARK_OAK};

		Block[] tables = {Content.Tables.OAK,
				Content.Tables.SPRUCE,
				Content.Tables.BIRCH,
				Content.Tables.JUNGLE,
				Content.Tables.ACACIA,
				Content.Tables.DARK_OAK};
		
		ItemStack[] logs = {new ItemStack(Blocks.log, 1, 0),new ItemStack(Blocks.log, 1, 1), new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.log, 1, 3), new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.log2, 1, 1)};
		
		for (int i=0; i<chairs.length; i++) {
			GameRegistry.addShapedRecipe(new ItemStack(chairs[i], 8), "P ", "PP", "LL", 'P', new ItemStack(Blocks.planks, 1, (byte)i), 'L', logs[i]);
		}
		
		for (int i=0; i<tables.length; i++) {
			GameRegistry.addShapedRecipe(new ItemStack(tables[i], 4), "PP", "LL", 'P', new ItemStack(Blocks.planks, 1, (byte)i), 'L', logs[i]);
		}
	}
	
	public static void registerEntities() {
		EntityRegistry.registerModEntity(EntitySittable.class, "seat", 0, BroadAdditionsMod.INSTANCE, 64, 1, true);
		EntityRegistry.registerModEntity(EntityStaticItem.class, "staticItem", 1, BroadAdditionsMod.INSTANCE, 64, 1, true);
		EntityRegistry.registerModEntity(EntityBackpack.class, "backpack", 2, BroadAdditionsMod.INSTANCE, 64, 1, true);
	}
	
	public static void registerEnchantments() {
		Enchantment.addToBookList(new Enchantment(15, new ResourceLocation(BroadAdditionsMod.MODID, "boosting"), 1, EnumEnchantmentType.ARMOR_FEET) {
			@Override
		    public int getMaxLevel()
		    {
		        return 3;
		    }

			@Override
		    public int getMinEnchantability(int enchantmentLevel)
		    {
		        return 15;
		    }

			@Override
		    public int getMaxEnchantability(int enchantmentLevel)
		    {
		        return super.getMinEnchantability(enchantmentLevel) + 50;
		    }

			@Override
		    public boolean canApplyTogether(Enchantment ench)
		    {
		        return super.canApplyTogether(ench) && ench.effectId != featherFalling.effectId && ench.effectId != depthStrider.effectId;
		    }
		}.setName("boosting"));
	}
}
