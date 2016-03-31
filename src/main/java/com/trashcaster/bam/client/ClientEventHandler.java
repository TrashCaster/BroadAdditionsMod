package com.trashcaster.bam.client;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.client.gui.GuiAccessoryButton;
import com.trashcaster.bam.client.gui.inventory.GuiAccessories;
import com.trashcaster.bam.entity.PlayerData;
import com.trashcaster.bam.network.messages.MessageBoostKey;
import com.trashcaster.bam.util.Util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.GuiScreenEvent;
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
		boolean state = Keyboard.getEventKeyState();
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		if (code == mc.gameSettings.keyBindJump.getKeyCode() && state && Util.getBoost(player) > 0) {
			BroadAdditionsMod.NETWORK.sendToServer(new MessageBoostKey());

			double speed = (0.125d+0.125d*(double)Util.getBoost(player))/2d;

			player.motionX += Math.cos((player.rotationYaw+90) * (float)Math.PI/180f)*speed;
			player.motionY += speed*1.5d;
			player.motionZ += Math.sin((player.rotationYaw+90) * (float)Math.PI/180f)*speed;

			player.setVelocity(player.motionX, player.motionY, player.motionZ);
		}
	}

	@SubscribeEvent
	public void onRenderPlayerPost(RenderPlayerEvent.Post event) {

	}
}
