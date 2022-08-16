package com.solifungi.pkmskills.common.blocks;

import com.solifungi.pkmskills.common.PokemonSkills;
import com.solifungi.pkmskills.common.init.ModBlocks;
import com.solifungi.pkmskills.common.init.ModCreativeTabs;
import com.solifungi.pkmskills.common.init.ModItems;
import com.solifungi.pkmskills.common.util.interfaces.IHasModel;
import net.minecraft.block.BlockMagma;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBlaze extends BlockMagma implements IHasModel {
    public BlockBlaze(String name)
    {
        super();
        setUnlocalizedName(name);
        setRegistryName(name);
        setLightLevel(0.8F);
        setHardness(2.0F);
        setCreativeTab(ModCreativeTabs.PKM_MISC);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public void registerModels()
    {
        PokemonSkills.proxy.registerItemRenderer(Item.getItemFromBlock(this),0,"inventory");
    }
}
