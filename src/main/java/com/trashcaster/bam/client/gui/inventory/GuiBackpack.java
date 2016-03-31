package com.trashcaster.bam.client.gui.inventory;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.client.gui.GuiAccessoryButton;
import com.trashcaster.bam.entity.item.EntityBackpack;
import com.trashcaster.bam.inventory.ContainerAccessories;
import com.trashcaster.bam.inventory.ContainerBackpack;
import com.trashcaster.bam.inventory.InventoryAccessories;
import com.trashcaster.bam.inventory.InventoryBackpack;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiBackpack extends GuiContainer {

	private float xSize_lo;

	private float ySize_lo;

	private static final ResourceLocation inventoryBackground = new ResourceLocation(BroadAdditionsMod.MODID,"textures/gui/inventory_backpack.png");

	private final InventoryBackpack inventory;

	public GuiBackpack(EntityPlayer player, EntityBackpack entity, InventoryPlayer inventory,
			InventoryBackpack inventoryBackpack) {
		super(new ContainerBackpack(player, (EntityBackpack)entity, player.inventory, inventoryBackpack));
		this.inventory = inventoryBackpack;
	}
	
	public void initGui() {
		super.initGui();
		buttonList.add(new GuiAccessoryButton(100, 0, this.height/2 - 14, 18, 28, "button.switchHoverText.toInventory", true));
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		xSize_lo = mouseX;
		ySize_lo = mouseY;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items)
	 */
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

		String s = this.inventory.getDisplayName().getFormattedText();
		this.fontRendererObj.drawString(s, 86, 16, 4210752);
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the
	 * items)
	 */
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(inventoryBackground);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		GuiInventory.drawEntityOnScreen(guiLeft + 51, guiTop + 75, 30, guiLeft + 51 - mouseX, guiTop + 25 - mouseY, mc.thePlayer);
	}

	/**
	 * Copied straight out of vanilla - renders the player model on screen
	 */
	public void drawPlayerModel(int x, int y, int scale, float yaw, float pitch, EntityLivingBase entity) {
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 50.0F);
		GL11.glScalef(-scale, scale, scale);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		float f2 = entity.renderYawOffset;
		float f3 = entity.rotationYaw;
		float f4 = entity.rotationPitch;
		float f5 = entity.prevRotationYawHead;
		float f6 = entity.rotationYawHead;
		GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-((float) Math.atan(pitch / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
		entity.renderYawOffset = (float) Math.atan(yaw / 40.0F) * 20.0F;
		entity.rotationYaw = (float) Math.atan(yaw / 40.0F) * 40.0F;
		entity.rotationPitch = -((float) Math.atan(pitch / 40.0F)) * 20.0F;
		entity.rotationYawHead = entity.rotationYaw;
		entity.prevRotationYawHead = entity.rotationYaw;
		GL11.glTranslatef(0.0F, (float)entity.getYOffset(), 0.0F);
		mc.getRenderManager().playerViewY = 180.0F;
		mc.getRenderManager().renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		entity.renderYawOffset = f2;
		entity.rotationYaw = f3;
		entity.rotationPitch = f4;
		entity.prevRotationYawHead = f5;
		entity.rotationYawHead = f6;
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}
}
