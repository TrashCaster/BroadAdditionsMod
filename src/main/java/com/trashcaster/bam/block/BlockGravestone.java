package com.trashcaster.bam.block;

import java.util.List;
import java.util.Random;

import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.common.CommonProxy;
import com.trashcaster.bam.item.ItemBlockGravestone;
import com.trashcaster.bam.tileentity.TileEntityGravestone;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGravestone extends BlockContainer {
	
	public static final String[][] SIGN_STRINGS = {{"In loving","memory"},{"Lest we","forget"},{"God speed"}};

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockGravestone() {
		super(Material.rock);
		GameRegistry.registerBlock(this, ItemBlockGravestone.class, "gravestone");
		setUnlocalizedName(BroadAdditionsMod.MODID + "." + "gravestone");
		setCreativeTab(CreativeTabs.tabDecorations);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		GameRegistry.addRecipe(new ItemStack(this), " S ","SCS","SSS", 'S', new ItemStack(Blocks.stone, 1, (byte)5), 'C', Blocks.chest);
	}

	@Override 
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		EnumFacing face = (EnumFacing) worldIn.getBlockState(pos).getValue(FACING);

		if (face.equals(EnumFacing.NORTH)) {
			setBlockBounds(0.125f, 0, 1f-1f,

			0.875f, 0.875f, 1f-0.625f);
		}
		if (face.equals(EnumFacing.EAST)) {
			setBlockBounds(0.625f, 0, 0.125f,

			1f, 0.875f, 0.875f);
		}
		if (face.equals(EnumFacing.SOUTH)) {
			setBlockBounds(0.125f, 0, 0.625f,

			0.875f, 0.875f, 1f);
		}
		if (face.equals(EnumFacing.WEST)) {
			setBlockBounds(1f-1f, 0, 0.125f,

			1f-0.625f, 0.875f, 0.875f);
		}
	}

	@Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntityGravestone tileEntity = (TileEntityGravestone)worldIn.getTileEntity(pos);
		if (!worldIn.isRemote) {
		    for (ItemStack item:tileEntity.getItems()) {
		    	if (item != null) {
		    		EntityItem itemEntity = new EntityItem(worldIn, pos.getX()+0.5d, pos.getY()+0.5d, pos.getZ()+0.5d, item);
		    		worldIn.spawnEntityInWorld(itemEntity);
		    	}
		    }
		    for (ItemStack item:tileEntity.getArmor()) {
		    	if (item != null) {
		    		EntityItem itemEntity = new EntityItem(worldIn, pos.getX()+0.5d, pos.getY()+0.5d, pos.getZ()+0.5d, item);
		    		worldIn.spawnEntityInWorld(itemEntity);
		    	}
		    }
		}
    	super.breakBlock(worldIn, pos, state);
    }

	@Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te)
    {
		TileEntityGravestone teg = (TileEntityGravestone)te;
		if (teg != null && teg.getOwner() == null) {
			super.harvestBlock(worldIn, player, pos, state, teg);
		}
    }

	@Override
	public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list,
			Entity collidingEntity) {
		setBlockBoundsBasedOnState(worldIn, pos);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		this.setDefaultFacing(worldIn, pos, state);
	}

	private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			Block block = worldIn.getBlockState(pos.north()).getBlock();
			Block block1 = worldIn.getBlockState(pos.south()).getBlock();
			Block block2 = worldIn.getBlockState(pos.west()).getBlock();
			Block block3 = worldIn.getBlockState(pos.east()).getBlock();
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

			if (enumfacing == EnumFacing.NORTH && block.isFullBlock() && !block1.isFullBlock()) {
				enumfacing = EnumFacing.SOUTH;
			} else if (enumfacing == EnumFacing.SOUTH && block1.isFullBlock() && !block.isFullBlock()) {
				enumfacing = EnumFacing.NORTH;
			} else if (enumfacing == EnumFacing.WEST && block2.isFullBlock() && !block3.isFullBlock()) {
				enumfacing = EnumFacing.EAST;
			} else if (enumfacing == EnumFacing.EAST && block3.isFullBlock() && !block2.isFullBlock()) {
				enumfacing = EnumFacing.WEST;
			}

			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
		}
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	public boolean onBlockActivated(World worldIn, final BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntityGravestone tileEntity = (TileEntityGravestone)worldIn.getTileEntity(pos);
		if (tileEntity != null && worldIn.isRemote) {
			playerIn.openGui(BroadAdditionsMod.INSTANCE, CommonProxy.GUI_HANDLER.GRAVESTONE_DISPLAY_GUI, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
	}

	@SideOnly(Side.CLIENT)
	public IBlockState getStateForEntityRender(IBlockState state) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { FACING });
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityGravestone();
	}

}
