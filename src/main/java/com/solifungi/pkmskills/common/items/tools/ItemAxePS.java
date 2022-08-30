package com.solifungi.pkmskills.common.items.tools;

import com.solifungi.pkmskills.common.PokemonSkills;
import com.solifungi.pkmskills.common.init.ModCreativeTabs;
import com.solifungi.pkmskills.common.init.ModItems;
import com.solifungi.pkmskills.common.util.handlers.EnumHandler;
import com.solifungi.pkmskills.common.util.interfaces.IHasModel;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.text.TextFormatting;

public class ItemAxePS extends ItemAxe implements IHasModel {

    private TextFormatting format = null;

    public ItemAxePS(String name, ToolMaterial material, float damage, float speed) {
        super(material, damage, speed);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(ModCreativeTabs.PKM_EQUIP);

        //Apply color format for element tools
        if(material.getRepairItemStack().getItem().equals(ModItems.ELEMENT_CRYSTAL))
        {
            EnumHandler.EnumElementType type = EnumHandler.EnumElementType.byMetadata(material.getRepairItemStack().getMetadata());
            this.format = type.getColor().getColor();
        }

        ModItems.ITEMS.add(this);
    }

    //Apply text format
    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        return format != null ? format + super.getItemStackDisplayName(stack) : super.getItemStackDisplayName(stack);
    }

    @Override
    public void registerModels()
    {
        PokemonSkills.proxy.registerItemRenderer(this,0,"inventory");
    }

    public Item setDisassembleTo(Item toolTop)
    {
        ModItems.putItemInDisassembleMap(ModItems.disassembleMap, this, toolTop);
        return this;
    }

}
