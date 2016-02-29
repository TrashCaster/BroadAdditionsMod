package com.trashcaster.bam.client.model;

import com.trashcaster.bam.block.BlockChair;
import com.trashcaster.bam.block.BlockTable;
import com.trashcaster.bam.entity.EntityFlyingCarpet;
import com.trashcaster.bam.entity.EntitySittable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelModPlayer extends ModelBiped {
	public ModelRenderer bipedLeftArmwear;
	public ModelRenderer bipedRightArmwear;
	public ModelRenderer bipedLeftLegwear;
	public ModelRenderer bipedRightLegwear;
	public ModelRenderer bipedBodyWear;
	private ModelRenderer bipedCape;
	private ModelRenderer bipedDeadmau5Head;
	private boolean smallArms;

	public ModelModPlayer(float p_i46304_1_, boolean p_i46304_2_) {
		super(p_i46304_1_, 0.0F, 64, 64);
		this.smallArms = p_i46304_2_;
		this.bipedDeadmau5Head = new ModelRenderer(this, 24, 0);
		this.bipedDeadmau5Head.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, p_i46304_1_);
		this.bipedCape = new ModelRenderer(this, 0, 0);
		this.bipedCape.setTextureSize(64, 32);
		this.bipedCape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, p_i46304_1_);

		if (p_i46304_2_) {
			this.bipedLeftArm = new ModelRenderer(this, 32, 48);
			this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_);
			this.bipedLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);
			this.bipedRightArm = new ModelRenderer(this, 40, 16);
			this.bipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_);
			this.bipedRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);
			this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
			this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_ + 0.25F);
			this.bipedLeftArmwear.setRotationPoint(5.0F, 2.5F, 0.0F);
			this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
			this.bipedRightArmwear.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_ + 0.25F);
			this.bipedRightArmwear.setRotationPoint(-5.0F, 2.5F, 10.0F);
		} else {
			this.bipedLeftArm = new ModelRenderer(this, 32, 48);
			this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, p_i46304_1_);
			this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
			this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
			this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
			this.bipedLeftArmwear.setRotationPoint(5.0F, 2.0F, 0.0F);
			this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
			this.bipedRightArmwear.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
			this.bipedRightArmwear.setRotationPoint(-5.0F, 2.0F, 10.0F);
		}

		this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
		this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i46304_1_);
		this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		this.bipedLeftLegwear = new ModelRenderer(this, 0, 48);
		this.bipedLeftLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
		this.bipedLeftLegwear.setRotationPoint(1.9F, 12.0F, 0.0F);
		this.bipedRightLegwear = new ModelRenderer(this, 0, 32);
		this.bipedRightLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
		this.bipedRightLegwear.setRotationPoint(-1.9F, 12.0F, 0.0F);
		this.bipedBodyWear = new ModelRenderer(this, 16, 32);
		this.bipedBodyWear.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, p_i46304_1_ + 0.25F);
		this.bipedBodyWear.setRotationPoint(0.0F, 0.0F, 0.0F);
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_,
			float p_78088_6_, float p_78088_7_) {
		super.render(p_78088_1_, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_);
		GlStateManager.pushMatrix();

		if (this.isChild) {
			float f6 = 2.0F;
			GlStateManager.scale(1.0F / f6, 1.0F / f6, 1.0F / f6);
			GlStateManager.translate(0.0F, 24.0F * p_78088_7_, 0.0F);
			this.bipedLeftLegwear.render(p_78088_7_);
			this.bipedRightLegwear.render(p_78088_7_);
			this.bipedLeftArmwear.render(p_78088_7_);
			this.bipedRightArmwear.render(p_78088_7_);
			this.bipedBodyWear.render(p_78088_7_);
		} else {
			if (p_78088_1_.isSneaking()) {
				GlStateManager.translate(0.0F, 0.2F, 0.0F);
			}

			this.bipedLeftLegwear.render(p_78088_7_);
			this.bipedRightLegwear.render(p_78088_7_);
			this.bipedLeftArmwear.render(p_78088_7_);
			this.bipedRightArmwear.render(p_78088_7_);
			this.bipedBodyWear.render(p_78088_7_);
		}

		GlStateManager.popMatrix();
	}

	public void renderDeadmau5Head(float p_178727_1_) {
		copyModelAngles(this.bipedHead, this.bipedDeadmau5Head);
		this.bipedDeadmau5Head.rotationPointX = 0.0F;
		this.bipedDeadmau5Head.rotationPointY = 0.0F;
		this.bipedDeadmau5Head.render(p_178727_1_);
	}

	public void renderCape(float p_178728_1_) {
		this.bipedCape.render(p_178728_1_);
	}

	/**
	 * Sets the model's various rotation angles. For bipeds, par1 and par2 are
	 * used for animating the movement of arms and legs, where par1 represents
	 * the time(so that arms and legs swing back and forth) and par2 represents
	 * how "far" arms and legs can swing at most.
	 */
	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_,
			float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
		this.setBipedRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
		copyModelAngles(this.bipedLeftLeg, this.bipedLeftLegwear);
		copyModelAngles(this.bipedRightLeg, this.bipedRightLegwear);
		copyModelAngles(this.bipedLeftArm, this.bipedLeftArmwear);
		copyModelAngles(this.bipedRightArm, this.bipedRightArmwear);
		copyModelAngles(this.bipedBody, this.bipedBodyWear);

		if (p_78087_7_.isSneaking()) {
			this.bipedCape.rotationPointY = 2.0F;
		} else {
			this.bipedCape.rotationPointY = 0.0F;
		}
	}

	/*
	 * This method is copied from ModelBiped in order to let us modify the
	 * rendering
	 */
	public void setBipedRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_,
			float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
		this.bipedHead.rotateAngleY = p_78087_4_ / (180F / (float) Math.PI);
		this.bipedHead.rotateAngleX = p_78087_5_ / (180F / (float) Math.PI);
		this.bipedRightArm.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + (float) Math.PI) * 2.0F * p_78087_2_
				* 0.5F;
		this.bipedLeftArm.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 2.0F * p_78087_2_ * 0.5F;
		this.bipedRightArm.rotateAngleZ = 0.0F;
		this.bipedLeftArm.rotateAngleZ = 0.0F;
		this.bipedRightLeg.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_;
		this.bipedLeftLeg.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + (float) Math.PI) * 1.4F * p_78087_2_;
		this.bipedRightLeg.rotateAngleY = 0.0F;
		this.bipedLeftLeg.rotateAngleY = 0.0F;

		if (this.isRiding) {
			if (p_78087_7_.ridingEntity instanceof EntitySittable) {
				EntitySittable seat = (EntitySittable) p_78087_7_.ridingEntity;
				BlockPos seatPos = seat.getPosition();
				IBlockState state = seat.worldObj.getBlockState(seatPos);
				if (state.getBlock() instanceof BlockChair) {
					EnumFacing face = (EnumFacing) state.getValue(BlockChair.FACING);
					BlockPos offsetPos = seatPos.offset(face);
					IBlockState forwardState = seat.worldObj.getBlockState(offsetPos);
					if (forwardState.getBlock().isSideSolid(seat.worldObj, offsetPos, EnumFacing.UP)
							|| forwardState.getBlock() instanceof BlockTable) {
						this.bipedRightArm.rotateAngleX += -((float) Math.PI / 2.5F);
						this.bipedLeftArm.rotateAngleX += -((float) Math.PI / 2.5F);
					}
				}
				this.bipedRightLeg.rotateAngleX = -((float) Math.PI * 2F / 5F);
				this.bipedLeftLeg.rotateAngleX = -((float) Math.PI * 2F / 5F);
			} else if (p_78087_7_.ridingEntity instanceof EntityFlyingCarpet) {
				this.bipedRightArm.rotateAngleX += ((float) Math.PI / 10F);
				this.bipedLeftArm.rotateAngleX += ((float) Math.PI / 10F);
				this.bipedRightLeg.rotateAngleX += -((float) Math.PI / 2F);
				this.bipedLeftLeg.rotateAngleX += -((float) Math.PI / 2F);
				
			} else {
				this.bipedRightArm.rotateAngleX += -((float) Math.PI / 5F);
				this.bipedLeftArm.rotateAngleX += -((float) Math.PI / 5F);
				this.bipedRightLeg.rotateAngleX = -((float) Math.PI * 2F / 5F);
				this.bipedLeftLeg.rotateAngleX = -((float) Math.PI * 2F / 5F);
			}
			this.bipedRightLeg.rotateAngleY = ((float) Math.PI / 20F);
			this.bipedLeftLeg.rotateAngleY = -((float) Math.PI / 20F);
		}

		if (this.heldItemLeft != 0) {
			this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F
					- ((float) Math.PI / 10F) * (float) this.heldItemLeft;
		}
		this.bipedRightArm.rotateAngleY = 0.0F;
		this.bipedRightArm.rotateAngleZ = 0.0F;

		switch (this.heldItemRight) {
		case 0:
		case 2:
		default:
			break;
		case 1:
			this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F
					- ((float) Math.PI / 10F) * (float) this.heldItemRight;
			break;
		case 3:
			this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F
					- ((float) Math.PI / 10F) * (float) this.heldItemRight;
			this.bipedRightArm.rotateAngleY = -0.5235988F;
		}

		this.bipedLeftArm.rotateAngleY = 0.0F;
		float f6;
		float f7;

		if (this.swingProgress > -9990.0F) {
			f6 = this.swingProgress;
			this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * (float) Math.PI * 2.0F) * 0.2F;
			this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
			this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
			this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
			this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
			this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
			this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
			this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
			f6 = 1.0F - this.swingProgress;
			f6 *= f6;
			f6 *= f6;
			f6 = 1.0F - f6;
			f7 = MathHelper.sin(f6 * (float) Math.PI);
			float f8 = MathHelper.sin(this.swingProgress * (float) Math.PI) * -(this.bipedHead.rotateAngleX - 0.7F)
					* 0.75F;
			this.bipedRightArm.rotateAngleX = (float) ((double) this.bipedRightArm.rotateAngleX
					- ((double) f7 * 1.2D + (double) f8));
			this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
			this.bipedRightArm.rotateAngleZ += MathHelper.sin(this.swingProgress * (float) Math.PI) * -0.4F;
		}

		if (this.isSneak) {
			this.bipedBody.rotateAngleX = 0.5F;
			this.bipedRightArm.rotateAngleX += 0.4F;
			this.bipedLeftArm.rotateAngleX += 0.4F;
			this.bipedRightLeg.rotationPointZ = 4.0F;
			this.bipedLeftLeg.rotationPointZ = 4.0F;
			this.bipedRightLeg.rotationPointY = 9.0F;
			this.bipedLeftLeg.rotationPointY = 9.0F;
			this.bipedHead.rotationPointY = 1.0F;
		} else {
			this.bipedBody.rotateAngleX = 0.0F;
			this.bipedRightLeg.rotationPointZ = 0.1F;
			this.bipedLeftLeg.rotationPointZ = 0.1F;
			this.bipedRightLeg.rotationPointY = 12.0F;
			this.bipedLeftLeg.rotationPointY = 12.0F;
			this.bipedHead.rotationPointY = 0.0F;
		}
		
		if (p_78087_7_.ridingEntity != null && p_78087_7_.ridingEntity instanceof EntityFlyingCarpet) {
			this.bipedBody.rotateAngleX -= ((float) Math.PI/6);
			this.bipedLeftLeg.rotationPointZ -= 6.5f;
			this.bipedRightLeg.rotationPointZ -= 6.5f;
			this.bipedLeftLeg.rotationPointY -= 0.5f;
			this.bipedRightLeg.rotationPointY -= 0.5f;
		}

		this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
		this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
		this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
		this.bipedLeftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;

		if (this.aimedBow) {
			f6 = 0.0F;
			f7 = 0.0F;
			this.bipedRightArm.rotateAngleZ = 0.0F;
			this.bipedLeftArm.rotateAngleZ = 0.0F;
			this.bipedRightArm.rotateAngleY = -(0.1F - f6 * 0.6F) + this.bipedHead.rotateAngleY;
			this.bipedLeftArm.rotateAngleY = 0.1F - f6 * 0.6F + this.bipedHead.rotateAngleY + 0.4F;
			this.bipedRightArm.rotateAngleX = -((float) Math.PI / 2F) + this.bipedHead.rotateAngleX;
			this.bipedLeftArm.rotateAngleX = -((float) Math.PI / 2F) + this.bipedHead.rotateAngleX;
			this.bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
			this.bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
			this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
			this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
			this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
			this.bipedLeftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
		}

		copyModelAngles(this.bipedHead, this.bipedHeadwear);
	}

	public void renderRightArm() {
		this.bipedRightArm.render(0.0625F);
		this.bipedRightArmwear.render(0.0625F);
	}

	public void renderLeftArm() {
		this.bipedLeftArm.render(0.0625F);
		this.bipedLeftArmwear.render(0.0625F);
	}

	public void setInvisible(boolean invisible) {
		super.setInvisible(invisible);
		this.bipedLeftArmwear.showModel = invisible;
		this.bipedRightArmwear.showModel = invisible;
		this.bipedLeftLegwear.showModel = invisible;
		this.bipedRightLegwear.showModel = invisible;
		this.bipedBodyWear.showModel = invisible;
		this.bipedCape.showModel = invisible;
		this.bipedDeadmau5Head.showModel = invisible;
	}

	public void postRenderArm(float p_178718_1_) {
		if (this.smallArms) {
			++this.bipedRightArm.rotationPointX;
			this.bipedRightArm.postRender(p_178718_1_);
			--this.bipedRightArm.rotationPointX;
		} else {
			this.bipedRightArm.postRender(p_178718_1_);
		}
	}
}