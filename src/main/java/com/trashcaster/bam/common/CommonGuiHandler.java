package com.trashcaster.bam.common;

import com.trashcaster.bam.entity.PlayerData;
import com.trashcaster.bam.inventory.ContainerAccessories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CommonGuiHandler implements IGuiHandler {

	public static final int GRAVESTONE_EDIT_GUI = 0;
	public static final int GRAVESTONE_DISPLAY_GUI = 1;
	public static final int GUI_ACCESSORIES = 2;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		switch (ID) {
		case GUI_ACCESSORIES:
			PlayerData data = PlayerData.get(player);
			return new ContainerAccessories(player, player.inventory, data.inventory);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

}
