package com.cjburkey.xerxesplus.crafting;

import java.util.ArrayList;
import java.util.List;
import com.cjburkey.xerxesplus.XerxesPlus;
import com.cjburkey.xerxesplus.block.ModBlocks;
import com.cjburkey.xerxesplus.item.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCMessage;

public class PounderRecipe {
	
	public static void commonInit() {
		//registerGlobalCrusherRecipe(200, new ItemStack(ModItems.meticulumIngot, 1), new ItemStack(ModItems.meticulumDust, 1), null, 1);
		//registerGlobalCrusherRecipe(300, new ItemStack(ModBlocks.blockMeticulumOre, 1), new ItemStack(ModItems.meticulumDust, 2), new ItemStack(Items.DIAMOND, 1), 1);
	}
	
	// -- PER-RECIPE VALUES -- //
	
	private int energy;
	private ItemStack input;
	private ItemStack primaryOutput;
	private ItemStack secondaryOutput;
	private int secondaryChance;
	
	public PounderRecipe(int energy, ItemStack input, ItemStack primaryOutput) {
		this(energy, input, primaryOutput, null, 0);
	}
	
	public PounderRecipe(int energy, ItemStack input, ItemStack primaryOutput, ItemStack secondaryOutput, int secondaryChance) {
		this.energy = energy;
		this.input = input;
		this.primaryOutput = primaryOutput;
		this.secondaryOutput = secondaryOutput;
		this.secondaryChance = secondaryChance;
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public ItemStack getInput() {
		return input;
	}
	
	public ItemStack getPrimaryOutput() {
		return primaryOutput;
	}
	
	public ItemStack getSecondaryOutput() {
		return secondaryOutput;
	}
	
	public int getSecondaryChance() {
		return secondaryChance;
	}
	
	// -- STATIC DUST REGISTRATION -- //
	
	private static final List<PounderRecipe> recipes = new ArrayList<>();
	
	public static void onAddRecipe(IMCMessage msg) {
		PounderRecipe rec = fromNbt(msg.getNBTValue());
		if (rec == null) {
			return;
		}
		recipes.add(rec);
	}
	
	public static void registerGlobalCrusherRecipe(int energy, ItemStack input, ItemStack primaryOutput, ItemStack secondaryOutput, int secondaryChance) {
		if (input == null || primaryOutput == null) {
			return;
		}
		NBTTagCompound toSend = new NBTTagCompound();
		toSend.setInteger("energy", energy);
		toSend.setTag("input", new NBTTagCompound());
		toSend.setTag("primaryOutput", new NBTTagCompound());
		if (secondaryOutput != null) {
			toSend.setTag("secondaryOutput", new NBTTagCompound());
		}
		input.writeToNBT(toSend.getCompoundTag("input"));
		primaryOutput.writeToNBT(toSend.getCompoundTag("primaryOutput"));
		if (secondaryOutput != null) {
			secondaryOutput.writeToNBT(toSend.getCompoundTag("secondaryOutput"));
			toSend.setInteger("secondaryChance", secondaryChance);
		}
		FMLInterModComms.sendMessage("ThermalExpansion", "PulverizerRecipe", toSend);
		FMLInterModComms.sendMessage("burkeyspower", "AddPounderRecipe", toSend.copy());
	}
	
	private static PounderRecipe fromNbt(NBTTagCompound sent) {
		if (sent == null) {
			XerxesPlus.logger.error("Failed to add crusher recipe, stack tag missing");
			return null;
		}
		if (!sent.hasKey("energy")) {
			XerxesPlus.logger.error("Failed to add crusher recipe, energy amount missing");
			return null;
		}
		if (!sent.hasKey("input")) {
			XerxesPlus.logger.error("Failed to add crusher recipe, input stack missing");
			return null;
		}
		if (!sent.hasKey("primaryOutput")) {
			XerxesPlus.logger.error("Failed to add crusher recipe, primary output stack missing");
			return null;
		}
		int energy = sent.getInteger("energy");
		ItemStack input = new ItemStack(sent.getCompoundTag("input"));
		ItemStack primaryOutput = new ItemStack(sent.getCompoundTag("primaryOutput"));
		ItemStack secondaryOutput = null;
		int secondaryChance = 0;
		if (sent.hasKey("secondaryOutput")) {
			if (sent.hasKey("secondaryChance")) {
				secondaryChance = sent.getInteger("secondaryChance");
			}
			secondaryOutput = new ItemStack(sent.getCompoundTag("secondaryOutput"));
			return new PounderRecipe(energy, input, primaryOutput, secondaryOutput, secondaryChance);
		} else {
			return new PounderRecipe(energy, input, primaryOutput);
		}
	}
	
}