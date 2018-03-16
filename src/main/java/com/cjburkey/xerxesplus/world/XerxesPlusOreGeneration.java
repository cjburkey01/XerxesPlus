package com.cjburkey.xerxesplus.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.cjburkey.xerxesplus.XerxesPlus;
import com.cjburkey.xerxesplus.block.ModBlocks;
import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class XerxesPlusOreGeneration implements IWorldGenerator {
	
	public static boolean debug = false;
	
	private List<WorldGenDefinition> generators = new ArrayList<>();
	
	public XerxesPlusOreGeneration() {
		// Y-level 5 to 16 in all dimensions but the end(1) and the nether(-1)
		generators.add(new WorldGenDefinition(5, 16, 4, false, new int[] { -1, 1 }, ModBlocks.blockMeticulumOre, new int[] { 1, 2 }));
	}
	
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		for (WorldGenDefinition generator : generators) {
			generator.generate(world, random, chunkX, chunkZ);
		}
	}
	
	private class WorldGenDefinition {
		
		private final int minY;
		private final int maxY;
		private final int tries;
		private final boolean whitelist;
		private final int[] dimensions;
		private final WorldGenMinable[] generators;
		
		/**
		 * Defines a world generator
		 * @param minY			The minimum y-level for the ore to generate
		 * @param maxY			The maximum y-level for the ore to generate
		 * @param tries			The number of attempts to generate per chunk
		 * @param dimensions	The list of dimensions in which this ore may generate
		 * @param block			The ore block to generate
		 * @param sizes			A list of possible vein sizes
		 */
		public WorldGenDefinition(int minY, int maxY, int tries, int[] dimensions, Block block, int[] sizes) {
			this(minY, maxY, tries, true, dimensions, block, sizes);
		}
		
		/**
		 * Defines a world generator
		 * @param minY			The minimum y-level for the ore to generate
		 * @param maxY			The maximum y-level for the ore to generate
		 * @param tries			The number of attempts to generate per chunk
		 * @param whitelist		Whether or not the dimension list will be inclusive (true) or exclusive (false)
		 * @param dimensions	A whitelisted or blacklisted list of dimensions
		 * @param block			The ore block to generate
		 * @param sizes			A list of possible vein sizes
		 */
		public WorldGenDefinition(int minY, int maxY, int tries, boolean whitelist, int[] dimensions, Block block, int[] sizes) {
			if (minY > maxY || minY < 0 || maxY > 255) {
				throw new IllegalArgumentException("Ore generated out of bounds: min - max = " + minY + " - " + maxY);
			}
			this.minY = minY;
			this.maxY = maxY;
			this.tries = tries;
			this.whitelist = whitelist;
			this.dimensions = dimensions;
			generators = new WorldGenMinable[sizes.length];
			for (int i = 0; i < sizes.length; i ++) {
				generators[i] = new WorldGenMinable(block.getDefaultState(), sizes[i], new Pred());
			}
		}
		
		/**
		 * Attempts to generate this ore in a given chunk
		 * @param world		The world in which to generate the ore
		 * @param random	A random value to use to generate the ore patches
		 * @param chunkX	The x-position of the chunk in which to generate the ore patches
		 * @param chunkZ	The y-position of the chunk in which to generate the ore patches
		 */
		public void generate(World world, Random random, int chunkX, int chunkZ) {
			boolean inDim = false;
			for (int dim : dimensions) {
				if ((whitelist && world.provider.getDimension() == dim) || (!whitelist && world.provider.getDimension() != dim)) {
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
					XerxesPlus.logger.info("Generating ore at: " + x + ", " + y + ", " + z);
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