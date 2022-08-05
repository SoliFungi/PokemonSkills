package com.solifungi.pkmskills.common.items;

import com.solifungi.pkmskills.common.PokemonSkills;
import com.solifungi.pkmskills.common.init.ModCreativeTabs;
import com.solifungi.pkmskills.common.init.ModItems;
import com.solifungi.pkmskills.common.util.handlers.EnumHandler;
import com.solifungi.pkmskills.common.util.interfaces.IHasModel;
import com.solifungi.pkmskills.common.util.interfaces.IMetaName;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemElementCrystal extends Item implements IHasModel, IMetaName
{

    private String name, type;

    public ItemElementCrystal(String name, String type)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(ModCreativeTabs.PKM_MISC);
        setHasSubtypes(true);
        setMaxDamage(0);

        this.name = name;
        this.type = type;

        ModItems.ITEMS.add(this);
    }


    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }

    @Override
    public String getSpecialName(ItemStack stack)
    {
        return EnumHandler.EnumElementType.values()[stack.getItemDamage()].getName();
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return "item." + this.name + "_" + ((IMetaName)this).getSpecialName(stack);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            for(EnumHandler.EnumElementType variant : EnumHandler.EnumElementType.values())
            {
                items.add(new ItemStack(this, 1, variant.getMeta()));
            }
        }
    }

    @Override
    public void registerModels()
    {
        for(int i = 0 ; i < EnumHandler.EnumElementType.values().length ; i++)
        {
            PokemonSkills.proxy.registerVariantRenderer(this, i, "element_" + this.type + "_" +
                    EnumHandler.EnumElementType.values()[i].getName(), "inventory");
        }
    }
}
