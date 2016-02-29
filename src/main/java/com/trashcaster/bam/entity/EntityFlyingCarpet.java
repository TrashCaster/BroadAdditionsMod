package com.trashcaster.bam.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jcraft.jorbis.Block;
import com.trashcaster.bam.BroadAdditionsMod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class EntityFlyingCarpet extends EntityLiving {

	public boolean ascend = false;
	public double offsetY = 0d;
	public double prevOffsetY = 0d;

	private ItemStack item;

	public EntityFlyingCarpet(World worldIn) {
		super(worldIn);
		this.setSize(2F, 0.25F);
		this.stepHeight = 0.5F;
		this.ignoreFrustumCheck = true;
	}

	public EntityFlyingCarpet(World worldIn, double x, double y, double z, float yaw) {
		this(worldIn);
		this.setLocationAndAngles(x, y, z, yaw, 0f);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		this.getDataWatcher().addObjectByDataType(10, 5);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.125D);
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	public double getMountedYOffset() {
		return 0.1d + this.offsetY;
	}

	@Override
	public boolean interact(EntityPlayer playerIn) {
		if (!playerIn.worldObj.isRemote) {
			if (this.riddenByEntity == null) {
				playerIn.mountEntity(this);
			}
			return true;
		}
		return true;
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
		if (this.getEntityItem() != null)
			this.entityDropItem(this.getEntityItem(), 0.25f);
	}

	public boolean getCanSpawnHere() {
		if (worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(1000, 1000, 1000))
				.isEmpty()) {
			if (!worldObj.getEntitiesWithinAABB(EntityVillager.class, this.getEntityBoundingBox().expand(200, 200, 200))
					.isEmpty()) {
				System.out.println("Spawned at: " + this.posX + "," + this.posY + "," + this.posZ);
				return true;
			}
		}

		return false;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (this.getEntityItem() == null) {
			this.setEntityItemStack(new ItemStack(BroadAdditionsMod.Content.Misc.FLYING_CARPET));
		}
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.prevRotationPitch = this.rotationPitch;
		this.onGround = false;
		this.motionY *= 0.25d;
		if (!this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().offset(0, -0.1d, 0)).isEmpty()) {
			this.onGround = true;
		}
		this.prevOffsetY = this.offsetY;
		this.offsetY = Math.sin(Math.toRadians(this.ticksExisted * 8 % 360)) / 12d;

		double healthRatio = Math.max(this.getHealth() / this.getMaxHealth(), 0.25f);

		if (!this.worldObj.isRemote) {
			if (!this.onGround && this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) this.riddenByEntity;
				if (!player.capabilities.isCreativeMode) {
					this.attackEntityFrom(new DamageSource("flight"), 1f);
				}
			} else {
				boolean previouslyBelow = false;
				if (this.getHealth() < this.getMaxHealth()) {
					previouslyBelow = true;
				}
				this.heal(1f);
				if (this.getHealth() < this.getMaxHealth()) {
					this.playSound("random.pop", 0.1f, 0.5f + rand.nextFloat() * 0.25f);
				} else if (previouslyBelow) {
					this.playSound("random.pop", 1f, 0f);
				}
			}
			this.prevRotationYaw = this.rotationYaw;
			if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase) {
				EntityLivingBase living = (EntityLivingBase) this.riddenByEntity;
				this.rotationYaw = living.rotationYaw;
				this.setRotation(this.rotationYaw, this.rotationPitch);
				living.rotationYaw = this.rotationYaw;
				living.rotationYawHead = this.rotationYaw;
			}
			if (this.riddenByEntity == null || !(this.riddenByEntity instanceof EntityLivingBase)) {
				this.ascend = false;
			}
			if (this.ascend) {
				this.motionY = 0.025d;
				this.motionY *= healthRatio;
			}
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
		}
		this.doBlockCollisions();
	}

	public void knockBack(Entity entityIn, float p_70653_2_, double p_70653_3_, double p_70653_5_) {
		// override to stop getting hit around
	}

	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.getEntityItem() != null && !source.getDamageType().equals("flight")) {
			ItemStack stack = this.getEntityItem();
			for (int i = 0; i < 5; ++i) {
				Vec3 vec3 = new Vec3(((double) this.rand.nextFloat() - 0.5D), Math.random() * 0.1D + 0.1D, 0.0D);
				vec3 = vec3.rotatePitch(-this.rotationPitch * (float) Math.PI / 180.0F);
				vec3 = vec3.rotateYaw(-(this.rotationYaw + this.rand.nextFloat() * 360f) * (float) Math.PI / 180.0F);
				double d0 = (double) (-this.rand.nextFloat()) * 0.6D - 0.3D;
				Vec3 vec31 = new Vec3(((double) this.rand.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
				vec31 = vec31.rotatePitch(-this.rotationPitch * (float) Math.PI / 180.0F);
				vec31 = vec31.rotateYaw(-(this.rotationYaw + this.rand.nextFloat() * 360f) * (float) Math.PI / 180.0F);
				vec31 = vec31.addVector(this.posX, this.posY + (double) this.getEyeHeight() * 4d, this.posZ);
				this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, vec31.xCoord, vec31.yCoord, vec31.zCoord,
						vec3.xCoord, vec3.yCoord + 0.05D, vec3.zCoord,
						new int[] { Item.getIdFromItem(stack.getItem()) });
			}
		}
		if (source.equals(DamageSource.outOfWorld) && !this.worldObj.isRemote) {
			this.setDead();
		}
		if (source.getDamageType().equals("player") && !this.worldObj.isRemote) {
			this.dropFewItems(true, 0);
			this.setDead();
		}
		if (source.getDamageType().equals("flight") && !this.worldObj.isRemote) {
			return super.attackEntityFrom(source, amount);
		}
		if (this.riddenByEntity != null && source.getEntity() != this.riddenByEntity
				&& !source.getDamageType().equals("inWall")) {
			return this.riddenByEntity.attackEntityFrom(source, amount);
		}
		return true;
	}

	public void fall(float distance, float damageMultiplier) {
	}

	public boolean canBePushed() {
		return true;
	}

	public void applyEntityCollision(Entity entityIn) {
		entityIn.motionX *= -1d;
		entityIn.motionY *= -1d;
		entityIn.motionZ *= -1d;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
	}

	@Override
	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_) {
		if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase) {
			p_70612_1_ = ((EntityLivingBase) this.riddenByEntity).moveStrafing * 0.5F * 0.5f;
			p_70612_2_ = ((EntityLivingBase) this.riddenByEntity).moveForward * 0.5f;

			if (this.onGround) {
				p_70612_1_ *= 3f;
				p_70612_2_ *= 3f;
			}

			if (!this.worldObj.isRemote) {
				this.setAIMoveSpeed(
						(float) this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
				super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
			}
		} else {
			super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
		}
	}

	public int getMaxSpawnedInChunk() {
		return 1;
	}

	@Override
	public ItemStack getHeldItem() {
		return null;
	}

	@Override
	public ItemStack getEquipmentInSlot(int slotIn) {
		return null;
	}

	@Override
	public ItemStack getCurrentArmor(int slotIn) {
		return null;
	}

	@Override
	public void setCurrentItemOrArmor(int slotIn, ItemStack stack) {
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		if (this.getEntityItem() != null) {
			tagCompound.setTag("Item", this.getEntityItem().writeToNBT(new NBTTagCompound()));
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound) {
		super.readEntityFromNBT(tagCompound);

		NBTTagCompound nbttagcompound = tagCompound.getCompoundTag("Item");
		this.setEntityItemStack(ItemStack.loadItemStackFromNBT(nbttagcompound));
	}

	@Override
	public ItemStack[] getInventory() {
		return new ItemStack[1];
	}

	public void setAscend(boolean state) {
		this.ascend = state;
	}

	@Override
	public Entity[] getParts() {
		return null;
	}

	@Override
	protected String getHurtSound() {
		return Blocks.carpet.stepSound.getBreakSound();
	}

	@Override
	protected String getDeathSound() {
		return Blocks.carpet.stepSound.getBreakSound();
	}

	@Override
	public void updateRiderPosition() {
		if (this.riddenByEntity != null) {
			float f = MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F);
			float f1 = MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F);
			float amount = 0.5f;
			this.riddenByEntity.setPosition(this.posX + (double) (f * amount),
					this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(),
					this.posZ - (double) (f1 * amount));
		}
	}

	public ItemStack getEntityItem() {
		ItemStack itemstack = this.getDataWatcher().getWatchableObjectItemStack(10);
		return itemstack;
	}

	/**
	 * Sets the ItemStack for this entity
	 */
	public void setEntityItemStack(ItemStack stack) {
		this.getDataWatcher().updateObject(10, stack);
		this.getDataWatcher().setObjectWatched(10);
	}
}
