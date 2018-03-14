package com.cjburkey.xerxesplus.tile;

import com.cjburkey.xerxesplus.proxy.CommonProxy;
import com.cjburkey.xerxesplus.util.XpCalculation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityXpStore extends TileEntityInventory {

	public int experienceTotal;
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
		if (levels > 0) {	// Take levels from player
			drainPlayerXpToReachPlayerLevel(ply, (all) ? 0 : Math.max(ply.experienceLevel - levels, 0));
		} else {	// Give xp to player
			givePlayerXp(ply, (all) ? experienceLevel + 1 : Math.abs(levels));
		}
		markDirty();
	}
	
	public int addExperience(int xpToAdd) {
		int j = CommonProxy.maxXp - experienceTotal;
		if (xpToAdd > j) {
			xpToAdd = j;
		}
		experienceTotal += xpToAdd;
		experienceLevel = XpCalculation.getLevelForExperience(experienceTotal);
		experience = (experienceTotal - XpCalculation.getExperienceForLevel(experienceLevel)) / (float) getXpBarCapacity();
		return xpToAdd;
	}
	
	public int getXpBarCapacity() {
		return XpCalculation.getXpBarCapacity(experienceLevel);
	}
	
	public int getXpBarScaled(int scale) {
		int result = (int) (experience * scale);
		return result;
	}
	
	public void givePlayerXp(EntityPlayer player, int levels) {
		for (int i = 0; i < levels && experienceTotal > 0; i++) {
			givePlayerXpLevel(player);
		}
	}
	
	public void givePlayerXpLevel(EntityPlayer player) {
		int currentXP = XpCalculation.getPlayerXP(player);
		int nextLevelXP = XpCalculation.getExperienceForLevel(player.experienceLevel + 1);
		int requiredXP = nextLevelXP - currentXP;

		requiredXP = Math.min(experienceTotal, requiredXP);
		XpCalculation.addPlayerXP(player, requiredXP);

		int newXp = experienceTotal - requiredXP;
		experience = 0;
		experienceLevel = 0;
		experienceTotal = 0;
		addExperience(newXp);
	}
	
	public void drainPlayerXpToReachContainerLevel(EntityPlayer player, int level) {
		int targetXP = XpCalculation.getExperienceForLevel(level);
		int requiredXP = targetXP - experienceTotal;
		if (requiredXP <= 0) {
			return;
		}
		int drainXP = Math.min(requiredXP, XpCalculation.getPlayerXP(player));
		addExperience(drainXP);
		XpCalculation.addPlayerXP(player, -drainXP);
	}
	
	public void drainPlayerXpToReachPlayerLevel(EntityPlayer player, int level) {
		int targetXP = XpCalculation.getExperienceForLevel(level);
		int drainXP = XpCalculation.getPlayerXP(player) - targetXP;
		if (drainXP <= 0) {
			return;
		}
		drainXP = addExperience(drainXP);
		if (drainXP > 0) {
			XpCalculation.addPlayerXP(player, -drainXP);
		}
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}
		nbt.setInteger("experienceTotal", experienceTotal);
		nbt.setFloat("experience", experience);
		nbt.setInteger("experienceLevel", experienceLevel);
		return super.writeToNBT(nbt);
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt == null || !nbt.hasKey("experienceTotal") || !nbt.hasKey("experience") || !nbt.hasKey("experienceLevel")) {
			return;
		}
		experienceTotal = nbt.getInteger("experienceTotal");
		experience = nbt.getFloat("experience");
		experienceLevel = nbt.getInteger("experienceLevel");
	}
	
}