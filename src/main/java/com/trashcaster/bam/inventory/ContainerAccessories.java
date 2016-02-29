package com.trashcaster.bam.inventory;

import javax.swing.Icon;

import com.trashcaster.bam.entity.PlayerData;
import com.trashcaster.bam.item.ItemAccessory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerAccessories extends Container {
	/**
	 * Avoid magic numbers! This will greatly reduce the chance of you making
	 * errors in 'transferStackInSlot' method
	 */
	private static final int ARMOR_START = InventoryAccessories.SLOT_COUNT, ARMOR_END = ARMOR_START + 3,
			INV_START = ARMOR_END + 1, INV_END = INV_START + 26, HOTBAR_START = INV_END + 1,
			HOTBAR_END = HOTBAR_START + 8;

	private final EntityPlayer player;

	public ContainerAccessories(EntityPlayer player, InventoryPlayer playerInventory,
			InventoryAccessories accessories) {
		this.player = player;
		int i;

		// Add CUSTOM slots - we'll just add two for now, both of the same type.
		// Make a new Slot class for each different item type you want to add
		for (i = 0; i < InventoryAccessories.SLOT_COUNT; i++) {
			this.addSlotToContainer(
					new Slot(accessories, i, 88 + 18 * (i % 4), 26 + (18 * (int) Math.floor((float) i / 4f))) {
						public int getSlotStackLimit() {
							return 1;
						}

						public boolean isItemValid(ItemStack itemstack) {
							return itemstack != null && itemstack.getItem() instanceof ItemAccessory
									&& (InventoryAccessories.isItemValidForSlot(itemstack, this));
						}
					});
		}

		// Add ARMOR slots; note you need to make a public version of SlotArmor
		// just copy and paste the vanilla code into a new class and change what
		// you need
		for (i = 0; i < 4; ++i) {
			this.addSlotToContainer(new SlotArmor(player, playerInventory, playerInventory.getSizeInventory() - 1 - i,
					8, 8 + i * 18, i));
		}

		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	/*
	 * Hook into the default inventory updating to provide near real-time updates to all players
	 */
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		PlayerData.get(this.player).resync();
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int par2) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 < INV_START) {
				if (!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END + 1, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else {
				if (itemstack1.getItem() instanceof ItemTool) {
					if (!this.mergeItemStack(itemstack1, 0, InventoryAccessories.SLOT_COUNT, false)) {
						return null;
					}
				} else if (itemstack1.getItem() instanceof ItemArmor) {
					int type = ((ItemArmor) itemstack1.getItem()).armorType;
					if (!this.mergeItemStack(itemstack1, ARMOR_START + type, ARMOR_START + type + 1, false)) {
						return null;
					}
				} else if (par2 >= INV_START && par2 < HOTBAR_START) {
					// place in action bar
					if (!this.mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_START + 1, false)) {
						return null;
					}
				} else if (par2 >= HOTBAR_START && par2 < HOTBAR_END + 1) {
					if (!this.mergeItemStack(itemstack1, INV_START, INV_END + 1, false)) {
						return null;
					}
				}
			}

			if (itemstack1.stackSize == 0) {
				System.out.println("Slot: " + slot.slotNumber + " in " + slot.inventory.getClass().getSimpleName()
						+ " has stack size 0");
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
		boolean flag = false;
		int i = startIndex;

		if (reverseDirection) {
			i = endIndex - 1;
		}

		if (stack.isStackable()) {
			while (stack.stackSize > 0 && (!reverseDirection && i < endIndex || reverseDirection && i >= startIndex)) {
				Slot slot = (Slot) this.inventorySlots.get(i);
				ItemStack itemstack = slot.getStack();

				if (itemstack != null && itemstack.getItem() == stack.getItem()
						&& (!stack.getHasSubtypes() || stack.getMetadata() == itemstack.getMetadata())
						&& ItemStack.areItemStackTagsEqual(stack, itemstack)) {
					int j = itemstack.stackSize + stack.stackSize;

					int k = Math.min(stack.getMaxStackSize(), slot.getSlotStackLimit());
					if (j <= k) {
						stack.stackSize = 0;
						itemstack.stackSize = j;
						slot.onSlotChanged();
						flag = true;
					} else if (itemstack.stackSize < k) {
						stack.stackSize -= k - itemstack.stackSize;
						itemstack.stackSize = k;
						slot.onSlotChanged();
						flag = true;
					}
				}

				if (reverseDirection) {
					--i;
				} else {
					++i;
				}
			}
		}

		if (stack.stackSize > 0) {
			if (reverseDirection) {
				i = endIndex - 1;
			} else {
				i = startIndex;
			}

			while (!reverseDirection && i < endIndex || reverseDirection && i >= startIndex) {
				Slot slot1 = (Slot) this.inventorySlots.get(i);
				ItemStack itemstack1 = slot1.getStack();

				if (itemstack1 == null && slot1.isItemValid(stack)) // Forge:
																	// Make sure
																	// to
																	// respect
																	// isItemValid
																	// in the
																	// slot.
				{
					itemstack1 = stack.copy();
					if (itemstack1.stackSize > slot1.getSlotStackLimit()) {
						itemstack1.stackSize = slot1.getSlotStackLimit();
					}

					stack.stackSize -= itemstack1.stackSize;
					slot1.putStack(itemstack1);
					slot1.onSlotChanged();
					flag = true;
					if (!(stack.stackSize > 0)) {
						break;
					}
				}

				if (reverseDirection) {
					--i;
				} else {
					++i;
				}
			}
		}
		return flag;
	}

	private class SlotArmor extends Slot {
		/**
		 * The armor type that can be placed on that slot, it uses the same
		 * values of armorType field on ItemArmor.
		 */
		final int armorType;

		/**
		 * The parent class of this slot, ContainerPlayer, SlotArmor is a Anon
		 * inner class.
		 */
		final EntityPlayer player;

		public SlotArmor(EntityPlayer player, IInventory inventory, int slotIndex, int x, int y, int armorType) {
			super(inventory, slotIndex, x, y);
			this.player = player;
			this.armorType = armorType;
		}

		/**
		 * Returns the maximum stack size for a given slot (usually the same as
		 * getInventoryStackLimit(), but 1 in the case of armor slots)
		 */
		public int getSlotStackLimit() {
			return 1;
		}

		/**
		 * Check if the stack is a valid item for this slot. Always true beside
		 * for the armor slots.
		 */
		public boolean isItemValid(ItemStack itemstack) {
			Item item = (itemstack == null ? null : itemstack.getItem());
			return item != null && item.isValidArmor(itemstack, armorType, player);
		}

		/**
		 * Returns the icon index on items.png that is used as background image
		 * of the slot.
		 */
		@SideOnly(Side.CLIENT)
		public String getSlotTexture() {
			return ItemArmor.EMPTY_SLOT_NAMES[this.armorType];
		}
	}
}
