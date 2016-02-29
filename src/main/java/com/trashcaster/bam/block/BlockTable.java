package com.trashcaster.bam.block;

import java.io.File;
import java.util.List;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

import com.trashcaster.bam.BroadAdditionsMod;
import com.trashcaster.bam.entity.item.EntityStaticItem;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTable extends Block {

	/** Whether this table connects in the northern direction */
	public static final PropertyBool NORTH = PropertyBool.create("north");
	/** Whether this table connects in the eastern direction */
	public static final PropertyBool EAST = PropertyBool.create("east");
	/** Whether this table connects in the southern direction */
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	/** Whether this table connects in the western direction */
	public static final PropertyBool WEST = PropertyBool.create("west");

	private int rayTraceState;

	private final String variant;

	public BlockTable(Material materialIn, String variant) {
		super(materialIn);
		this.variant = variant;
		GameRegistry.registerBlock(this, variant + "_table");
		setUnlocalizedName(BroadAdditionsMod.MODID + "." + variant + "_table");
		setCreativeTab(CreativeTabs.tabDecorations);
		this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, Boolean.valueOf(false))
				.withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false))
				.withProperty(WEST, Boolean.valueOf(false)));
	}

	public boolean onBlockActivated(World worldIn, final BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote || !side.equals(EnumFacing.UP)) {
			return true;
		}
		if (playerIn.getHeldItem() != null && !(playerIn.getHeldItem().getItem() instanceof ItemBlock)) {
			ItemStack item = playerIn.getHeldItem().copy();
			item.stackSize = 1;
			if (!playerIn.capabilities.isCreativeMode) {
				playerIn.getHeldItem().stackSize--;
			}
			EntityStaticItem itemEntity = new EntityStaticItem(worldIn, pos.getX() + hitX, pos.getY() + hitY - 0.0875f,
					pos.getZ() + hitZ, playerIn.rotationYaw, 0f);
			itemEntity.setItem(item);
			worldIn.spawnEntityInWorld(itemEntity);
		}
		return true;
	}

	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		boolean north = this.canConnectTo(worldIn, pos.north());
		boolean south = this.canConnectTo(worldIn, pos.south());
		boolean east = this.canConnectTo(worldIn, pos.east());
		boolean west = this.canConnectTo(worldIn, pos.west());
		if (rayTraceState == 0) {
			this.setBlockBounds(0f, 0.75F, 0f, 1f, 1F, 1f);
		} else if (rayTraceState == 1) {
			if (!(north || west)) {
				this.setBlockBounds(0f, 0F, 0f, 0.125f, 0.875F, 0.125f);
			}
		} else if (rayTraceState == 2) {
			if (!(north || east)) {
				this.setBlockBounds(0.875f, 0F, 0f, 1f, 0.875F, 0.125f);
			}
		} else if (rayTraceState == 3) {
			if (!(south || east)) {
				this.setBlockBounds(0.875f, 0F, 0.875f, 1f, 0.875F, 1f);
			}
		} else if (rayTraceState == 4) {
			if (!(south || west)) {
				this.setBlockBounds(0f, 0F, 0.875f, 0.125f, 0.875F, 1f);
			}
		}
	}

	public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list,
			Entity collidingEntity) {

		this.setBlockBounds(0f, 0.75F, 0f, 1f, 0.875F, 1f);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);

		this.setBlockBounds(0f, 0F, 0f, 0.125f, 0.875F, 0.125f);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);

		this.setBlockBounds(0.875f, 0F, 0f, 1f, 0.875F, 0.125f);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);

		this.setBlockBounds(0.875f, 0F, 0.875f, 1f, 0.875F, 1f);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);

		this.setBlockBounds(0f, 0F, 0.875f, 0.125f, 0.875F, 1f);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
	}

	public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
		MovingObjectPosition mop = null;
		for (int i = 0; i < 5; i++) {
			rayTraceState = i;
			mop = super.collisionRayTrace(worldIn, pos, start, end);
			if (mop != null) {
				return mop;
			}
		}
		return mop;
	}

	public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
		return new AxisAlignedBB((double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), (double) pos.getX() + 1,
				(double) pos.getY() + 0.9375f, (double) pos.getZ() + 1);
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean isFullCube() {
		return false;
	}

	public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos).getBlock();
		return (block instanceof BlockTable && ((BlockTable) block).blockMaterial == this.blockMaterial)
				&& ((BlockTable) block).variant.equals(this.variant);
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
		return true;
	}

	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(NORTH, Boolean.valueOf(this.canConnectTo(worldIn, pos.north())))
				.withProperty(EAST, Boolean.valueOf(this.canConnectTo(worldIn, pos.east())))
				.withProperty(SOUTH, Boolean.valueOf(this.canConnectTo(worldIn, pos.south())))
				.withProperty(WEST, Boolean.valueOf(this.canConnectTo(worldIn, pos.west())));
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { NORTH, EAST, WEST, SOUTH });
	}
}
