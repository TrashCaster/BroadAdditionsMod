package com.trashcaster.bam.item;

import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.common.CommonProxy;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemBlockGravestone extends ItemBlock {

	public ItemBlockGravestone(Block block) {
		super(block);
        this.setCreativeTab(CreativeTabs.tabDecorations);
	}
	
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
    {
        if (!world.setBlockState(pos, newState, 3)) return false;

        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() == this.block)
        {
            if (!setTileEntityNBT(world, player, pos, stack)) {

            	player.openGui(BroadAdditionsMod.INSTANCE, CommonProxy.GUI_HANDLER.GRAVESTONE_EDIT_GUI, world, pos.getX(), pos.getY(), pos.getZ());
            }
            this.block.onBlockPlacedBy(world, pos, state, player, stack);
        }

        return true;
    }

}
