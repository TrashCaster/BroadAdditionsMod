package com.trashcaster.bam.client.renderer.tileentity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.block.BlockGravestone;
import com.trashcaster.bam.client.ClientProxy;
import com.trashcaster.bam.client.model.ModelGravestone;
import com.trashcaster.bam.tileentity.TileEntityGravestone;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelHumanoidHead;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderGiantZombie;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;

public class TileEntityGravestoneRenderer extends TileEntitySpecialRenderer<TileEntityGravestone> {

	private ModelGravestone gravestoneModel = new ModelGravestone();
	private ModelHumanoidHead headModel = new ModelHumanoidHead();

	private static final ResourceLocation GRAVESTONE_TEXTURE = new ResourceLocation(BroadAdditionsMod.MODID,
			"textures/entity/gravestone.png");

	@Override
	public void renderTileEntityAt(TileEntityGravestone tileEntity, double posX, double posY, double posZ,
			float partialTicks, int destroyProgress) {
		
		float f1 = 0.6666667F;
		float angle = 0f;
		BlockPos blockpos = new BlockPos(0, 0, 0);
		if (tileEntity != null) {
			blockpos = tileEntity.getPos();
			double distance = Math.sqrt(posX * posX + posY * posY + posZ * posZ);
			IBlockState iblockstate = tileEntity.getWorld().getBlockState(blockpos);

			int meta = iblockstate.getBlock().getMetaFromState(iblockstate);
			if (meta == 2) {
				angle = 180.0F;
			}

			if (meta == 4) {
				angle = 90.0F;
			}

			if (meta == 5) {
				angle = -90.0F;
			}
		}

		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		GlStateManager.translate((float) posX + 0.5f, (float) posY + 1.5d, (float) posZ + 0.5f);
		GlStateManager.scale(-1, -1, 1);
		GlStateManager.rotate(180f, 0, 1, 0);
		GlStateManager.rotate(angle, 0, 1, 0);

		int i = blockpos.getX();
		int j = blockpos.getY();
		int k = blockpos.getZ();

		if (tileEntity != null) {
			
			if (destroyProgress < 0) {
				FontRenderer fr = this.getFontRenderer();
				GlStateManager.pushMatrix();
				GlStateManager.translate(0d, 0.775d, -0.325d);
				GlStateManager.scale(0.00625d, 0.00625d, 0.00625d);
				for (int a=0; a<tileEntity.getLines().size(); a++) {
					String s = tileEntity.getLines().get(a);
					if (a == tileEntity.lineBeingEdited) {
						s = "> "+s+" <";
					}
					fr.drawString(s, -fr.getStringWidth(s)/2f, fr.FONT_HEIGHT*a, Color.BLACK.getRGB(), false);
				}
				GlStateManager.popMatrix();
				GlStateManager.color(1f, 1f, 1f);
		GameProfile profile = tileEntity.getOwner();

		if (profile != null) {
			ResourceLocation resourcelocation = DefaultPlayerSkin.getDefaultSkinLegacy();
			Minecraft minecraft = Minecraft.getMinecraft();
			Map map = minecraft.getSkinManager().loadSkinFromCache(profile);

			if (map.containsKey(Type.SKIN)) {
				resourcelocation = minecraft.getSkinManager().loadSkin((MinecraftProfileTexture) map.get(Type.SKIN),
						Type.SKIN);
			} else {
				UUID uuid = EntityPlayer.getUUID(profile);
				resourcelocation = DefaultPlayerSkin.getDefaultSkin(uuid);
			}
			GlStateManager.pushMatrix();
			GlStateManager.enableAlpha();
			GlStateManager.translate(0, 1.5d, -0.5d);
			GlStateManager.scale(0.5d, 0.5d, 0.5d);
			GlStateManager.rotate(0f, 1, 0, 0);
			GlStateManager.translate(0, 0, 0.15d);
			this.bindTexture(resourcelocation);
			this.headModel.render((Entity) null, 0, 0, 0, 0, 0, 0.0625f);
			GlStateManager.popMatrix();
		}
		}
		}

		if (destroyProgress >= 0) {
			this.bindTexture(DESTROY_STAGES[destroyProgress]);
			GlStateManager.matrixMode(5890);
			GlStateManager.pushMatrix();
			GlStateManager.translate(1F, 1F, 1F);
			GlStateManager.scale(4.0F, 2.0F, 1.0F);
			GlStateManager.matrixMode(5888);
		} else {
			this.bindTexture(GRAVESTONE_TEXTURE);
		}
		gravestoneModel.render(tileEntity != null);

		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
		if (destroyProgress >= 0) {
			GlStateManager.matrixMode(5890);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5888);
		}
	}
}
