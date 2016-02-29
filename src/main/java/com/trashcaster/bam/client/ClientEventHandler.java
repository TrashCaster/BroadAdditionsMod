package com.trashcaster.bam.client;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.client.gui.GuiAccessoryButton;
import com.trashcaster.bam.client.gui.inventory.GuiAccessories;
import com.trashcaster.bam.entity.EntityFlyingCarpet;
import com.trashcaster.bam.entity.EntitySittable;
import com.trashcaster.bam.network.messages.MessageRidingJumpKey;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class ClientEventHandler {

	public GuiButton button;

	@SubscribeEvent
	public void onGuiOpen(GuiScreenEvent.InitGuiEvent event) {
		if (event.gui instanceof GuiContainer && !(event.gui instanceof GuiAccessories)) {
			button = new GuiAccessoryButton(100, 0, event.gui.height/2 - 14, 18, 28, "button.switchHoverText.toAccessories");
			event.buttonList.add(button);
		}
	}
	
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		int code = Keyboard.getEventKey();
		Minecraft mc = Minecraft.getMinecraft();
		if (code == mc.gameSettings.keyBindJump.getKeyCode() && mc.thePlayer != null && mc.thePlayer.ridingEntity != null) {
			BroadAdditionsMod.NETWORK.sendToServer(new MessageRidingJumpKey(Keyboard.getEventKeyState()));
		}
	}

	@SubscribeEvent
	public void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
		if (event.entityPlayer.ridingEntity != null && (event.entityPlayer.ridingEntity instanceof EntitySittable || event.entityPlayer.ridingEntity instanceof EntityFlyingCarpet)) {
			event.setCanceled(true);

			AbstractClientPlayer player = (AbstractClientPlayer) event.entityPlayer;
			String skinType = player.getSkinType();
			if (skinType.equals("default")) {
				ClientProxy.RENDER_MOD_PLAYER_DEFAULT.doRender(player, event.x, event.y, event.z, 0f,
						event.partialRenderTick);
			}
			if (skinType.equals("slim")) {
				ClientProxy.RENDER_MOD_PLAYER_SLIM.doRender(player, event.x, event.y, event.z, 0f,
						event.partialRenderTick);
			}
		}
	}

	@SubscribeEvent
	public void onRenderPlayerPost(RenderPlayerEvent.Post event) {
		
	}
	

	@SubscribeEvent
	public void onModelBake(ModelBakeEvent event) {
	}

	@SubscribeEvent
	public void onRenderOverlay(RenderGameOverlayEvent.Pre event) {
		Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
		if (entity != null && entity.ridingEntity instanceof EntityFlyingCarpet) {
			EntityFlyingCarpet helicopter = (EntityFlyingCarpet) entity.ridingEntity;
			int width = event.resolution.getScaledWidth();
			int height = event.resolution.getScaledHeight();
			if (event.type.equals(ElementType.HEALTHMOUNT)) {
				double healthRatio = (helicopter.getHealth() / helicopter.getMaxHealth());

				int red = Math.max(0, Math.min(255,(int) (255d - (255d * healthRatio))));
				int green = Math.max(0, Math.min(255,(int) (255d * healthRatio)));
				int blue = Math.max(0, Math.min(255,(int) (65d * healthRatio)));
				Color bar = new Color(red, green, blue);
				Color bar2 = new Color(Math.max(0, red - 80), Math.max(0, green - 80), Math.max(0, blue - 80));
				event.setCanceled(true);
				GlStateManager.pushMatrix();
				int left = width / 2 + 10;
				int top = height - GuiIngameForge.right_height;
				Gui.drawRect(left, top, left + 81, top + 9, Color.BLACK.getRGB());
				this.zLevel += 10d;
				this.drawGradientRect(left + 1, top + 1, left + (int)Math.max(Math.ceil(81d * healthRatio), healthRatio == 0d ? 0:3) - 1, top + 9 - 1,
						bar.getRGB(), bar2.getRGB());
				this.zLevel -= 10d;
				GlStateManager.popMatrix();
			}
		}
	}

	public void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV) {
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
		worldrenderer.pos((double) (xCoord + 0.0F), (double) (yCoord + (float) maxV), (double) this.zLevel)
				.tex((double) ((float) (minU + 0) * f), (double) ((float) (minV + maxV) * f1)).endVertex();
		worldrenderer.pos((double) (xCoord + (float) maxU), (double) (yCoord + (float) maxV), (double) this.zLevel)
				.tex((double) ((float) (minU + maxU) * f), (double) ((float) (minV + maxV) * f1)).endVertex();
		worldrenderer.pos((double) (xCoord + (float) maxU), (double) (yCoord + 0.0F), (double) this.zLevel)
				.tex((double) ((float) (minU + maxU) * f), (double) ((float) (minV + 0) * f1)).endVertex();
		worldrenderer.pos((double) (xCoord + 0.0F), (double) (yCoord + 0.0F), (double) this.zLevel)
				.tex((double) ((float) (minU + 0) * f), (double) ((float) (minV + 0) * f1)).endVertex();
		tessellator.draw();
	}

	protected void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
		float f = (float) (startColor >> 24 & 255) / 255.0F;
		float f1 = (float) (startColor >> 16 & 255) / 255.0F;
		float f2 = (float) (startColor >> 8 & 255) / 255.0F;
		float f3 = (float) (startColor & 255) / 255.0F;
		float f4 = (float) (endColor >> 24 & 255) / 255.0F;
		float f5 = (float) (endColor >> 16 & 255) / 255.0F;
		float f6 = (float) (endColor >> 8 & 255) / 255.0F;
		float f7 = (float) (endColor & 255) / 255.0F;
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.shadeModel(7425);
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
		worldrenderer.pos((double) right, (double) top, (double) this.zLevel).color(f1, f2, f3, f).endVertex();
		worldrenderer.pos((double) left, (double) top, (double) this.zLevel).color(f1, f2, f3, f).endVertex();
		worldrenderer.pos((double) left, (double) bottom, (double) this.zLevel).color(f5, f6, f7, f4).endVertex();
		worldrenderer.pos((double) right, (double) bottom, (double) this.zLevel).color(f5, f6, f7, f4).endVertex();
		tessellator.draw();
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}

	float zLevel = -10f;
}
