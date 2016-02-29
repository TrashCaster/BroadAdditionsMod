package com.trashcaster.bam.tileentity;

import java.util.ArrayList;
import java.util.UUID;

import com.mojang.authlib.GameProfile;
import com.trashcaster.bam.BroadAdditionsMod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.actors.threadpool.Arrays;

public class TileEntityGravestone extends TileEntity {

	private ArrayList<String> lines = new ArrayList<String>();
	private ItemStack[] items = new ItemStack[36];
	private ItemStack[] armor = new ItemStack[4];
    private GameProfile playerProfile = null;
    private EntityPlayer activePlayer = null;
	private boolean isEditable = true;
    public int lineBeingEdited = -1;

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

        if (this.playerProfile != null)
        {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            NBTUtil.writeGameProfile(nbttagcompound1, this.playerProfile);
            compound.setTag("Owner", nbttagcompound1);
        }
		NBTTagList list = new NBTTagList();
		for (String s : lines) {
			NBTTagString tag = new NBTTagString(s);
			list.appendTag(tag);
		}
		compound.setTag("Lines", list);

		NBTTagList itemList = new NBTTagList();
		for (int i=0; i<items.length; i++) {
			if (items[i] != null) {
			    NBTTagCompound tag = new NBTTagCompound();
			    tag.setByte("Slot", (byte)i);
			    items[i].writeToNBT(tag);
			    itemList.appendTag(tag);
			}
		}
		compound.setTag("Items", itemList);

		NBTTagList equipmentList = new NBTTagList();
		for (int i=0; i<armor.length; i++) {
			if (armor[i] != null) {
			    NBTTagCompound tag = new NBTTagCompound();
			    tag.setByte("Slot", (byte)i);
			    armor[i].writeToNBT(tag);
			    equipmentList.appendTag(tag);
			}
		}
		compound.setTag("Equipment", equipmentList);
	}

	@Override
    public void markDirty()
    {
    	super.markDirty();
    }

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		lines.clear();
        this.isEditable = false;
        if (compound.hasKey("Owner", 10))
        {
            this.playerProfile = NBTUtil.readGameProfileFromNBT(compound.getCompoundTag("Owner"));
        }
		NBTTagList list = compound.getTagList("Lines", Constants.NBT.TAG_STRING);
		for (int i = 0; i < list.tagCount(); i++) {
			lines.add(list.getStringTagAt(i));
		}
		NBTTagList itemList = compound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < itemList.tagCount(); i++) {
			NBTTagCompound tag = itemList.getCompoundTagAt(i);
			int slot = tag.getByte("Slot") & 255;

            ItemStack itemstack = ItemStack.loadItemStackFromNBT(tag);
            if (itemstack != null && slot >= 0 && slot < items.length) {
            	items[slot] = itemstack;
            }
		}
		NBTTagList equipmentList = compound.getTagList("Equipment", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < equipmentList.tagCount(); i++) {
			NBTTagCompound tag = equipmentList.getCompoundTagAt(i);
			int slot = tag.getByte("Slot") & 255;

            ItemStack itemstack = ItemStack.loadItemStackFromNBT(tag);
            if (itemstack != null && slot >= 0 && slot < armor.length) {
            	armor[slot] = itemstack;
            }
		}
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new S35PacketUpdateTileEntity(this.pos, this.getBlockMetadata(), tag);
	}

	@Override
	public void onDataPacket(net.minecraft.network.NetworkManager net,
			net.minecraft.network.play.server.S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}
	
	public GameProfile getOwner() {
		return this.playerProfile;
	}
	public ItemStack[] getItems() {
		return this.items;
	}
	
	public ItemStack[] getArmor() {
		return this.armor;
	}
	
	public void setItems(ItemStack[] items) {
		this.items = items;
	}
	
	public void setArmor(ItemStack[] armor) {
		this.armor = armor;
	}
	
	public void setOwner(GameProfile owner) {
		this.playerProfile = owner;
	}
    public boolean canRenderBreaking()
    {
    	return true;
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public net.minecraft.util.AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(pos.add(-5, -5, -5), pos.add(6, 6, 6));
	}
	
	public void setLines(String... strings) {
		this.lines.clear();
		for (String s:strings) {
			lines.add(s);
		}
	}

	public ArrayList<String> getLines() {
		if (this.lines.isEmpty()) {
			for (int i=0; i<8; i++) {
				lines.add("");
			}
		}
		return this.lines;
	}

    @SideOnly(Side.CLIENT)
	public void setEditable(boolean editableIn) {
		if (!editableIn) {
			this.activePlayer = null;
		}
		this.isEditable = editableIn;
	}
    
    public boolean getIsEditable() {
    	return this.isEditable;
    }
	
    public void setPlayer(EntityPlayer playerIn)
    {
        this.activePlayer = playerIn;
    }
}
