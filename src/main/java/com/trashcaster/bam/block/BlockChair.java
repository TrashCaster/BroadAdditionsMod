package com.trashcaster.bam.block;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Predicate;
import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.entity.EntitySittable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockChair extends Block {

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	private int rayTraceState;

	private final String variant;

	public BlockChair(Material materialIn, String variant) {
		super(materialIn);
		this.variant = variant;
		GameRegistry.registerBlock(this, variant + "_chair");
		setUnlocalizedName(BroadAdditionsMod.MODID + "." + variant + "_chair");
		setCreativeTab(CreativeTabs.tabDecorations);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	public boolean onBlockActivated(World worldIn, final BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return true;
		} else {
			List<EntitySittable> seats = worldIn.getEntities(EntitySittable.class, new Predicate<EntitySittable>() {
				@Override
				public boolean apply(EntitySittable input) {
					return input.getTilePos().equals(pos);
				}
			});
			if (seats.isEmpty()) {
				EntitySittable seat = new EntitySittable(worldIn, pos);
				if (state != null) {
					EnumFacing facing = (EnumFacing)state.getValue(FACING);
					if (facing.equals(EnumFacing.NORTH)) {
						seat.rotationYaw = 180f;
					}
					if (facing.equals(EnumFacing.EAST)) {
						seat.rotationYaw = -90f;
					}
					if (facing.equals(EnumFacing.SOUTH)) {
						seat.rotationYaw = 0f;
					}
					if (facing.equals(EnumFacing.WEST)) {
						seat.rotationYaw = 90f;
					}
				}
				worldIn.spawnEntityInWorld(seat);
				playerIn.mountEntity(seat);
			} else if (seats.get(0).riddenByEntity != null && seats.get(0).riddenByEntity != playerIn) {
				playerIn.addChatComponentMessage(new ChatComponentTranslation("message.chair.occupied",
						new Object[] { seats.get(0).riddenByEntity.getName() }));
			}
			return true;
		}
	}

	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		//this.setDefaultFacing(worldIn, pos, state);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		float f = 0.125f;
		if (rayTraceState == 0) {
			this.setBlockBounds(f, 0.0F, f, 1f - f, 0.45F, 1f - f);
		} else if (rayTraceState == 1) {
			EnumFacing enumfacing = (EnumFacing) worldIn.getBlockState(pos).getValue(FACING);

			float g = 0.15f;
			float f1 = f;
			float f2 = f;
			float f3 = 1f - f;
			float f4 = f + g;

			if (enumfacing == EnumFacing.NORTH) {
				f1 = f;
				f2 = 1f - g;
				f3 = 1f - f;
				f4 = 1f - f;
			}
			if (enumfacing == EnumFacing.WEST) {
				f1 = 1f - g;
				f2 = f;
				f3 = 1f - f;
				f4 = 1f - f;
			}
			if (enumfacing == EnumFacing.EAST) {
				f1 = f;
				f2 = f;
				f3 = f + g;
				f4 = 1f - f;
			}
			this.setBlockBounds(f1, 0.4F, f2, f3, 1.0F, f4);
		}
	}

    public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos)
    {
    	float f = 0.125f;
        return new AxisAlignedBB((double)pos.getX()+f, (double)pos.getY(), (double)pos.getZ()+f, (double)pos.getX() + 1-f, (double)pos.getY() + 1.1f, (double)pos.getZ() + 1-f);
    }
	
	public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list,
			Entity collidingEntity) {
		if (!(collidingEntity instanceof EntitySittable)) {
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
			float f = 0.125f;
			this.setBlockBounds(f, 0.0F, f, 1f - f, 0.4F, 1f - f);
			super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);

			float g = 0.15f;
			float f1 = f;
			float f2 = f;
			float f3 = 1f - f;
			float f4 = f + g;

			if (enumfacing == EnumFacing.NORTH) {
				f1 = f;
				f2 = 1f - g;
				f3 = 1f - f;
				f4 = 1f - f;
			}
			if (enumfacing == EnumFacing.WEST) {
				f1 = 1f - g;
				f2 = f;
				f3 = 1f - f;
				f4 = 1f - f;
			}
			if (enumfacing == EnumFacing.EAST) {
				f1 = f;
				f2 = f;
				f3 = f + g;
				f4 = 1f - f;
			}

			this.setBlockBounds(f1, 0.4F, f2, f3, 1.0F, f4);
			super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		} else {
			this.setBlockBounds(0f, 0F, 0f, 0f, 0F, 0f);
			super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		}
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

	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
	}

	@SideOnly(Side.CLIENT)
	public IBlockState getStateForEntityRender(IBlockState state) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
	}

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
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { FACING });
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean isFullCube() {
		return false;
	}

	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
		return true;
	}

	public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
		MovingObjectPosition mop = null;
		for (int i = 0; i < 3; i++) {
			rayTraceState = i;
			mop = super.collisionRayTrace(worldIn, pos, start, end);
			if (mop != null) {
				return mop;
			}
		}
		return mop;
	}
}
