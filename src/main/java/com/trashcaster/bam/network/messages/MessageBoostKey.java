package com.trashcaster.bam.network.messages;

import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.util.Util;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageBoostKey implements IMessage {
	
	public MessageBoostKey() {}

	// This is a client to server message. So it is always handled on the server
	public static class ServerHandler implements IMessageHandler<MessageBoostKey, IMessage> {
		@Override
		public IMessage onMessage(final MessageBoostKey message, final MessageContext ctx) {
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
			mainThread.addScheduledTask(new Runnable() {
				@Override
				public void run() {
					EntityPlayer player = ctx.getServerHandler().playerEntity;
					if (Util.getBoost(player) > 0) {
						int boost = Util.getBoost(player);
						ItemStack boots = player.getEquipmentInSlot(1);
						boots.attemptDamageItem(boost*5, player.getRNG());
						player.worldObj.playSoundAtEntity(player, "mob.ghast.fireball", 0.5f, 0f);
						BroadAdditionsMod.NETWORK.sendToAllAround(new MessageSpawnBoostParticles(boost*5, player.posX, player.posY, player.posZ, -player.motionX, -player.motionY, -player.motionZ), new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 120));
						
						if (boots.getItemDamage() >= boots.getMaxDamage()) {
							player.setCurrentItemOrArmor(1, null);
						}
						
						player.fallDistance -= 1f;
					}
				}
			});
			return null;
		}
	}

	// This is a client to server message. So it is always handled on the
	// server. This shouldn't be used
	public static class ClientHandler implements IMessageHandler<MessageBoostKey, IMessage> {
		@Override
		public IMessage onMessage(MessageBoostKey message, MessageContext ctx) {
			return null;
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}
}
