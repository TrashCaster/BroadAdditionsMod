package com.trashcaster.bam.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerDummy extends Container {

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
