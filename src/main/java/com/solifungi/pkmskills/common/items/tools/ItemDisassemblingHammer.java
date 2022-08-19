package com.solifungi.pkmskills.common.items.tools;

import com.google.common.collect.Sets;
import com.solifungi.pkmskills.common.PokemonSkills;
import com.solifungi.pkmskills.common.init.ModBlocks;
import com.solifungi.pkmskills.common.init.ModCreativeTabs;
import com.solifungi.pkmskills.common.init.ModItems;
import com.solifungi.pkmskills.common.util.interfaces.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import java.util.Set;

public class ItemDisassemblingHammer extends ItemTool implements IHasModel
{
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.ANVIL, ModBlocks.POKE_ANVIL);

    public ItemDisassemblingHammer(String name) {
        super(ToolMaterial.IRON, EFFECTIVE_ON);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.attackDamage = 9.0F;
        this.attackSpeed = -3.4F;
        this.setMaxDamage(495);
        this.setCreativeTab(ModCreativeTabs.PKM_MISC);

        ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels()
    {
        PokemonSkills.proxy.registerItemRenderer(this,0,"inventory");
    }
}
