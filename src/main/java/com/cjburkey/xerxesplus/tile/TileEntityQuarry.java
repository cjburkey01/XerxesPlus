package com.cjburkey.xerxesplus.tile;

import java.util.ArrayList;
import java.util.List;
import com.cjburkey.xerxesplus.block.BlockQuarry;
import com.cjburkey.xerxesplus.config.ModConfig;
import com.cjburkey.xerxesplus.packet.PacketHandler;
import com.cjburkey.xerxesplus.packet.PacketQuarryParticleToClient;
import com.cjburkey.xerxesplus.util.ItemHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityQuarry extends TileEntityInventory implements IEnergyStorage, ITickable {

	public static final int opsPerTick = 5;
	public static final int maxSkipsPerTick = 100;
	
	public TileEntityQuarry() {
		super("tile_quarry", 0);
	}
	
	private boolean init;
	private int energy;
	private int posX;
	private int posY;
	private int posZ;
	private int skips = 0;
	private List<ItemStack> workingOn = new ArrayList<>();
	private boolean energyLow;
	
	public void update() {
		if (world.isRemote) {
			return;
		}
		if (!init) {
			init = true;
			init();
		}
		for (int i = 0; i < opsPerTick; i ++) {
			if (!attemptOperation()) {
				break;
			}
		}
	}
	
	private void init() {
		posX = pos.getX();
		posZ = pos.getZ();
		nextForward();
	}
	
	private boolean attemptOperation() {
		if (!addItems()) {
			return false;
		}
		
		BlockPos pos = new BlockPos(posX, posY, posZ);
		IBlockState state = world.getBlockState(pos);
		
		float hardness = state.getBlockHardness(world, pos);
		if (state.getBlock().equals(Blocks.AIR) || hardness < 0) {	// Bedrock or something else unbreakable
			if (skips ++ > maxSkipsPerTick) {	// We've done enough this tick, wait a bit
				skips = 0;
				return false;
			}
			nextBlock();
			return attemptOperation();
		}

		energyLow = false;
		float energyUse = Math.min(hardness * ModConfig.energyRatioQuarry, ModConfig.energyMinQuarry);
		if (energyUse > energy) {
			energyLow = true;
			return false;	// We don't have enough power this tick, skip to next
		}
		energy -= energyUse;
		
		List<ItemStack> drops = ItemHelper.getDropsForBlock(world, pos, 0);
		workingOn.addAll(drops);
		world.setBlockState(pos, Blocks.AIR.getDefaultState());
		if (ModConfig.drawQuarryParticles) {
			PacketHandler.sendAround(new PacketQuarryParticleToClient(pos.getX(), pos.getY(), pos.getZ()), world, pos, 16);
		}
		
		nextBlock();
		return true;
	}
	
	private boolean addItems() {
		if (workingOn.size() < 1) {
			return true;
		}
		for (int i = 0; i < workingOn.size(); i ++) {
			ItemStack item = workingOn.get(i);
			if (item.isEmpty() || ItemHelper.addStackToAdjacentInventories(item, world, pos)) {
				workingOn.remove(i);
				return addItems();
			}
		}
		return false;
	}
	
	private void nextForward() {
		Vec3i dir = getFacing().getDirectionVec();
		posX += dir.getX();
		posZ += dir.getZ();
		posY = setNextY();
	}
	
	private int setNextY() {
		for (int y = world.getHeight(); y > 0; y --) {
			if (!world.getBlockState(new BlockPos(posX, y, posZ)).getBlock().equals(Blocks.AIR)) {
				return y;
			}
		}
		nextForward();
		return setNextY();
	}
	
	private void nextBlock() {
		posY --;
		if (posY <= 0) {
			nextForward();
		}
		markDirty();
	}
	
	public BlockPos getCurrentPosition() {
		return new BlockPos(posX, posY, posZ);
	}
	
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int diff = Math.min(ModConfig.maxEnergyQuarry - energy, maxReceive);
		if (!simulate) {
			energy += diff;
		}
		markDirty();
		return diff;
	}
	
	public int extractEnergy(int maxExtract, boolean simulate) {
		return 0;
	}
	
	public int getEnergyStored() {
		return energy;
	}
	
	public int getMaxEnergyStored() {
		return ModConfig.maxEnergyQuarry;
	}
	
	public boolean canExtract() {
		return false;
	}
	
	public boolean canReceive() {
		return true;
	}
	
	public boolean isEnergyLow() {
		return energyLow;
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}
		nbt.setBoolean("init", init);
		nbt.setInteger("energy", energy);
		nbt.setInteger("posX", posX);
		nbt.setInteger("posY", posY);
		nbt.setInteger("posZ", posZ);
		return super.writeToNBT(ItemHelper.addItemsToTag("itemsOnHold", nbt, workingOn));
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt == null || !nbt.hasKey("energy") || !nbt.hasKey("posX") || !nbt.hasKey("posY") || !nbt.hasKey("posZ") || !nbt.hasKey("init") || !nbt.hasKey("itemsOnHold")) {
			return;
		}
		init = nbt.getBoolean("init");
		energy = nbt.getInteger("energy");
		posX = nbt.getInteger("posX");
		posY = nbt.getInteger("posY");
		posZ = nbt.getInteger("posZ");
		for (ItemStack stack : ItemHelper.getItemsFromTag("itemsOnHold", nbt)) {
			workingOn.add(stack);
		}
	}
	
	private EnumFacing getFacing() {
		IBlockState state = world.getBlockState(pos);
		return state.getValue(BlockQuarry.FACING);
	}
	
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (facing == null || capability == null) {
			return false;
		}
		if (capability.equals(CapabilityEnergy.ENERGY)) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (!hasCapability(capability, facing)) {
			return null;
		}
		if (capability.equals(CapabilityEnergy.ENERGY)) {
			return (T) this;
		}
		return super.getCapability(capability, facing);
	}
	
}