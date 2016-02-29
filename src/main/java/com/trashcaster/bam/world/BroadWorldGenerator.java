package com.trashcaster.bam.world;

import java.util.Random;

import com.trashcaster.bam.BroadAdditionsMod;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraftforge.fml.common.IWorldGenerator;

public class BroadWorldGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (random.nextInt(40) == 0) {
			BlockPos pos = new BlockPos(chunkX*16 + random.nextInt(16), random.nextInt(64), chunkZ*16 + random.nextInt(16));
			System.out.println("Generated lake "+pos.toString());
		    new WorldGenLakes(Blocks.packed_ice).generate(world, random, pos);
		}
	}

}
