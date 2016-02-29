package com.trashcaster.bam.network.messages;

import com.trashcaster.bam.entity.EntityFlyingCarpet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageRidingJumpKey implements IMessage {

	protected boolean state = false;
	
	public MessageRidingJumpKey() {}

	public MessageRidingJumpKey(boolean state) {
		this.state = state;
	}

	// This is a client to server message. So it is always handled on the server
	public static class ServerHandler implements IMessageHandler<MessageRidingJumpKey, IMessage> {
		@Override
		public IMessage onMessage(final MessageRidingJumpKey message, final MessageContext ctx) {
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
			mainThread.addScheduledTask(new Runnable() {
				@Override
				public void run() {
					Entity riding = ctx.getServerHandler().playerEntity.ridingEntity;
					if (riding != null && riding instanceof EntityFlyingCarpet) {
						EntityFlyingCarpet carpet = (EntityFlyingCarpet)riding;
						carpet.setAscend(message.state);
					}
				}
			});
			return null;
		}
	}

	// This is a client to server message. So it is always handled on the
	// server. This shouldn't be used
	public static class ClientHandler implements IMessageHandler<MessageRidingJumpKey, IMessage> {
		@Override
		public IMessage onMessage(MessageRidingJumpKey message, MessageContext ctx) {
			return null;
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.state = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(this.state);
	}

}
