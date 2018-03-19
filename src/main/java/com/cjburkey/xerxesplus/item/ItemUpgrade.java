package com.cjburkey.xerxesplus.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemUpgrade extends Item {
	
	public final int upgradeType;
	
	public ItemUpgrade(int type, int maxStack) {
		upgradeType = type;
		setMaxStackSize(maxStack);
	}
	
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
		if (upgradeType == 2) {
			tooltip.add("\u00A74This will expand the width of the quarry by 2 for each upgrade in this stack.");
		}
	}
	
}