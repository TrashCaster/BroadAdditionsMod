package com.trashcaster.bam.client.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.lwjgl.opengl.GL11;

import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.network.messages.MessageSwitchInventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import scala.actors.threadpool.Arrays;

public class GuiAccessoryButton extends GuiButton {
	ResourceLocation tabResource = new ResourceLocation(BroadAdditionsMod.MODID, "textures/gui/accessory_tab.png");
	int hoverTime = 0;
	boolean isAccessoryInventory = false;
	int delay = 10;

	public GuiAccessoryButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
	}

	public GuiAccessoryButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, boolean isAccessoryInventory) {
		this(buttonId, x, y, widthIn, heightIn, buttonText);
		this.isAccessoryInventory = isAccessoryInventory;
	}

	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		boolean press = super.mousePressed(mc, mouseX, mouseY);
		if (press && delay == 0) {
			if (!this.isAccessoryInventory) {
			    MessageSwitchInventory message = new MessageSwitchInventory(true);
			    BroadAdditionsMod.NETWORK.sendToServer(message);
			} else {
				Minecraft.getMinecraft().displayGuiScreen(new GuiInventory(Minecraft.getMinecraft().thePlayer));
			}
		}
		return press;
	}

	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (delay > 0) {
			delay --;
		}
		if (this.hovered && this.hoverTime < 5) {
			this.hoverTime++;
		}
		if (!this.hovered && this.hoverTime > 0) {
			this.hoverTime--;
		}

		if (this.visible) {
			mc.getTextureManager().bindTexture(tabResource);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width
					&& mouseY < this.yPosition + this.height;
			int i = this.getHoverState(this.hovered);

			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			GlStateManager.blendFunc(770, 771);
			this.drawTexturedModalRect(this.xPosition + (4 * this.hoverTime) - 25, this.yPosition, 0, 28 * i, 39, 28);
			GlStateManager.disableLighting();
			this.mouseDragged(mc, mouseX, mouseY);
			int j = 14737632;

			if (packedFGColour != 0) {
				j = packedFGColour;
			} else if (!this.enabled) {
				j = 10526880;
			}
			if (this.hovered) {
				String[] displayStrings = I18n.format(this.displayString, new Object[0]).split(Pattern.quote("\\n"));
				List<String> strings = Arrays.asList(displayStrings);
				int offsetX = 0;
				for (int k=0; k<strings.size(); k++) {
					String s = strings.get(k).replaceAll(Pattern.quote("\\&"), "\u00a7");
					strings.set(k, s);
					offsetX = Math.max(offsetX, Minecraft.getMinecraft().fontRendererObj.getStringWidth(s));
				}
				ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
				GlStateManager.pushMatrix();
				GlStateManager.translate(0, res.getScaledHeight_double()-20, 0);
				this.drawHoveringText(strings, 20+offsetX+5, 0, Minecraft.getMinecraft().fontRendererObj);
				GlStateManager.popMatrix();
			}
			GlStateManager.disableLighting();
			GlStateManager.enableLighting();
		}
	}

	public void drawButtonForegroundLayer(int mouseX, int mouseY) {

	}

	protected void drawHoveringText(List<String> textLines, int x, int y, FontRenderer font) {
		if (!textLines.isEmpty()) {
			GlStateManager.disableRescaleNormal();
			RenderHelper.disableStandardItemLighting();
			GlStateManager.disableLighting();
			GlStateManager.disableDepth();
			int i = 0;

			for (String s : textLines) {
				int j = font.getStringWidth(s);

				if (j > i) {
					i = j;
				}
			}

			int l1 = x + 12;
			int i2 = y - 12;
			int k = 8;

			if (textLines.size() > 1) {
				k += 2 + (textLines.size() - 1) * 10;
			}

			if (l1 + i > this.width) {
				l1 -= 28 + i;
			}

			if (i2 + k + 6 > this.height) {
				i2 = this.height - k - 6;
			}

			this.zLevel = 300.0F;
			int l = -267386864;
			this.drawGradientRect(l1 - 3, i2 - 4, l1 + i + 3, i2 - 3, l, l);
			this.drawGradientRect(l1 - 3, i2 + k + 3, l1 + i + 3, i2 + k + 4, l, l);
			this.drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 + k + 3, l, l);
			this.drawGradientRect(l1 - 4, i2 - 3, l1 - 3, i2 + k + 3, l, l);
			this.drawGradientRect(l1 + i + 3, i2 - 3, l1 + i + 4, i2 + k + 3, l, l);
			int i1 = 1347420415;
			int j1 = (i1 & 16711422) >> 1 | i1 & -16777216;
			this.drawGradientRect(l1 - 3, i2 - 3 + 1, l1 - 3 + 1, i2 + k + 3 - 1, i1, j1);
			this.drawGradientRect(l1 + i + 2, i2 - 3 + 1, l1 + i + 3, i2 + k + 3 - 1, i1, j1);
			this.drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 - 3 + 1, i1, i1);
			this.drawGradientRect(l1 - 3, i2 + k + 2, l1 + i + 3, i2 + k + 3, j1, j1);

			for (int k1 = 0; k1 < textLines.size(); ++k1) {
				String s1 = (String) textLines.get(k1);
				font.drawStringWithShadow(s1, (float) l1, (float) i2, -1);

				if (k1 == 0) {
					i2 += 2;
				}

				i2 += 10;
			}

			this.zLevel = 0.0F;
			GlStateManager.enableLighting();
			GlStateManager.enableDepth();
			RenderHelper.enableStandardItemLighting();
			GlStateManager.enableRescaleNormal();
		}
	}

}
