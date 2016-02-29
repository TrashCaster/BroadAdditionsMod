package com.trashcaster.bam.item;

import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.common.BucketHandler;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemBucketRedwater extends ItemBucket {
	private final String name = "redwater_bucket";

	public ItemBucketRedwater() {
		super(BroadAdditionsMod.Content.Liquid.REDWATER.getBlock());
		GameRegistry.registerItem(this, name);
		setUnlocalizedName(BroadAdditionsMod.MODID+"."+name);
		BucketHandler.INSTANCE.buckets.put(BroadAdditionsMod.Content.Liquid.REDWATER.getBlock(), this);
	}

}
