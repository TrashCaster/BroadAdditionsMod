package com.trashcaster.bam.util;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidBlock;

public class Content {

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
			public static Item BACKPACK;
		}
		
		public static class Misc {
			public static Block GRAVESTONE;
			public static Block NETHER_CHEST;
		}
		
		public static class Liquid {
			public static final Set<Fluid> REGISTERED_FLUIDS = new HashSet<Fluid>();
			public static final Set<IFluidBlock> REGISTERED_FLUID_BLOCKS = new HashSet<IFluidBlock>();
			
			public static Fluid REDWATER;
			
			public static Item REDWATER_BUCKET;
		}
}
