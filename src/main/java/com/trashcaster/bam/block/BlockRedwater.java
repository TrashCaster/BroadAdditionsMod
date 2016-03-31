package com.trashcaster.bam.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockRedwater extends BlockFluidClassic {

	public BlockRedwater(Fluid fluid, Material material) {
		super(fluid, material);
	}
	
    public boolean canProvidePower()
    {
        return true;
    }

    public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side)
    {
        return 15-((Integer)worldIn.getBlockState(pos).getValue(LEVEL)).intValue();
    }

    public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side)
    {
        return 15-((Integer)worldIn.getBlockState(pos).getValue(LEVEL)).intValue();
    }

    public Boolean isEntityInsideMaterial(World world, BlockPos blockpos, IBlockState iblockstate, Entity entity, double yToTest, Material materialIn, boolean testingHead)
    {
        return materialIn == Material.water;
    }

    public Vec3 modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3 motion)
    {
    	Vec3 accel = this.getFlowVector(worldIn, pos);
        return motion.add(new Vec3(accel.xCoord*0.15d, accel.yCoord*0.15d, accel.zCoord*0.15d));
    }
    
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    	super.updateTick(worldIn, pos, state, rand);

        IBlockState north = worldIn.getBlockState(pos.offset(EnumFacing.NORTH));
        IBlockState south = worldIn.getBlockState(pos.offset(EnumFacing.SOUTH));
        IBlockState east = worldIn.getBlockState(pos.offset(EnumFacing.EAST));
        IBlockState west = worldIn.getBlockState(pos.offset(EnumFacing.WEST));
        IBlockState down = worldIn.getBlockState(pos.offset(EnumFacing.DOWN));

        boolean northB = (north.getBlock() == this && ((BlockFluidClassic)north.getBlock()).isSourceBlock(worldIn, pos.offset(EnumFacing.NORTH)));
        boolean southB = (south.getBlock() == this && ((BlockFluidClassic)south.getBlock()).isSourceBlock(worldIn, pos.offset(EnumFacing.SOUTH)));
        boolean eastB = (east.getBlock() == this && ((BlockFluidClassic)east.getBlock()).isSourceBlock(worldIn, pos.offset(EnumFacing.EAST)));
        boolean westB = (west.getBlock() == this && ((BlockFluidClassic)west.getBlock()).isSourceBlock(worldIn, pos.offset(EnumFacing.WEST)));
        boolean downB = (down.getBlock() == this && ((BlockFluidClassic)down.getBlock()).isSourceBlock(worldIn, pos.offset(EnumFacing.DOWN)));
        
        int neighborSources = 0;

        if (northB)
        	neighborSources ++;
        if (southB)
        	neighborSources++;
        if (eastB)
        	neighborSources++;
        if (westB) 
        	neighborSources++;
        
        if ((northB && southB && downB) || (eastB && westB && downB) || (!downB && neighborSources >= 2)) {
        	System.out.println("Found valid source blocks");
        	worldIn.setBlockState(pos, state.withProperty(LEVEL,0), 2);
        }

        worldIn.notifyNeighborsOfStateChange(pos, this);
        for (EnumFacing face:EnumFacing.VALUES) {
            worldIn.notifyNeighborsOfStateChange(pos.offset(face), this);
        }
    }
}
