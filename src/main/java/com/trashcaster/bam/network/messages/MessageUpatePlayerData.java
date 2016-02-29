package com.trashcaster.bam.network.messages;

import java.util.ArrayList;
import java.util.UUID;

import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.entity.PlayerData;
import com.trashcaster.bam.tileentity.TileEntityGravestone;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
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

public class MessageUpatePlayerData implements IMessage {

	protected UUID player;
	protected NBTTagCompound data;
	
	public MessageUpatePlayerData() {}

	public MessageUpatePlayerData(EntityPlayer player, NBTTagCompound data) {
		this.player = player.getUniqueID();
		this.data = data;
	}

	// this is a server to client message, so the server doesn't need to handle it
	public static class ServerHandler implements IMessageHandler<MessageUpatePlayerData, IMessage> {
		@Override
		public IMessage onMessage(final MessageUpatePlayerData message, final MessageContext ctx) {
			return null;
		}
	}

	public static class ClientHandler implements IMessageHandler<MessageUpatePlayerData, IMessage> {
		@Override
		public IMessage onMessage(MessageUpatePlayerData message, MessageContext ctx) {
			EntityPlayer player = Minecraft.getMinecraft().theWorld.getPlayerEntityByUUID(message.player);
			PlayerData data = PlayerData.get(player);
			data.loadNBTData(message.data);
			System.out.println("Received player data update from server for: "+player.getDisplayNameString());
			return null;
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			this.player = UUID.fromString(ByteBufUtils.readUTF8String(buf));
		    this.data = ByteBufUtils.readTag(buf);
		} catch (Exception e) {
			// it's an exception. just choke it down (sounds dirty)
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.player.toString());
		ByteBufUtils.writeTag(buf, this.data);
	}

}
