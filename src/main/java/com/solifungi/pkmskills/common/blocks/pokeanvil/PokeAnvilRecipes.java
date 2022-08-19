package com.solifungi.pkmskills.common.blocks.pokeanvil;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.solifungi.pkmskills.common.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import java.util.Map;
import java.util.Map.Entry;

public class PokeAnvilRecipes
{
    public static final PokeAnvilRecipes INSTANCE = new PokeAnvilRecipes();
    private final Table<ItemStack, ItemStack, ItemStack> workingList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();

    public static PokeAnvilRecipes getInstance()
    {
        return INSTANCE;
    }

    private PokeAnvilRecipes()
    {
        ItemStack disassemblingHammer = new ItemStack(ModItems.DISASSEMBLING_HAMMER,1,32767);

        //Misc
        addPokeAnvilRecipes(new ItemStack(Items.IRON_INGOT), disassemblingHammer, new ItemStack(ModItems.PLATE_IRON));
        addPokeAnvilRecipes(new ItemStack(ModItems.PLATE_IRON), disassemblingHammer, new ItemStack(ModItems.ROD_IRON,3));

        //Tool (dis)assemble
        for(Entry<Item,Item> entry : ModItems.disassembleMap.entrySet())
        {
            addPokeAnvilRecipes(new ItemStack(entry.getKey(),1,32767), disassemblingHammer, new ItemStack(entry.getValue()));
            addPokeAnvilRecipes(new ItemStack(entry.getValue(),1,32767), ModItems.getHandle(entry.getKey()), new ItemStack(entry.getKey()));
        }
    }

    public void addPokeAnvilRecipes(ItemStack inputLeft, ItemStack inputRight, ItemStack result)
    {
        if(getPokeAnvilResult(inputLeft, inputRight) != ItemStack.EMPTY) return;
        this.workingList.put(inputLeft, inputRight, result);
    }

    public ItemStack getPokeAnvilResult(ItemStack inputLeft, ItemStack inputRight)
    {
        for(Entry<ItemStack, Map<ItemStack, ItemStack>> entry1 : this.workingList.columnMap().entrySet())
        {
            if(this.compareInputAndList(inputRight, (ItemStack)entry1.getKey()))
            {
                for(Entry<ItemStack, ItemStack> entry2 : entry1.getValue().entrySet())
                {
                    if(this.compareInputAndList(inputLeft, (ItemStack)entry2.getKey()))
                    {
                        return (ItemStack)entry2.getValue();
                    }
                }
            }
        }
        return ItemStack.EMPTY;
    }

    private boolean compareInputAndList(ItemStack inputStack, ItemStack listStack)
    {
        return listStack.getItem() == inputStack.getItem() && (listStack.getMetadata() == 32767 || listStack.getMetadata() == inputStack.getMetadata())
                && (inputStack.getCount() >= listStack.getCount());
    }

    public Table<ItemStack, ItemStack, ItemStack> getWorkingList()
    {
        return this.workingList;
    }

}
