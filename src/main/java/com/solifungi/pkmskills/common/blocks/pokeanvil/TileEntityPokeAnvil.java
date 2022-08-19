package com.solifungi.pkmskills.common.blocks.pokeanvil;

import com.google.common.collect.Sets;
import com.solifungi.pkmskills.common.init.ModItems;
import com.solifungi.pkmskills.common.items.tools.ItemDisassemblingHammer;
import com.solifungi.pkmskills.common.util.handlers.SoundsHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Set;

public class TileEntityPokeAnvil extends TileEntity implements IInventory, ITickable
{
    private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(3,ItemStack.EMPTY);
    private String customName;
    private int workTime;
    private int totalWorkTime;


    @Override
    public String getName()
    {
        return this.hasCustomName() ? this.customName : "container.poke_anvil";
    }

    @Override
    public boolean hasCustomName()
    {
        return this.customName != null && !this.customName.isEmpty();
    }

    public void setCustomName(String customName)
    {
        this.customName = customName;
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
    }

    @Override
    public int getSizeInventory()
    {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty()
    {
        for(ItemStack stack : this.inventory)
        {
            if(!stack.isEmpty())
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index)
    {
        return (ItemStack) this.inventory.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(this.inventory, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(this.inventory, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        ItemStack itemStack = (ItemStack) this.inventory.get(index);
        boolean flag = !itemStack.isEmpty() && stack.isItemEqual(itemStack) && ItemStack.areItemStackTagsEqual(stack, itemStack);
        this.inventory.set(index, stack);

        if(stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

        if(index + 1 == 1 && !flag)
        {
            ItemStack stack1 = (ItemStack) this.inventory.get(index + 1);
            this.totalWorkTime = this.getWorkTime(stack, stack1);
            this.workTime = 0;
            this.markDirty();
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("WorkTime", (short)this.workTime);
        compound.setInteger("WorkTimeTotal", (short)this.totalWorkTime);
        ItemStackHelper.saveAllItems(compound, this.inventory);

        if(this.hasCustomName())
        {
            compound.setString("CustomName", this.customName);
        }

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.inventory = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.inventory);
        this.workTime = compound.getInteger("WorkTime");
        this.totalWorkTime = compound.getInteger("WorkTimeTotal");

        if(compound.hasKey("CustomName",8))
        {
            this.setCustomName(compound.getString("CustomName"));
        }
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * This function decides every tick if a pokeanvil take motion
     */
    public void update()
    {
        boolean flag = false;

        if(!this.world.isRemote)
        {
            if(this.canWorkWith())
            {
                //Forging sound: 3.0sec(with 0.75s beginning blank)
                if(workTime % 60 == 0)
                {
                    this.world.playSound((EntityPlayer)null, this.pos.getX() + 0.5D, this.pos.getY() + 1.0D, this.pos.getZ() + 0.5D, SoundsHandler.BLOCK_POKEANVIL_FORGING, SoundCategory.BLOCKS,1.0F,1.0F);
                }

                this.workTime += 1;
                if(this.workTime == this.totalWorkTime)
                {
                    //Finishing sound: 1.5sec
                    this.world.playSound((EntityPlayer)null, this.pos.getX() + 0.5D, this.pos.getY() + 1.0D, this.pos.getZ() + 0.5D, SoundsHandler.BLOCK_POKEANVIL_FINISH, SoundCategory.BLOCKS, 0.8F, 1.2F);

                    this.workTime = 0;
                    this.totalWorkTime = this.getWorkTime((ItemStack)this.inventory.get(0), (ItemStack)this.inventory.get(1));
                    this.workWithItem();
                    flag = true;
                }
            }
            else
            {
                this.workTime = 0;
            }
        }

        if(flag)
        {
            this.markDirty();
        }
    }

    public int getWorkTime(ItemStack left, ItemStack right)
    {
        if(ModItems.disassembleMap.containsKey(left.getItem()))
        {
            if(ModItems.disassembleMap.containsValue(left.getItem()))
            {
                return 180;
            }
        }
        return 60;
    }

    /**
     * Judge if the anvil is prepared for working.
     * @return true if the recipe is correct, and the output slot is empty or has the same item as result.
     */
    private boolean canWorkWith()
    {
        if(((ItemStack)this.inventory.get(0)).isEmpty() || ((ItemStack)this.inventory.get(1)).isEmpty())
        {
            return false;
        }
        else
        {
            ItemStack result = PokeAnvilRecipes.getInstance().getPokeAnvilResult((ItemStack)this.inventory.get(0), (ItemStack)this.inventory.get(1));
            if(result.isEmpty())
            {
                return false;
            }
            else
            {
                ItemStack output = (ItemStack)this.inventory.get(2);
                if(output.isEmpty())
                {
                    return true;
                }
                else if(!compareOutputAndResult(output,result))
                {
                    return false;
                }
                else
                {
                    int res = output.getCount() + result.getCount();
                    return res <= getInventoryStackLimit() && res <= output.getMaxStackSize();
                }
            }
        }
    }

    /**
     * This is an instant move to process the inputs.
     */
    public void workWithItem()
    {
        if(this.canWorkWith())
        {
            ItemStack inputLeft = (ItemStack)this.inventory.get(0);
            ItemStack inputRight = (ItemStack)this.inventory.get(1);
            ItemStack output = (ItemStack)this.inventory.get(2);
            ItemStack result = PokeAnvilRecipes.getInstance().getPokeAnvilResult(inputLeft, inputRight);

            /*
             * Type: disassemble, assemble, others
             * inputLeft: tool(disassemble), toolTop(assemble), other(others)
             * inputRight: hammer(disassemble/others), sticks/rods(assemble)
             **/
            if(ModItems.disassembleMap.containsKey(inputLeft.getItem())) //Disassemble, output must be empty
            {
                int meta = inputLeft.getItemDamage();
                NBTTagCompound compound = inputLeft.getTagCompound();
                ItemStack trueResult = new ItemStack(result.getItem(),1, meta, compound);
                this.inventory.set(2, trueResult.copy());

                int damage = inputRight.getItemDamage();
                if(damage + 15 < inputRight.getMaxDamage())
                {
                    inputRight.setItemDamage(damage + 15);
                }
                else
                {
                    inputRight.shrink(1);
                    this.world.playSound((EntityPlayer)null, this.pos.getX() + 0.5D, this.pos.getY() + 1.0D, this.pos.getZ() + 0.5D, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.NEUTRAL,0.8F, 0.8F + this.world.rand.nextFloat() * 0.4F);
                }

                inputLeft.shrink(1);
            }

            else if(ModItems.disassembleMap.containsValue(inputLeft.getItem())) //Assemble, output must be empty
            {
                int meta = inputLeft.getItemDamage();
                NBTTagCompound compound = inputLeft.getTagCompound();
                ItemStack trueResult = new ItemStack(result.getItem(),1, meta, compound);

                this.inventory.set(2, trueResult.copy());

                if(result.getItem() instanceof ItemTool)
                {
                    inputRight.shrink(2);
                }
                else
                {
                    inputRight.shrink(1);
                }

                inputLeft.shrink(1);
            }

            else //Others
            {
                if(output.isEmpty()) {this.inventory.set(2, result.copy());}
                else if(compareOutputAndResult(output, result)) {output.grow(result.getCount());}

                int damage = inputRight.getItemDamage();
                if(damage + 5 < inputRight.getMaxDamage())
                {
                    inputRight.setItemDamage(damage + 5);
                }
                else
                {
                    inputRight.shrink(1);
                    this.world.playSound((EntityPlayer)null, this.pos.getX() + 0.5D, this.pos.getY() + 1.0D, this.pos.getZ() + 0.5D, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.NEUTRAL,0.8F, 0.8F + this.world.rand.nextFloat() * 0.4F);
                }

                inputLeft.shrink(1);
            }

        }
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return this.world.getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64;
    }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return index != 2;
    }

    public String getGuiID()
    {
        return "pkmskills:poke_anvil";
    }

    @Override
    public int getField(int id)
    {
        switch(id)
        {
            case 0:
                return this.workTime;
            case 1:
                return this.totalWorkTime;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value)
    {
        switch(id)
        {
            case 0:
                this.workTime = value;
                break;
            case 1:
                this.totalWorkTime = value;
        }
    }

    @Override
    public int getFieldCount()
    {
        return 2;
    }

    @Override
    public void clear()
    {
        this.inventory.clear();
    }

    private boolean compareOutputAndResult(ItemStack output, ItemStack result)
    {
        return result.getItem() == output.getItem() && result.getMetadata() == output.getMetadata() && result.getTagCompound() == output.getTagCompound();
    }

}
