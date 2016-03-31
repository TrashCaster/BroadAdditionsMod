package com.trashcaster.bam.network.messages;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSpawnBoostParticles implements IMessage {
	
	int amount;
	double posX,posY,posZ;
	double motionX,motionY,motionZ;

	public MessageSpawnBoostParticles() {}
	
	public MessageSpawnBoostParticles(int amount, double posX, double posY, double posZ, double motionX, double motionY, double motionZ) {
		this.amount = amount;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;

		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
	}

	// This is a server to client message. So it is always handled on the client
	// this shouldnt be used
	public static class ServerHandler implements IMessageHandler<MessageSpawnBoostParticles, IMessage> {
		@Override
		public IMessage onMessage(final MessageSpawnBoostParticles message, final MessageContext ctx) {
			return null;
		}
	}

	// This is a server to client message. So it is always handled on the client
	public static class ClientHandler implements IMessageHandler<MessageSpawnBoostParticles, IMessage> {
		@Override
		public IMessage onMessage(MessageSpawnBoostParticles message, MessageContext ctx) {
			for (int i=0; i<message.amount; i++) {
				Minecraft.getMinecraft().theWorld.spawnParticle(EnumParticleTypes.CLOUD, true, message.posX, message.posY, message.posZ, message.motionX, message.motionY, message.motionZ, 0); 
			}
			return null;
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.amount = buf.readInt();
		this.posX = buf.readDouble();
		this.posY = buf.readDouble();
		this.posZ = buf.readDouble();
		this.motionX = buf.readDouble();
		this.motionY = buf.readDouble();
		this.motionZ = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(amount);
		buf.writeDouble(posX);
		buf.writeDouble(posY);
		buf.writeDouble(posZ);
		buf.writeDouble(motionX);
		buf.writeDouble(motionY);
		buf.writeDouble(motionZ);
	}
}
