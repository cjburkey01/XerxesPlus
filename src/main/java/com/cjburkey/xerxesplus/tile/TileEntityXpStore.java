package com.cjburkey.xerxesplus.tile;

import com.cjburkey.xerxesplus.XerxesPlus;
import com.cjburkey.xerxesplus.gui.GuiContainerXpStore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityXpStore extends TileEntityInventory {

	private int xpLevel;
	private int xpStored;
	
	public TileEntityXpStore() {
		super("tile_xp_store", 0);
	}
	
	public int getXpLevel() {
		return xpLevel;
	}
	
	public int getXpStored() {
		return xpStored;
	}
	
	// If zero, do nothing. If < 0, take out abs(levels), if > 0, give levels
	public void updateStorage(EntityPlayer ply, int levels) {
		if (levels == 0) {
			return;
		}
		if (levels > 0) {
			if (ply.experienceLevel >= levels) {
				ply.addExperienceLevel(-levels);
				xpLevel += levels;
			} else {
				int plyLevels = ply.experienceLevel;
				int plyExp = (int) Math.floor(ply.experience * GuiContainerXpStore.getSingleLevelToNext(ply.experienceLevel));
				xpLevel += plyLevels;
				xpStored += plyExp;
				ply.experienceLevel = 0;
				ply.experienceTotal = 0;
				ply.experience = 0.0f;
				ply.addExperienceLevel(0);
			}
		} else {
			// TODO: FIX THIS, IT DOESN'T WORK
			if (xpLevel >= Math.abs(levels)) {
				ply.addExperience(GuiContainerXpStore.getLevelToLevel(xpLevel, xpLevel - Math.abs(levels)));
				xpLevel -= Math.abs(levels);
			} else {
				ply.addExperience(GuiContainerXpStore.getLevelToLevel(xpLevel, 0) + xpStored);
				xpLevel = 0;
				xpStored = 0;
			}
			/*if (xpLevel >= Math.abs(levels)) {
				ply.addExperienceLevel(Math.abs(levels));
				xpLevel -= Math.abs(levels);
			} else {
				ply.addExperienceLevel(xpLevel);
				ply.addExperience(xpStored);
				xpLevel = 0;
				xpStored = 0;
			}*/
		}
		markDirty();
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}
		nbt.setInteger("xp_levels_stored", xpLevel);
		nbt.setInteger("xp_stored", xpStored);
		return super.writeToNBT(nbt);
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt == null || !nbt.hasKey("xp_levels_stored") || !nbt.hasKey("xp_stored")) {
			return;
		}
		xpLevel = nbt.getInteger("xp_levels_stored");
		xpStored = nbt.getInteger("xp_stored");
	}
	
}