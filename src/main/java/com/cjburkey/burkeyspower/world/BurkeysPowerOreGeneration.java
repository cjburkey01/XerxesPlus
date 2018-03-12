package com.cjburkey.burkeyspower.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.cjburkey.burkeyspower.BurkeysPower;
import com.cjburkey.burkeyspower.block.ModBlocks;
import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class BurkeysPowerOreGeneration implements IWorldGenerator {
	
	public static boolean debug = false;
	
	private List<WorldGenDefinition> generators = new ArrayList<>();
	
	public BurkeysPowerOreGeneration() {
		// Y-level 5 to 16 in dimension 0(overworld)
		generators.add(new WorldGenDefinition(5, 16, 2, new int[] { 0 }, ModBlocks.blockMeticulumOre, new int[] { 1, 2, 3 }));
	}
	
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		for (WorldGenDefinition generator : generators) {
			generator.generate(world, random, chunkX, chunkZ);
		}
	}
	
	private class WorldGenDefinition {
		
		private int minY;
		private int maxY;
		private int tries;
		private int[] dimensions;
		private WorldGenMinable[] generators;
		
		public WorldGenDefinition(int minY, int maxY, int tries, int[] dimensions, Block block, int[] sizes) {
			if (minY > maxY || minY < 0 || maxY > 255) {
				throw new IllegalArgumentException("Ore generated out of bounds: min - max = " + minY + " - " + maxY);
			}
			this.minY = minY;
			this.maxY = maxY;
			this.tries = tries;
			this.dimensions = dimensions;
			generators = new WorldGenMinable[sizes.length];
			for (int i = 0; i < sizes.length; i ++) {
				generators[i] = new WorldGenMinable(block.getDefaultState(), sizes[i], new Pred());
			}
		}
		
		public void generate(World world, Random random, int chunkX, int chunkZ) {
			boolean inDim = false;
			for (int dim : dimensions) {
				if (world.provider.getDimension() == dim) {
					inDim = true;
					break;
				}
			}
			if (!inDim) {
				return;
			}
			int heightDifference = maxY - minY + 1;
			for (int i = 0; i < tries; i ++) {
				int x = chunkX * 16 + random.nextInt(16);
				int y = minY + random.nextInt(heightDifference);
				int z = chunkZ * 16 + random.nextInt(16);
				pickGenerator(random).generate(world, random, new BlockPos(x, y, z));
				
				if (debug) {
					BurkeysPower.logger.info("Generating ore at: " + x + ", " + y + ", " + z);
				}
			}
		}
		
		private WorldGenMinable pickGenerator(Random random) {
			return generators[random.nextInt(generators.length)];
		}
		
	}
	
	private class Pred implements Predicate<IBlockState> {
		
		public boolean apply(IBlockState state) {
			return true;
		}
		
	}
	
}