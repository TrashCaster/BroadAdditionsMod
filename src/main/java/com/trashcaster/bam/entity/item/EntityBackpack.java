package com.trashcaster.bam.entity.item;

import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.common.CommonGuiHandler;
import com.trashcaster.bam.inventory.InventoryBackpack;
import com.trashcaster.bam.util.Content;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBackpack extends Entity {
	
	public InventoryBackpack inventory;

	public EntityBackpack(World worldIn) {
		super(worldIn);
		this.setSize(0.75f, 0.75f);
		this.inventory = new InventoryBackpack();
	}

	public EntityBackpack(World worldIn, double x, double y, double z, float yaw, float pitch) {
		this(worldIn);
		this.setLocationAndAngles(x, y, z, yaw, pitch);

		float f2 = 0.3F;
		motionX = (double) (-MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI) * f2);
		motionZ = (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI) * f2);
		motionY = (double) (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI) * f2 + 0.1F);
		float f3 = this.rand.nextFloat() * (float) Math.PI * 2.0F;
		f2 = 0.02F * this.rand.nextFloat();
		motionX += Math.cos((double) f3) * (double) f2;
		motionY += (double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
		motionZ += Math.sin((double) f3) * (double) f2;
	}

	public EntityBackpack(World worldIn, double x, double y, double z) {
		this(worldIn, x, y, z, (float) Math.random() * 360f, 0f);
	}

	@Override
	protected void entityInit() {
		this.getDataWatcher().addObjectByDataType(10, 5);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompound) {
		this.inventory = new InventoryBackpack();
		setItem(ItemStack.loadItemStackFromNBT(tagCompound.getCompoundTag("Item")));
		if (getItem() != null) {
			ItemStack item = getItem();
			NBTTagCompound tag = item.getTagCompound();
			if (tag == null) {
				item.setTagCompound(new NBTTagCompound());
			} else {
			    NBTTagCompound items = tag.getCompoundTag("Inventory");
			    this.inventory.readFromNBT(items);
			}
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound) {
		if (this.getItem() != null) {
			ItemStack item = this.getItem();
			NBTTagCompound tag = item.getTagCompound();
			if (tag == null) {
				tag = new NBTTagCompound();
			} else {
				tag.removeTag("Inventory");
			}
			NBTTagCompound items = new NBTTagCompound();
			this.inventory.writeToNBT(items);
			tag.setTag("Inventory", items);
			item.setTagCompound(tag);
			this.setItem(item);
			tagCompound.setTag("Item", this.getItem().writeToNBT(new NBTTagCompound()));
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		} else {
			if (!this.isDead && !this.worldObj.isRemote) {
				if (this.getItem() != null) {
					ItemStack item = this.getItem();
					this.entityDropItem(item, 0.5f);
					this.setDead();
				}
				this.setDead();
				this.setBeenAttacked();
			}

			return true;
		}
	}

	@Override
	public boolean interactFirst(EntityPlayer playerIn) {
		if (!this.worldObj.isRemote)
		playerIn.openGui(BroadAdditionsMod.INSTANCE, CommonGuiHandler.GUI_BACKPACK, this.worldObj, this.getEntityId(), 0, 0);
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
		if (getItem() == null) {
			this.setItem(new ItemStack(Content.Accessories.BACKPACK));
			this.getItem().getItem().onCreated(this.getItem(), this.worldObj, null);
		} else {
			super.onUpdate();

			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;
			this.motionY -= 0.03999999910593033D;
			this.noClip = this.pushOutOfBlocks(this.posX,
					(this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0D, this.posZ);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			boolean flag = (int) this.prevPosX != (int) this.posX || (int) this.prevPosY != (int) this.posY
					|| (int) this.prevPosZ != (int) this.posZ;

			if (flag || this.ticksExisted % 25 == 0) {
				if (this.worldObj.getBlockState(new BlockPos(this)).getBlock().getMaterial() == Material.lava) {
					this.motionY = 0.20000000298023224D;
					this.motionX = (double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
					this.motionZ = (double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
					this.playSound("random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
				}
			}

			float f = 0.98F;

			if (this.onGround) {
				f = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX),
						MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1,
						MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.98F;
			}

			this.motionX *= (double) f;
			this.motionY *= 0.9800000190734863D;
			this.motionZ *= (double) f;

			if (this.onGround) {
				this.motionY *= -0.5D;
			}

			this.handleWaterMovement();

			ItemStack item = getItem();
				NBTTagCompound tag = item.getTagCompound();
				if (tag != null) {
				    NBTTagCompound items = tag.getCompoundTag("Inventory");
				    this.inventory.readFromNBT(items);
				}
			if (item != null && item.stackSize <= 0) {
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
