package com.trashcaster.bam.network.messages;

import java.util.ArrayList;

import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.common.CommonGuiHandler;
import com.trashcaster.bam.tileentity.TileEntityGravestone;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSwitchInventory implements IMessage {

	protected boolean validSwitch = false;
	
	public MessageSwitchInventory() {}

	public MessageSwitchInventory(boolean valid) {
		this.validSwitch = valid;
	}

	// This is a client to server message. So it is always handled on the server
	public static class ServerHandler implements IMessageHandler<MessageSwitchInventory, IMessage> {
		@Override
		public IMessage onMessage(final MessageSwitchInventory message, final MessageContext ctx) {
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
			mainThread.addScheduledTask(new Runnable() {
				@Override
				public void run() {
					if (message.validSwitch) {
						ctx.getServerHandler().playerEntity.openGui(BroadAdditionsMod.INSTANCE, CommonGuiHandler.GUI_ACCESSORIES, ctx.getServerHandler().playerEntity.worldObj, 0, 0, 0);
					}
				}
			});
			return null;
		}
	}

	// This is a client to server message. So it is always handled on the
	// server. This shouldn't be used
	public static class ClientHandler implements IMessageHandler<MessageSwitchInventory, IMessage> {
		@Override
		public IMessage onMessage(MessageSwitchInventory message, MessageContext ctx) {
			return null;
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.validSwitch = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(this.validSwitch);
	}

}
