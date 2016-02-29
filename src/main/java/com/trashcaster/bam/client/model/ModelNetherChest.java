package com.trashcaster.bam.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelNetherChest extends ModelBase
{
    public ModelRenderer chestFront;
    public ModelRenderer chestBack;
    public ModelRenderer chestRight;
    public ModelRenderer chestLeft;
    public ModelRenderer chestBottom;
    public ModelRenderer chestPortal;
    public ModelRenderer chestTop;
    public ModelRenderer chestKnob;
  
  public ModelNetherChest()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      chestFront = new ModelRenderer(this, 0, 0);
      chestFront.addBox(-7F, 0F, -7F, 14, 10, 2);
      chestFront.setRotationPoint(0F, 14F, 0F);
      chestFront.setTextureSize(64, 64);
      chestFront.mirror = true;
      setRotation(chestFront, 0F, 0F, 0F);
      chestBack = new ModelRenderer(this, 32, 0);
      chestBack.addBox(-7F, 0F, 5F, 14, 10, 2);
      chestBack.setRotationPoint(0F, 14F, 0F);
      chestBack.setTextureSize(64, 64);
      chestBack.mirror = true;
      setRotation(chestBack, 0F, 0F, 0F);
      chestRight = new ModelRenderer(this, 0, 12);
      chestRight.addBox(-7F, 0F, -5F, 2, 10, 10);
      chestRight.setRotationPoint(0F, 14F, 0F);
      chestRight.setTextureSize(64, 64);
      chestRight.mirror = true;
      setRotation(chestRight, 0F, 0F, 0F);
      chestLeft = new ModelRenderer(this, 24, 12);
      chestLeft.addBox(5F, 0F, -5F, 2, 10, 10);
      chestLeft.setRotationPoint(0F, 14F, 0F);
      chestLeft.setTextureSize(64, 64);
      chestLeft.mirror = true;
      setRotation(chestLeft, 0F, 0F, 0F);
      chestBottom = new ModelRenderer(this, 0, 32);
      chestBottom.addBox(-5F, 8F, -5F, 10, 2, 10);
      chestBottom.setRotationPoint(0F, 14F, 0F);
      chestBottom.setTextureSize(64, 64);
      chestBottom.mirror = true;
      setRotation(chestBottom, 0F, 0F, 0F);
      chestPortal = new ModelRenderer(this, 40, 32);
      chestPortal.addBox(-5F, -5F, 2F, 10, 10, 2);
      chestPortal.setRotationPoint(0F, 14F, 0F);
      chestPortal.setTextureSize(64, 64);
      chestPortal.mirror = true;
      setRotation(chestPortal, -1.570796F, 0F, 0F);
      chestTop = new ModelRenderer(this, 0, 44);
      chestTop.addBox(-7F, -4F, -14F, 14, 4, 14);
      chestTop.setRotationPoint(0F, 14F, 7F);
      chestTop.setTextureSize(64, 64);
      chestTop.mirror = true;
      setRotation(chestTop, 0F, 0F, 0F);
      chestKnob = new ModelRenderer(this, 48, 12);
      chestKnob.addBox(-1F, -2F, -15F, 2, 4, 1);
      chestKnob.setRotationPoint(0F, 14F, 7F);
      chestKnob.setTextureSize(64, 64);
      chestKnob.mirror = true;
      setRotation(chestKnob, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    chestFront.render(f5);
    chestBack.render(f5);
    chestRight.render(f5);
    chestLeft.render(f5);
    chestBottom.render(f5);
    chestPortal.render(f5);
    chestTop.render(f5);
    chestKnob.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}
