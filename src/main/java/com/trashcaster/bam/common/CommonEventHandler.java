package com.trashcaster.bam.common;

import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.entity.EntityFlyingCarpet;
import com.trashcaster.bam.entity.PlayerData;
import com.trashcaster.bam.inventory.InventoryAccessories;
import com.trashcaster.bam.item.ItemAccessory;
import com.trashcaster.bam.tileentity.TileEntityGravestone;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonEventHandler {

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer && PlayerData.get((EntityPlayer) event.entity) == null) {
			PlayerData.register((EntityPlayer) event.entity);
		}
		if (event.entity instanceof EntityPlayer
				&& event.entity.getExtendedProperties(PlayerData.EXT_PROP_NAME) == null) {
			event.entity.registerExtendedProperties(PlayerData.EXT_PROP_NAME,
					new PlayerData((EntityPlayer) event.entity));
		}
	}

	@SubscribeEvent
	public void onLivingSpawn(LivingSpawnEvent.CheckSpawn event) {
		if (event.entity instanceof EntityVillager) {
			EntityVillager villager = (EntityVillager) event.entity;
			System.out.println("Spawned villager");
			if (villager.isChild() && villager.worldObj.rand.nextInt(6) == 0) {
				System.out.println("Spawn flying carpet");
				EntityFlyingCarpet carpet = new EntityFlyingCarpet(villager.worldObj, villager.posX, villager.posY,
						villager.posZ, villager.rotationYaw);
				villager.worldObj.spawnEntityInWorld(carpet);
				villager.mountEntity(carpet);
			}
		}
	}

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event) {
		if (event.entityLiving instanceof EntityPlayer && !event.entityLiving.worldObj.isRemote) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			if (!player.worldObj.getGameRules().getBoolean("keepInventory") && (!player.capabilities.allowFlying)) {
				if (player.inventory
						.consumeInventoryItem(Item.getItemFromBlock(BroadAdditionsMod.Content.Misc.GRAVESTONE))) {
					BlockPos pos = player.getPosition();
					for (int i = 16; i > -16; i--) {
						IBlockState targetBlock = player.worldObj.getBlockState(pos.add(0, i, 0));
						if (targetBlock.getBlock().equals(Blocks.air)
								&& player.worldObj.getBlockState(pos.add(0, i - 1, 0)).getBlock()
										.isSideSolid(player.worldObj, pos.add(0, i - 1, 0), EnumFacing.UP)) {
							pos = pos.add(0, i, 0);
							break;
						}
					}
					player.worldObj.setBlockState(pos, BroadAdditionsMod.Content.Misc.GRAVESTONE.onBlockPlaced(
							event.entity.worldObj, pos, player.getHorizontalFacing(), 0.5f, 0.5f, 0.5f, 0, player));
					TileEntityGravestone tileEntity = (TileEntityGravestone) player.worldObj.getTileEntity(pos);
					tileEntity.setLines("R I P", "", player.getName(), "", "You will be", "missed");
					tileEntity.setItems(player.inventory.mainInventory.clone());
					tileEntity.setArmor(player.inventory.armorInventory.clone());
					tileEntity.setOwner(player.getGameProfile());
					tileEntity.markDirty();
					player.inventory.clear();
				}
			}
		}
		if (event.entity instanceof EntityVillager && !event.entity.worldObj.isRemote) {
			EntityVillager villager = (EntityVillager) event.entity;
			if (villager.getCombatTracker().func_94550_c() instanceof EntitySkeleton) {
				villager.entityDropItem(new ItemStack(BroadAdditionsMod.Content.Misc.FLYING_CARPET, 1, (byte) 0),
						0.25f);
			}
		}
	}

	/*
	 * send synced data to players who are just now being tracked by a player
	 */
	@SubscribeEvent
	public void onPlayerStartTracking(PlayerEvent.StartTracking event) {
		if (event.entity instanceof EntityPlayer && !event.entityPlayer.worldObj.isRemote) {
			EntityPlayer otherPlayer = (EntityPlayer) event.entity;
			PlayerData.get(event.entityPlayer).resync(otherPlayer);
		}
	}

	/*
	 * Here we implement our own "onUpdate" method for accessories in the added
	 * inventory
	 */
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			PlayerData data = PlayerData.get(player);
			InventoryAccessories inv = data.inventory;

			for (int i = 0; i < inv.getSizeInventory(); i++) {
				ItemStack stack = inv.getStackInSlot(i);
				if (stack != null && stack.getItem() instanceof ItemAccessory) {
					ItemAccessory item = (ItemAccessory) stack.getItem();
					item.onAccessoryTick(event.entity.worldObj, player, stack, i);
				}
			}
		}
	}
}
