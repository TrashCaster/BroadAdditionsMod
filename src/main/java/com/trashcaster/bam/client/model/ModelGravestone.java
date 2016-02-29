package com.trashcaster.bam.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGravestone extends ModelBase {
	ModelRenderer gravestone1;
	ModelRenderer gravestone2;
	ModelRenderer gravestone3;
	ModelRenderer gravestoneBase;

	public ModelGravestone() {
		textureWidth = 64;
		textureHeight = 32;

		gravestone1 = new ModelRenderer(this, 0, 0);
		gravestone1.addBox(-6F, 0F, -6F, 12, 12, 3);
		gravestone1.setRotationPoint(0F, 12F, 1F);
		gravestone1.setTextureSize(64, 32);
		gravestone1.mirror = true;
		setRotation(gravestone1, 0F, 0F, 0F);
		gravestone2 = new ModelRenderer(this, 30, 0);
		gravestone2.addBox(-5F, 0F, -6F, 10, 1, 3);
		gravestone2.setRotationPoint(0F, 11F, 1F);
		gravestone2.setTextureSize(64, 32);
		gravestone2.mirror = true;
		setRotation(gravestone2, 0F, 0F, 0F);
		gravestone3 = new ModelRenderer(this, 30, 4);
		gravestone3.addBox(-3F, 0F, -6F, 6, 1, 3);
		gravestone3.setRotationPoint(0F, 10F, 1F);
		gravestone3.setTextureSize(64, 32);
		gravestone3.mirror = true;
		setRotation(gravestone3, 0F, 0F, 0F);
		gravestoneBase = new ModelRenderer(this, 0, 15);
		gravestoneBase.addBox(-7F, 0.5F, -2.5F, 14, 4, 5);
		gravestoneBase.setRotationPoint(0F, 22F, -6F);
		gravestoneBase.setTextureSize(64, 32);
		gravestoneBase.mirror = true;
		setRotation(gravestoneBase, 0.6108652F, 0F, 0F);
	}

	public void render(boolean renderBase) {
		gravestone1.render(0.0625f);
		gravestone2.render(0.0625f);
		gravestone3.render(0.0625f);
		if (renderBase) {
		    gravestoneBase.render(0.0625f);
		}
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
