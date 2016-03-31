package com.trashcaster.bam.inventory;

import com.trashcaster.bam.item.ItemAccessory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.util.Constants;

public class InventoryBackpack implements IInventory {
	private final String name = "Backpack";

	private final String tagName = "Items";
	public static final int SLOT_COUNT = 6;

	private ItemStack[] inventory = new ItemStack[SLOT_COUNT];
	
	private ItemStack inventoryHolder;

	public InventoryBackpack() {
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot >= this.inventory.length) return null;
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize > amount) {
				stack = stack.splitStack(amount);
				this.markDirty();
			} else {
				setInventorySlotContents(slot, null);
			}
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		if (slot >= this.inventory.length) return;
		this.inventory[slot] = itemstack;

		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}

		this.markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		return true;
	}

	public void writeToNBT(NBTTagCompound compound) {
		NBTTagList items = new NBTTagList();

		for (int i = 0; i < getSizeInventory(); ++i) {
			if (getStackInSlot(i) != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte) i);
				getStackInSlot(i).writeToNBT(item);
				items.appendTag(item);
			}
		}

		// We're storing our items in a custom tag list using our 'tagName' from
		// above
		// to prevent potential conflicts
		compound.setTag(tagName, items);
	}

	public void readFromNBT(NBTTagCompound compound) {
		NBTTagList items = compound.getTagList(tagName, Constants.NBT.TAG_COMPOUND);

		for (int i = 0; i < items.tagCount(); ++i) {
			NBTTagCompound item = (NBTTagCompound) items.getCompoundTagAt(i);
			byte slot = item.getByte("Slot");

			if (slot >= 0 && slot < getSizeInventory()) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(item);
			}
		}
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public IChatComponent getDisplayName() {
		return (IChatComponent) new ChatComponentTranslation(this.getName(), new Object[0]);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		if (this.inventory[index] != null) {
			ItemStack itemstack = this.inventory[index];
			this.inventory[index] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void markDirty() {
		for (int i = 0; i < getSizeInventory(); ++i) {
			if (getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0) {
				inventory[i] = null;
			}
		}
	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.inventory.length; ++i) {
			this.inventory[i] = null;
		}
	}
}