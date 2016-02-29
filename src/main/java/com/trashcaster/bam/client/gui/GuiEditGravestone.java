package com.trashcaster.bam.client.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.network.messages.MessageUpdateGravestone;
import com.trashcaster.bam.tileentity.TileEntityGravestone;

import net.minecraft.block.Block;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class GuiEditGravestone extends GuiScreen {
    private TileEntityGravestone tileGravestone;
    private GuiButton doneBtn;
    private int editLine;
    private int updateCounter;

	public GuiEditGravestone(EntityPlayer player, World world, int x, int y, int z) {
		this.tileGravestone = (TileEntityGravestone)world.getTileEntity(new BlockPos(x,y,z));
	}
	
    public void initGui()
    {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.add(this.doneBtn = new GuiButton(0, this.width / 2 - 100, this.height - this.height/6, I18n.format("gui.done", new Object[0])));
        this.tileGravestone.setEditable(false);
    }
    
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        NetHandlerPlayClient nethandlerplayclient = this.mc.getNetHandler();

        if (nethandlerplayclient != null)
        {
            //nethandlerplayclient.addToSendQueue(new C12PacketUpdateSign(this.tileGravestone.getPos(), this.tileGravestone.getLines()));
        }

        this.tileGravestone.setEditable(true);
    }
    
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.enabled)
        {
            if (button.id == 0)
            {
                this.tileGravestone.markDirty();
                BroadAdditionsMod.NETWORK.sendToServer(new MessageUpdateGravestone(tileGravestone.getLines(), tileGravestone.getPos()));
                this.mc.displayGuiScreen((GuiScreen)null);
            }
        }
    }
    
    public void updateScreen()
    {
        ++this.updateCounter;
    }
    
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (keyCode == 200)
        {
            this.editLine = this.editLine - 1 & 7;
        }

        if (keyCode == 208 || keyCode == 28 || keyCode == 156)
        {
            this.editLine = this.editLine + 1 & 7;
        }

        String s = this.tileGravestone.getLines().get(this.editLine);

        if (keyCode == 14 && s.length() > 0)
        {
            s = s.substring(0, s.length() - 1);
        }

        if (ChatAllowedCharacters.isAllowedCharacter(typedChar) && this.fontRendererObj.getStringWidth(s + typedChar) <= 90)
        {
            s = s + typedChar;
        }

        this.tileGravestone.getLines().set(this.editLine,s);

        if (keyCode == 1)
        {
            this.actionPerformed(this.doneBtn);
        }
    }
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("gravestone.edit", new Object[0]), this.width / 2, 20, 16777215);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)(this.width / 2), 0.0F, 50.0F);
        float f1 = 93.75F;
        GlStateManager.scale(-f1, -f1, -f1);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);

            int k = this.tileGravestone.getBlockMetadata();
            float f3 = 0.0F;

            if (k == 2)
            {
                f3 = 180.0F;
            }

            if (k == 4)
            {
                f3 = 90.0F;
            }

            if (k == 5)
            {
                f3 = -90.0F;
            }

            GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, -1.0625F, 0.0F);

        if (this.updateCounter / 6 % 2 == 0)
        {
            this.tileGravestone.lineBeingEdited = this.editLine;
        }

        GlStateManager.scale(1.5f, 1.5f, 1.5f);
        TileEntityRendererDispatcher.instance.renderTileEntityAt(this.tileGravestone, -0.5D, -0.5D, -0.5D, 0.0F);
        this.tileGravestone.lineBeingEdited = -1;
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
