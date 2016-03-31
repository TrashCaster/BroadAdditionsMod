package com.trashcaster.bam.common;

import com.trashcaster.bam.entity.PlayerData;
import com.trashcaster.bam.entity.item.EntityBackpack;
import com.trashcaster.bam.inventory.ContainerAccessories;
import com.trashcaster.bam.inventory.ContainerBackpack;
import com.trashcaster.bam.inventory.InventoryBackpack;
import com.trashcaster.bam.util.Util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CommonGuiHandler implements IGuiHandler {

	public static final int GRAVESTONE_EDIT_GUI = 0;
	public static final int GRAVESTONE_DISPLAY_GUI = 1;
	public static final int GUI_ACCESSORIES = 2;
	public static final int GUI_BACKPACK = 3;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		switch (ID) {
		case GUI_ACCESSORIES:
			PlayerData data = PlayerData.get(player);
			return new ContainerAccessories(player, player.inventory, data.inventory);
		case GUI_BACKPACK:
			Entity entity = world.getEntityByID(x);
			if (entity instanceof EntityBackpack) {
			    return new ContainerBackpack(player, (EntityBackpack)entity, player.inventory, ((EntityBackpack)entity).inventory);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

}
