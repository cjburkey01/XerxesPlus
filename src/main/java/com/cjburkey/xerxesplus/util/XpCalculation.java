package com.cjburkey.xerxesplus.util;

import javax.annotation.Nonnull;
import net.minecraft.entity.player.EntityPlayer;

public class XpCalculation {
	
	private static final int[] xpmap = new int[21863];
	public static final int EXP_PER_MB = 20;
	
	public static int xpToLiquid(int xp) {
		return EXP_PER_MB * xp;
	}
	
	public static int liquidToXp(int mb) {
		return mb / EXP_PER_MB;
	}
	
	public static void commonPreinit() {
		int res = 0;
		for (int i = 0; i < 21863; i++) {
			res += getXpBarCapacity(i);
			if (res < 0) {
				res = Integer.MAX_VALUE;
			}
			xpmap[i] = res;
		}
	}
	
	public static int getXpBarCapacity(int level) {
		if (level >= 30) {
			return 112 + (level - 30) * 9;
		} else if (level >= 15) {
			return 37 + (level - 15) * 5;
		}
		return 7 + level * 2;
	}
	
	public static void takeXpFromPlayer(EntityPlayer ply, int exp) {
		ply.addScore(-exp);
		int j = Integer.MIN_VALUE + ply.experienceTotal;
		if (exp < j) {
			exp = j;
		}
		ply.experience -= (float) exp / (float) ply.xpBarCap();
		ply.experienceTotal -= exp;
		while(ply.experience < 0.0F) {
			ply.experience = (ply.experience + 1.0F) * (float) ply.xpBarCap();
			ply.experienceLevel --;
			ply.experience /= (float) ply.xpBarCap();
		}
	}
	
	public static int getExperienceForLevel(int level) {
		if (level <= 0) {
			return 0;
		}
		if (level >= 21863) {
			return Integer.MAX_VALUE;
		}
		return xpmap[level];
	}
	
	public static int getLevelForExperience(int experience) {
	    for (int i = 1; i < xpmap.length; i++) {
			if (xpmap[i] > experience) {
				return i - 1;
			}
		}
		return xpmap.length;
	}
	
	public static int getPlayerXP(EntityPlayer player) {
		return (int) (getExperienceForLevel(player.experienceLevel) + (player.experience * player.xpBarCap()));
	}
	
	public static void addPlayerXP(EntityPlayer player, int amount) {
		int experience = Math.max(0, getPlayerXP(player) + amount);
		player.experienceTotal = experience;
		player.experienceLevel = getLevelForExperience(experience);
		int expForLevel = getExperienceForLevel(player.experienceLevel);
		player.experience = (float) (experience - expForLevel) / (float) player.xpBarCap();
	}
	
}