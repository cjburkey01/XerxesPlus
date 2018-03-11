package com.cjburkey.burkeyspower.crafting;

import java.util.ArrayList;
import java.util.List;
import com.cjburkey.burkeyspower.BurkeysPower;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCMessage;

public class DustRecipe {
	
	private int energy;
	private ItemStack input;
	private ItemStack primaryOutput;
	private ItemStack secondaryOutput;
	private int secondaryChance;
	
	public DustRecipe(int energy, ItemStack input, ItemStack primaryOutput) {
		this(energy, input, primaryOutput, null, 0);
	}
	
	public DustRecipe(int energy, ItemStack input, ItemStack primaryOutput, ItemStack secondaryOutput, int secondaryChance) {
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
	
	private static final List<DustRecipe> recipes = new ArrayList<>();
	
	public static void commonInit() {
		
	}
	
	public static void onAddRecipe(IMCMessage msg) {
		DustRecipe rec = fromNbt(msg.getNBTValue());
		if (rec == null) {
			return;
		}
		recipes.add(rec);
	}
	
	public static void addCrusherRecipe(int energy, ItemStack input, ItemStack primaryOutput, ItemStack secondaryOutput, int secondaryChance) {
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
	
	private static DustRecipe fromNbt(NBTTagCompound sent) {
		if (sent == null) {
			BurkeysPower.logger.error("Failed to add crusher recipe, stack tag missing");
			return null;
		}
		if (!sent.hasKey("energy")) {
			BurkeysPower.logger.error("Failed to add crusher recipe, energy amount missing");
			return null;
		}
		if (!sent.hasKey("input")) {
			BurkeysPower.logger.error("Failed to add crusher recipe, input stack missing");
			return null;
		}
		if (!sent.hasKey("primaryOutput")) {
			BurkeysPower.logger.error("Failed to add crusher recipe, primary output stack missing");
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
			return new DustRecipe(energy, input, primaryOutput, secondaryOutput, secondaryChance);
		} else {
			return new DustRecipe(energy, input, primaryOutput);
		}
	}
	
}