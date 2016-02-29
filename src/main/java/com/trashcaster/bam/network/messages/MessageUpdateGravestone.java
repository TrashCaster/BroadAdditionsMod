package com.trashcaster.bam.network.messages;

import java.util.ArrayList;

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

public class MessageUpdateGravestone implements IMessage {

	protected ArrayList<String> text = new ArrayList<String>();
	protected BlockPos pos;
	
	public MessageUpdateGravestone() {}

	public MessageUpdateGravestone(ArrayList<String> text, BlockPos pos) {
		this.text = text;
		this.pos = pos;
	}

	// This is a client to server message. So it is always handled on the server
	public static class ServerHandler implements IMessageHandler<MessageUpdateGravestone, IMessage> {
		@Override
		public IMessage onMessage(final MessageUpdateGravestone message, final MessageContext ctx) {
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
			mainThread.addScheduledTask(new Runnable() {
				@Override
				public void run() {
					World world = ctx.getServerHandler().playerEntity.worldObj;
					BlockPos pos = message.pos;
					if (world.getTileEntity(pos) instanceof TileEntityGravestone && !pos.equals(new BlockPos(-1,-1,-1))) {
						TileEntityGravestone tileEntity = (TileEntityGravestone) world.getTileEntity(pos);

						NBTTagCompound tag = new NBTTagCompound();
						tileEntity.writeToNBT(tag);

						NBTTagList list = new NBTTagList();
						for (String s : message.text) {
							list.appendTag(new NBTTagString(s));
						}
						tag.setTag("Lines", list);
						
						tileEntity.readFromNBT(tag);
						//tileEntity.markDirty();
					}
				}
			});
			return null;
		}
	}

	// This is a client to server message. So it is always handled on the
	// server. This shouldn't be used
	public static class ClientHandler implements IMessageHandler<MessageUpdateGravestone, IMessage> {
		@Override
		public IMessage onMessage(MessageUpdateGravestone message, MessageContext ctx) {
			return null;
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		int x = buf.readInt();
		int y = buf.readInt();
		int z = buf.readInt();
		this.pos = new BlockPos(x,y,z);
		
		int lines = buf.readInt();
		this.text.clear();
		for (int i=0; i<lines; i++) {
			this.text.add(ByteBufUtils.readUTF8String(buf));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		if (this.pos == null) {
			this.pos = new BlockPos(-1,-1,-1);
		}
		buf.writeInt(this.pos.getX());
		buf.writeInt(this.pos.getY());
		buf.writeInt(this.pos.getZ());
		buf.writeInt(this.text.size());
		for (String s:text) {
			ByteBufUtils.writeUTF8String(buf, s);
		}
	}

}
