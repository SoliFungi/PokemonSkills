package com.solifungi.pkmskills.common.items.tools;

import com.solifungi.pkmskills.common.PokemonSkills;
import com.solifungi.pkmskills.common.init.ModCreativeTabs;
import com.solifungi.pkmskills.common.init.ModItems;
import com.solifungi.pkmskills.common.util.interfaces.IHasModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;

public class ItemShovelPS extends ItemSpade implements IHasModel {
    public ItemShovelPS(String name, ToolMaterial material) {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(ModCreativeTabs.PKM_EQUIP);
        //toolMaterial = material;

        ModItems.ITEMS.add(this);
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
