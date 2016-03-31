package com.trashcaster.bam.item;

import org.apache.commons.lang3.text.WordUtils;

import com.trashcaster.bam.BroadAdditionsMod;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemAccessory extends Item {
	
	private final String name;
	public final AccessoryType type;
	
	public ItemAccessory(String name, AccessoryType type) {
		this.name = name;
		this.type = type;
        this.setMaxStackSize(1);
		GameRegistry.registerItem(this, name);
		setUnlocalizedName(BroadAdditionsMod.MODID+"."+name);
	}
	
	// this determines what items can coexist in the accessory inventory
	// (each type is unique, and can't be "doubled up")
	public enum AccessoryType {
		AMULET, BACK_ITEM;
	}

    public void onAccessoryTick(World world, EntityPlayer player, ItemStack itemStack, int slotIndex) {}
}
