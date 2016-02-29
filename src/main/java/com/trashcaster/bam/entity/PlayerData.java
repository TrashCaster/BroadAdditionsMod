package com.trashcaster.bam.entity;

import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.inventory.InventoryAccessories;
import com.trashcaster.bam.network.messages.MessageUpatePlayerData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PlayerData implements IExtendedEntityProperties {
	public final static String EXT_PROP_NAME = "BAM_PlayerData";

	private final EntityPlayer player;
	public final InventoryAccessories inventory = new InventoryAccessories();
	
	public PlayerData(EntityPlayer player) {
		this.player = player;
	}

	/**
	 * Used to register these extended properties for the player during
	 * EntityConstructing event This method is for convenience only; it will
	 * make your code look nicer
	 */
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(PlayerData.EXT_PROP_NAME, new PlayerData(player));
	}

	/**
	 * Returns ExtendedPlayer properties for player This method is for
	 * convenience only; it will make your code look nicer
	 */
	public static final PlayerData get(EntityPlayer player) {
		return (PlayerData) player.getExtendedProperties(EXT_PROP_NAME);
	}

	// Save any custom data that needs saving here
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = new NBTTagCompound();
		this.inventory.writeToNBT(properties);
		compound.setTag(EXT_PROP_NAME, properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		this.inventory.readFromNBT(properties);
	}
	
	@Override
	public void init(Entity entity, World world) {
	}
	
	public void resync() {
		NBTTagCompound data = new NBTTagCompound();
		this.saveNBTData(data);
		BroadAdditionsMod.NETWORK.sendToAll(new MessageUpatePlayerData(this.player, data));
	}
	
	public void resync(EntityPlayer player) {
		NBTTagCompound data = new NBTTagCompound();
		this.saveNBTData(data);
		BroadAdditionsMod.NETWORK.sendTo(new MessageUpatePlayerData(this.player, data), (EntityPlayerMP)player);
	}
}