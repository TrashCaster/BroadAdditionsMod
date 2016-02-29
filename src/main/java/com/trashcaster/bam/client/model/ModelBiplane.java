package com.trashcaster.bam.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBiplane extends ModelBase {
	public ModelRenderer topWing;
	public ModelRenderer bottomWing;
	public ModelRenderer propellerBlade;
	public ModelRenderer propellerNose;
	public ModelRenderer bodyFront;
	public ModelRenderer bodyMiddle;
	public ModelRenderer bodyBack;
	public ModelRenderer bodyBottom;
	public ModelRenderer bodyLeft;
	public ModelRenderer bodyRight;
	public ModelRenderer tail;
	public ModelRenderer tailWing;
	public ModelRenderer tailFin;
	public ModelRenderer leftStrut;
	public ModelRenderer rightStrut;
	public ModelRenderer frontWheels;
	public ModelRenderer backWheel;

	public ModelBiplane() {
		textureWidth = 128;
		textureHeight = 64;

		topWing = new ModelRenderer(this, 0, 0);
		topWing.addBox(-15F, -4F, 0F, 30, 8, 1);
		topWing.setRotationPoint(0F, 6F, 0F);
		topWing.setTextureSize(128, 64);
		topWing.mirror = true;
		setRotation(topWing, 1.570796F, 0F, 0F);
		bottomWing = new ModelRenderer(this, 62, 0);
		bottomWing.addBox(-15F, -4F, 0F, 30, 8, 1);
		bottomWing.setRotationPoint(0F, 21F, 0F);
		bottomWing.setTextureSize(128, 64);
		bottomWing.mirror = true;
		setRotation(bottomWing, 1.570796F, 0F, 0F);
		propellerBlade = new ModelRenderer(this, 0, 9);
		propellerBlade.addBox(-7F, -1F, -0.5F, 14, 1, 1);
		propellerBlade.setRotationPoint(0F, 18F, -8F);
		propellerBlade.setTextureSize(128, 64);
		propellerBlade.mirror = true;
		setRotation(propellerBlade, 1.570796F, 0F, 0F);
		propellerNose = new ModelRenderer(this, 30, 9);
		propellerNose.addBox(-1.5F, -2F, -1.5F, 3, 2, 3);
		propellerNose.setRotationPoint(0F, 18F, -8F);
		propellerNose.setTextureSize(128, 64);
		propellerNose.mirror = true;
		setRotation(propellerNose, 1.570796F, 0F, 0F);
		bodyFront = new ModelRenderer(this, 42, 9);
		bodyFront.addBox(-5F, 0F, -3F, 10, 3, 6);
		bodyFront.setRotationPoint(0F, 18F, -8F);
		bodyFront.setTextureSize(128, 64);
		bodyFront.mirror = true;
		setRotation(bodyFront, 1.570796F, 0F, 0F);
		bodyMiddle = new ModelRenderer(this, 74, 9);
		bodyMiddle.addBox(-6F, 0F, -4F, 12, 10, 8);
		bodyMiddle.setRotationPoint(0F, 18F, -5F);
		bodyMiddle.setTextureSize(128, 64);
		bodyMiddle.mirror = true;
		setRotation(bodyMiddle, 1.570796F, 0F, 0F);
		bodyBack = new ModelRenderer(this, 34, 18);
		bodyBack.addBox(-6F, 0F, -4F, 12, 2, 8);
		bodyBack.setRotationPoint(0F, 18F, 16F);
		bodyBack.setTextureSize(128, 64);
		bodyBack.mirror = true;
		setRotation(bodyBack, 1.570796F, 0F, 0F);
		bodyBottom = new ModelRenderer(this, 56, 28);
		bodyBottom.addBox(-5F, 0F, -4F, 10, 11, 1);
		bodyBottom.setRotationPoint(0F, 18F, 5F);
		bodyBottom.setTextureSize(128, 64);
		bodyBottom.mirror = true;
		setRotation(bodyBottom, 1.570796F, 0F, 0F);
		bodyLeft = new ModelRenderer(this, 78, 27);
		bodyLeft.addBox(5F, 0F, -4F, 1, 11, 8);
		bodyLeft.setRotationPoint(0F, 18F, 5F);
		bodyLeft.setTextureSize(128, 64);
		bodyLeft.mirror = true;
		setRotation(bodyLeft, 1.570796F, 0F, 0F);
		bodyRight = new ModelRenderer(this, 96, 27);
		bodyRight.addBox(-6F, 0F, -4F, 1, 11, 8);
		bodyRight.setRotationPoint(0F, 18F, 5F);
		bodyRight.setTextureSize(128, 64);
		bodyRight.mirror = true;
		setRotation(bodyRight, 1.570796F, 0F, 0F);
		tail = new ModelRenderer(this, 50, 40);
		tail.addBox(-4F, 0F, -3F, 8, 12, 6);
		tail.setRotationPoint(0F, 18F, 18F);
		tail.setTextureSize(128, 64);
		tail.mirror = true;
		setRotation(tail, 1.570796F, 0F, 0F);
		tailWing = new ModelRenderer(this, 78, 56);
		tailWing.addBox(-10F, 0F, -1F, 20, 6, 2);
		tailWing.setRotationPoint(0F, 18F, 28F);
		tailWing.setTextureSize(128, 64);
		tailWing.mirror = true;
		setRotation(tailWing, 1.570796F, 0F, 0F);
		tailFin = new ModelRenderer(this, 0, 33);
		tailFin.addBox(-1F, -2F, -2F, 2, 6, 14);
		tailFin.setRotationPoint(0F, 18F, 27F);
		tailFin.setTextureSize(128, 64);
		tailFin.mirror = true;
		setRotation(tailFin, 0.7853982F, 0F, 0F);
		leftStrut = new ModelRenderer(this, 0, 11);
		leftStrut.addBox(10F, -4F, 0F, 1, 8, 14);
		leftStrut.setRotationPoint(0F, 20F, 0F);
		leftStrut.setTextureSize(128, 64);
		leftStrut.mirror = true;
		setRotation(leftStrut, 1.570796F, 0F, 0F);
		rightStrut = new ModelRenderer(this, 0, 11);
		rightStrut.addBox(-11F, -4F, 0F, 1, 8, 14);
		rightStrut.setRotationPoint(0F, 20F, 0F);
		rightStrut.setTextureSize(128, 64);
		rightStrut.mirror = true;
		setRotation(rightStrut, 1.570796F, 0F, 0F);
		frontWheels = new ModelRenderer(this, 0, 60);
		frontWheels.addBox(-7F, -6F, -4F, 14, 2, 2);
		frontWheels.setRotationPoint(0F, 20F, 5F);
		frontWheels.setTextureSize(128, 64);
		frontWheels.mirror = true;
		setRotation(frontWheels, 1.570796F, 0F, 0F);
		backWheel = new ModelRenderer(this, 0, 56);
		backWheel.addBox(-1F, 22F, -4F, 2, 2, 2);
		backWheel.setRotationPoint(0F, 20F, 5F);
		backWheel.setTextureSize(128, 64);
		backWheel.mirror = true;
		setRotation(backWheel, 1.570796F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		topWing.render(f5);
		bottomWing.render(f5);
		propellerBlade.render(f5);
		propellerNose.render(f5);
		bodyFront.render(f5);
		bodyMiddle.render(f5);
		bodyBack.render(f5);
		bodyBottom.render(f5);
		bodyLeft.render(f5);
		bodyRight.render(f5);
		tail.render(f5);
		tailWing.render(f5);
		tailFin.render(f5);
		leftStrut.render(f5);
		rightStrut.render(f5);
		frontWheels.render(f5);
		backWheel.render(f5);
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
