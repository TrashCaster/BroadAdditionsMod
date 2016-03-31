package com.trashcaster.bam.item;

import com.trashcaster.bam.entity.item.EntityBackpack;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBackpack extends ItemAccessory {

	public ItemBackpack() {
		super("backpack", AccessoryType.BACK_ITEM);
	}
	
    public boolean hasCustomEntity(ItemStack stack)
    {
        return true;
    }

    public Entity createEntity(World world, Entity location, ItemStack itemstack)
    {
    	EntityBackpack backpack = null;
    	if (!world.isRemote && location != null && ((EntityItem)location).getThrower() != null) {
    		String thrower = ((EntityItem)location).getThrower();
    		EntityPlayer player = world.getPlayerEntityByName(thrower);
    		backpack = new EntityBackpack(world, location.posX, location.posY, location.posZ, player.rotationYaw, player.rotationPitch);
        	backpack.setItem(itemstack);
    	}
        return backpack;
    }
}
