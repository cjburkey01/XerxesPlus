package com.cjburkey.xerxesplus.container;

import com.cjburkey.xerxesplus.block.BlockQuarry;
import com.cjburkey.xerxesplus.gui.core.SlotUpgradeLocked;
import javax.annotation.Nonnull;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerQuarry extends Container {

    private IInventory playerInv;
    private IInventory inventory;

    public ContainerQuarry(IInventory player, IInventory inventory) {
        playerInv = player;
        this.inventory = inventory;

        addSlots();
    }

    private void addSlots() {
        // Inventory slots
        for (int x = 0; x < 3; x++) {
            addSlotToContainer(new SlotUpgradeLocked(x, inventory, x, BlockQuarry.INV_DEF.getStartInvX() + x * 18, BlockQuarry.INV_DEF.getStartInvY()));
        }

        // Player Inventory Slots
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                addSlotToContainer(new Slot(playerInv, 9 + y * 9 + x, BlockQuarry.INV_DEF.getStartPlyX() + x * 18, BlockQuarry.INV_DEF.getStartPlyY() + y * 18));
            }
        }

        // Hotbar slots
        for (int x = 0; x < 9; x++) {
            addSlotToContainer(new Slot(playerInv, x, BlockQuarry.INV_DEF.getStartPlyX() + x * 18, BlockQuarry.INV_DEF.getStartPlyY() + BlockQuarry.INV_DEF.getHotbarOffset()));
        }
    }

    @Nonnull
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();
            stack = stackInSlot.copy();
            if (index < inventory.getSizeInventory()) {
                if (!mergeItemStack(stackInSlot, inventory.getSizeInventory(), inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(stackInSlot, 0, inventory.getSizeInventory(), false)) {
                return ItemStack.EMPTY;
            }
            if (stackInSlot.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return stack;
    }

    public boolean canInteractWith(@Nonnull EntityPlayer ply) {
        return inventory.isUsableByPlayer(ply);
    }

}
