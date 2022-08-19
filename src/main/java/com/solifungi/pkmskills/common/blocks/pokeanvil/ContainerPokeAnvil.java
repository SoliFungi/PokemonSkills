package com.solifungi.pkmskills.common.blocks.pokeanvil;

import com.solifungi.pkmskills.common.blocks.pokeanvil.slot.SlotPokeAnvilOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerPokeAnvil extends Container
{
    private final TileEntityPokeAnvil tileEntity;
    private int workTime, totalWorkTime;

    public ContainerPokeAnvil(InventoryPlayer player, TileEntityPokeAnvil tileEntity)
    {
        this.tileEntity = tileEntity;

        //PokeAnvil slots
        this.addSlotToContainer(new Slot(tileEntity,0,27,47));
        this.addSlotToContainer(new Slot(tileEntity,1,76,47));
        this.addSlotToContainer(new SlotPokeAnvilOutput(player.player, tileEntity,2,134,47));

        //27 player storage slots
        for(int y = 0; y < 3; y++)
        {
            for(int x = 0; x < 9; x++)
            {
                this.addSlotToContainer(new Slot(player,x + y * 9 + 9,8 + x * 18,84 + y * 18));
            }
        }

        //9 player hotbar slots
        for(int x = 0; x < 9; x++)
        {
            this.addSlotToContainer(new Slot(player, x,8 + x * 18,142));
        }
    }

    @Override
    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tileEntity);
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for(int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener listener = (IContainerListener)this.listeners.get(i);

            if(this.workTime != this.tileEntity.getField(0))
            {
                listener.sendWindowProperty(this,0, this.tileEntity.getField(0));
            }
            if(this.totalWorkTime != this.tileEntity.getField(1))
            {
                listener.sendWindowProperty(this,1, this.tileEntity.getField(1));
            }
        }

        this.workTime = this.tileEntity.getField(0);
        this.totalWorkTime = this.tileEntity.getField(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.tileEntity.setField(id, data);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.tileEntity.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if(slot != null && slot.getHasStack())
        {
            ItemStack stack1 = slot.getStack();
            stack = stack1.copy();

            if (index == 2)
            {
                if (!this.mergeItemStack(stack1, 3, 39, true))
                {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack1, stack);
            }
            else if (index != 1 && index != 0)
            {
                Slot slot1 = (Slot)this.inventorySlots.get(index + 1);

                if (!PokeAnvilRecipes.getInstance().getPokeAnvilResult(stack1, slot1.getStack()).isEmpty())
                {
                    if (!this.mergeItemStack(stack1, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 3 && index < 30)
                {
                    if (!this.mergeItemStack(stack1, 30, 39, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 30 && index < 39 && !this.mergeItemStack(stack1, 3, 30, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(stack1, 3, 39, false))
            {
                return ItemStack.EMPTY;
            }

            if (stack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (stack1.getCount() == stack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack1);
        }

        return stack;
    }

}
