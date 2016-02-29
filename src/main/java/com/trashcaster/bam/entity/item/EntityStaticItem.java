package com.trashcaster.bam.entity.item;

import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityStaticItem extends Entity {

	public EntityStaticItem(World worldIn) {
		super(worldIn);
		this.setSize(0.5f, 0.1f);
		// this.noClip = true;
	}

	public EntityStaticItem(World worldIn, double x, double y, double z, float yaw, float pitch) {
		this(worldIn);
		BlockPos pos = new BlockPos(Math.floor(x), y, Math.floor(z));
		double dX = x - (pos.getX() + 0.5d);
		double dZ = z - (pos.getZ() + 0.5d);

		dX = Math.min(Math.max(-0.12d, dX), 0.12d);
		dZ = Math.min(Math.max(-0.12d, dZ), 0.12d);
		x = (pos.getX() + 0.5d) + dX;
		z = (pos.getZ() + 0.5d) + dZ;
		y += Math.random()/16d;
		pitch += (float)Math.random()*5f;
		this.setLocationAndAngles(x, y, z, yaw, pitch);
	}

	public EntityStaticItem(World worldIn, double x, double y, double z) {
		this(worldIn, x, y, z, (float) Math.random() * 360f, 0f);
	}

	@Override
	protected void entityInit() {
		this.getDataWatcher().addObjectByDataType(10, 5);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompound) {
		setItem(ItemStack.loadItemStackFromNBT(tagCompound.getCompoundTag("Item")));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound) {
		if (this.getItem() != null) {
			tagCompound.setTag("Item", this.getItem().writeToNBT(new NBTTagCompound()));
		}
	}

	public boolean interactFirst(EntityPlayer playerIn) {
		if (this.getItem() != null && !worldObj.isRemote) {
			ItemStack itemstack = playerIn.getHeldItem();

			if (itemstack == null) {
				playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, this.getItem());
				this.setDead();
			} else if (itemstack != null && ItemStack.areItemStackTagsEqual(itemstack, this.getItem())
					&& ItemStack.areItemsEqual(itemstack, this.getItem())
					&& itemstack.getMaxStackSize() >= itemstack.stackSize + 1) {
				itemstack.stackSize++;
				this.setDead();
			} else {
				if (playerIn.inventory.addItemStackToInventory(this.getItem())) {
				    this.setDead();
				}
			}
		}
		return true;
	}

	@Override
	public float getCollisionBorderSize() {
		return 0f;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public void onUpdate() {
		if (!this.worldObj.isRemote) {
			if (getItem() == null) {
				this.setDead();
			}
		}
		super.onUpdate();
	}

	public void setItem(ItemStack item) {
		this.getDataWatcher().updateObject(10, item);
		this.getDataWatcher().setObjectWatched(10);
	}

	public ItemStack getItem() {
		return this.getDataWatcher().getWatchableObjectItemStack(10);
	}
}
