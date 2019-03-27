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

    public static Quarry quarry = new Quarry();
    public static ExperienceStore experienceStore = new ExperienceStore();
    public static Misc misc = new Misc();

    public static class Quarry {

        @Comment("How much energy the infinity quarry may hold")
        @RangeInt(min = 1, max = Integer.MAX_VALUE - 1)
        public int maxEnergyQuarry = 5000000;

        @Comment("How much energy the quarry uses per block (this will be multiplied by the hardness of the block on a per-block basis)")
        @RangeInt(min = 1, max = Integer.MAX_VALUE / 2)
        public int energyRatioQuarry = 5000;

        @Comment("How much energy would be required to mine an instant-break block, such as tall grass")
        @RangeInt(min = 1, max = Integer.MAX_VALUE / 2)
        public int energyMinQuarry = 500;

        @Comment("Whether or not to draw particles where the quarry mines a block")
        public boolean drawQuarryParticles = true;

        private Quarry() {
        }

    }

    public static class ExperienceStore {

        @Comment("How much experience the XP Storage block may hold (this is NOT in levels).")
        @RangeInt(min = 1)
        public int maxXp = Integer.MAX_VALUE;

        private ExperienceStore() {
        }

    }

    public static class Misc {

        @Comment("Set this to whatever you want, it's ONLY COSMETIC; no conversion factors are done")
        public String energyUnits = "FE";

        private Misc() {
        }

    }

    @SuppressWarnings("unused")
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
