package com.cjburkey.xerxesplus.config;

import net.minecraftforge.common.config.Configuration;

public class ModConfig {
	
	public static int maxXp;
	public static int maxEnergyQuarry;
	public static int energyRatioQuarry;
	public static int energyMinQuarry;
	public static boolean drawQuarryParticles;
	public static String energyUnits;
	
	public static void commonPreinit(Configuration config) {
		maxXp = config.getInt("maxXp", "xpStore", Integer.MAX_VALUE, 1, Integer.MAX_VALUE, "How much experience the XP Storage block may hold (this is NOT in levels).");
		maxEnergyQuarry = config.getInt("maxEnergyQuarry", "quarry", 1000000, 1, Integer.MAX_VALUE / 2, "How much energy the infinity quarry may hold.");
		energyRatioQuarry = config.getInt("energyRatioQuarry", "quarry", 1000, 1, Integer.MAX_VALUE / 2, "How much energy the quarry uses per block (this is multiplied by the hardness of the block).");
		energyMinQuarry = config.getInt("energyMinQuarry", "quarry", 500, 1, Integer.MAX_VALUE / 2, "How much energy would be required to mine an instant-break block, such as tall grass.");
		drawQuarryParticles = config.getBoolean("drawQuarryParticles", "quarry", true, "Whether or not to draw particles where the quarry mines a block.");
		energyUnits = config.getString("energyUnits", "misc", "FE", "Set this to whatever you want, it's ONLY COSMETIC; no conversion factors are done.");
	}
	
}