package com.trashcaster.bam.entity;

import com.trashcaster.bam.block.BlockChair;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EntitySittable extends Entity {

	private BlockPos tilePosition;

	public EntitySittable(World worldIn) {
		super(worldIn);
		this.setSize(0.01f, 0.01f);
		this.noClip = true;
	}

	public EntitySittable(World worldIn, BlockPos pos) {
		this(worldIn);
		this.tilePosition = pos;
		setPosition(pos.getX() + 0.5d, pos.getY() + 0.25d, pos.getZ() + 0.5d);
	}

	@Override
	protected void entityInit() {

	}

	@Override
	public double getMountedYOffset() {
		return this.height * 0.0D;
	}

	@Override
	protected boolean shouldSetPosAfterLoading() {
		return false;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompound) {
		int x = tagCompound.getInteger("TileX");
		int y = tagCompound.getInteger("TileY");
		int z = tagCompound.getInteger("TileZ");
		this.tilePosition = new BlockPos(x, y, z);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound) {
		if (this.tilePosition != null) {
			tagCompound.setInteger("TileX", this.tilePosition.getX());
			tagCompound.setInteger("TileY", this.tilePosition.getY());
			tagCompound.setInteger("TileZ", this.tilePosition.getZ());
		}
	}

	@Override
	public void onEntityUpdate() {
		if (!this.worldObj.isRemote) {
			if (this.riddenByEntity == null || this.tilePosition == null || this.worldObj.isAirBlock(this.tilePosition)) {
				this.setDead();
			}
		}
	}

	@Override
	public void updateRiderPosition() {
		super.updateRiderPosition();
		float min = this.rotationYaw-45f;
		float max = this.rotationYaw+45f;
		this.riddenByEntity.rotationYaw = Math.min(Math.max(min,this.riddenByEntity.rotationYaw), max);
		this.riddenByEntity.setRotationYawHead(this.riddenByEntity.rotationYaw);
	}

	public BlockPos getTilePos() {
		return this.tilePosition;
	}
}
