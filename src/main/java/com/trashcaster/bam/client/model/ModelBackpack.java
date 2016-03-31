package com.trashcaster.bam.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBackpack extends ModelBase {
	ModelRenderer bagPart1;
	ModelRenderer bagPart2;
	ModelRenderer bagStrapRight;
	ModelRenderer bagStrapLeft;

	public ModelBackpack() {
		textureWidth = 64;
		textureHeight = 32;

		bagPart1 = new ModelRenderer(this, 0, 0);
		bagPart1.addBox(-4F, 0F, 2F, 8, 10, 3);
		bagPart1.setRotationPoint(0F, 0F, 0F);
		bagPart1.setTextureSize(64, 32);
		bagPart1.mirror = true;
		setRotation(bagPart1, 0F, 0F, 0F);
		bagPart2 = new ModelRenderer(this, 22, 0);
		bagPart2.addBox(-3F, 1F, 4F, 6, 8, 2);
		bagPart2.setRotationPoint(0F, 0F, 0F);
		bagPart2.setTextureSize(64, 32);
		bagPart2.mirror = true;
		setRotation(bagPart2, 0.1047198F, 0F, 0F);
		bagStrapRight = new ModelRenderer(this, 0, 13);
		bagStrapRight.addBox(-4F, -1F, -2F, 1, 12, 5);
		bagStrapRight.setRotationPoint(0F, 0F, 0F);
		bagStrapRight.setTextureSize(64, 32);
		bagStrapRight.mirror = true;
		setRotation(bagStrapRight, 0F, 0F, 0F);
		bagStrapLeft = new ModelRenderer(this, 0, 13);
		bagStrapLeft.addBox(3F, -1F, -2F, 1, 12, 5);
		bagStrapLeft.setRotationPoint(0F, 0F, 0F);
		bagStrapLeft.setTextureSize(64, 32);
		bagStrapLeft.mirror = true;
		setRotation(bagStrapLeft, 0F, 0F, 0F);
		bagStrapLeft.mirror = false;
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		bagPart1.render(f5);
		bagPart2.render(f5);
		bagStrapRight.render(f5);
		bagStrapLeft.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

}
