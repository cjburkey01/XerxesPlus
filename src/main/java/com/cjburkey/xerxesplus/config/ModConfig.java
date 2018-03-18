package com.cjburkey.xerxesplus.config;

import com.cjburkey.xerxesplus.ModInfo;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = ModInfo.MODID)
@LangKey("xerxessplus.config.title")
public class ModConfig {
	
	public static Quarry quarry = new Quarry(5000000, 5000, 500, true);
	public static ExperienceStore experienceStore = new ExperienceStore(Integer.MAX_VALUE);
	public static Misc misc = new Misc("FE");
	
	public static class Quarry {
		
		@Comment("How much energy the infinity quarry may hold.")
		@RangeInt(min = 1, max = Integer.MAX_VALUE - 1)
		public int maxEnergyQuarry;
		
		@Comment("How much energy the quarry uses per block (this is multiplied by the hardness of the block).")
		@RangeInt(min = 1, max = Integer.MAX_VALUE / 2)
		public int energyRatioQuarry;
		
		@Comment("How much energy would be required to mine an instant-break block, such as tall grass.")
		@RangeInt(min = 1, max = Integer.MAX_VALUE / 2)
		public int energyMinQuarry;
		
		@Comment("Whether or not to draw particles where the quarry mines a block")
		public boolean drawQuarryParticles;
		
		public Quarry(int maxEnergyQuarry, int energyRatioQuarry, int energyMinQuarry, boolean drawQuarryParticles) {
			this.maxEnergyQuarry = maxEnergyQuarry;
			this.energyRatioQuarry = energyRatioQuarry;
			this.energyMinQuarry = energyMinQuarry;
			this.drawQuarryParticles = drawQuarryParticles;
		}
		
	}
	
	public static class ExperienceStore {
		
		@Comment("How much experience the XP Storage block may hold (this is NOT in levels).")
		@RangeInt(min = 1, max = Integer.MAX_VALUE)
		public int maxXp;
		
		public ExperienceStore(int maxXp) {
			this.maxXp = maxXp;
		}
		
	}
	
	public static class Misc {
		
		@Comment("Set this to whatever you want, it's ONLY COSMETIC; no conversion factors are done")
		public String energyUnits;
		
		public Misc(String energyUnits) {
			this.energyUnits = energyUnits;
		}
		
	}
	
	@EventBusSubscriber(modid = ModInfo.MODID)
	private static class EventHandler {
		
		@SubscribeEvent
		public void onConfigChanged(OnConfigChangedEvent e) {
			if (e.getModID().equals(ModInfo.MODID)) {
				ConfigManager.sync(ModInfo.MODID, Type.INSTANCE);
			}
		}
		
	}
	
}