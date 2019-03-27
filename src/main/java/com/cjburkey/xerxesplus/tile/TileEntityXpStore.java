package com.cjburkey.xerxesplus.tile;

import com.cjburkey.xerxesplus.config.ModConfig;
import com.cjburkey.xerxesplus.util.XpCalcHelper;
import javax.annotation.Nonnull;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityXpStore extends TileEntityInventory {

    private int experienceTotal;
    public float experience;
    public int experienceLevel;

    public TileEntityXpStore() {
        super("tile_xp_store", 0);
    }

    // If zero, do nothing. If < 0, take out abs(levels), if > 0, add levels
    // Levels are relative to player (1 level for player is not 1 level for store)
    public void updateStorage(EntityPlayer ply, int levels, boolean all) {
        if (levels == 0) {
            return;
        }
        if (levels > 0) {    // Take levels from player
            drainPlayerXpToReachPlayerLevel(ply, (all) ? 0 : Math.max(ply.experienceLevel - levels, 0));
        } else {    // Give xp to player
            givePlayerXp(ply, (all) ? experienceLevel + 1 : Math.abs(levels));
        }
        markDirty();
    }

    private int addExperience(int xpToAdd) {
        int j = ModConfig.experienceStore.maxXp - experienceTotal;
        if (xpToAdd > j) {
            xpToAdd = j;
        }
        experienceTotal += xpToAdd;
        experienceLevel = XpCalcHelper.getLevelForExperience(experienceTotal);
        experience = (experienceTotal - XpCalcHelper.getExperienceForLevel(experienceLevel)) / (float) getXpBarCapacity();
        return xpToAdd;
    }

    public int getXpBarCapacity() {
        return XpCalcHelper.getXpBarCapacity(experienceLevel);
    }

    @SuppressWarnings("unused")
    public int getXpBarScaled(int scale) {
        return (int) (experience * scale);
    }

    private void givePlayerXp(EntityPlayer player, int levels) {
        for (int i = 0; i < levels && experienceTotal > 0; i++) {
            givePlayerXpLevel(player);
        }
    }

    private void givePlayerXpLevel(EntityPlayer player) {
        int currentXP = XpCalcHelper.getPlayerXP(player);
        int nextLevelXP = XpCalcHelper.getExperienceForLevel(player.experienceLevel + 1);
        int requiredXP = nextLevelXP - currentXP;

        requiredXP = Math.min(experienceTotal, requiredXP);
        XpCalcHelper.addPlayerXP(player, requiredXP);

        int newXp = experienceTotal - requiredXP;
        experience = 0;
        experienceLevel = 0;
        experienceTotal = 0;
        addExperience(newXp);
    }

    @SuppressWarnings("unused")
    public void drainPlayerXpToReachContainerLevel(EntityPlayer player, int level) {
        int targetXP = XpCalcHelper.getExperienceForLevel(level);
        int requiredXP = targetXP - experienceTotal;
        if (requiredXP <= 0) {
            return;
        }
        int drainXP = Math.min(requiredXP, XpCalcHelper.getPlayerXP(player));
        addExperience(drainXP);
        XpCalcHelper.addPlayerXP(player, -drainXP);
    }

    private void drainPlayerXpToReachPlayerLevel(EntityPlayer player, int level) {
        int targetXP = XpCalcHelper.getExperienceForLevel(level);
        int drainXP = XpCalcHelper.getPlayerXP(player) - targetXP;
        if (drainXP <= 0) {
            return;
        }
        drainXP = addExperience(drainXP);
        if (drainXP > 0) {
            XpCalcHelper.addPlayerXP(player, -drainXP);
        }
    }

    @Nonnull
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound nbt) {
        nbt.setInteger("experienceTotal", experienceTotal);
        nbt.setFloat("experience", experience);
        nbt.setInteger("experienceLevel", experienceLevel);
        return super.writeToNBT(nbt);
    }

    public void readFromNBT(@Nonnull NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (!nbt.hasKey("experienceTotal") || !nbt.hasKey("experience") || !nbt.hasKey("experienceLevel")) {
            return;
        }
        experienceTotal = nbt.getInteger("experienceTotal");
        experience = nbt.getFloat("experience");
        experienceLevel = nbt.getInteger("experienceLevel");
    }

}
