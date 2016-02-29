package com.trashcaster.bam.client.model;

import java.util.ArrayList;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFlyingCarpet extends ModelBase {

	public ModelRenderer carpet;

	public ModelFlyingCarpet() {
		textureWidth = 32;
		textureHeight = 16;

		carpet = new ModelRenderer(this, 0, 0);
		carpet.addBox(-12, -1, -16, 24, 2, 32);
		carpet.setTextureSize(32, 16);
		carpet.setRotationPoint(0F, 0F, 0f);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		carpet.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

}
