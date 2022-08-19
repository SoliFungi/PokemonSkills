package com.solifungi.pkmskills.common.blocks.tree;

import com.google.common.base.Predicate;
import com.solifungi.pkmskills.common.PokemonSkills;
import com.solifungi.pkmskills.common.blocks.items.ItemBlockVariants;
import com.solifungi.pkmskills.common.init.ModBlocks;
import com.solifungi.pkmskills.common.init.ModCreativeTabs;
import com.solifungi.pkmskills.common.init.ModItems;
import com.solifungi.pkmskills.common.util.handlers.EnumHandler;
import com.solifungi.pkmskills.common.util.interfaces.IHasModel;
import com.solifungi.pkmskills.common.util.interfaces.IMetaName;
import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;

public class BlockCustomLog extends BlockLog implements IHasModel, IMetaName
{
    private String name;
    private static final int typeNum = EnumHandler.EnumWoodType.values().length;

    public static final PropertyEnum<EnumHandler.EnumWoodType> VARIANT = PropertyEnum.<EnumHandler.EnumWoodType>create("variant", EnumHandler.EnumWoodType.class, new Predicate<EnumHandler.EnumWoodType>()
    {
        public boolean apply(@Nullable EnumHandler.EnumWoodType apply)
        {
            return apply.getMeta() < typeNum;
        }
    });

    public BlockCustomLog(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setSoundType(SoundType.WOOD);
        setCreativeTab(ModCreativeTabs.PKM_MISC);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumHandler.EnumWoodType.MOSSY).withProperty(LOG_AXIS, EnumAxis.Y));
        this.name = name;

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        for(EnumHandler.EnumWoodType type : EnumHandler.EnumWoodType.values())
        {
            items.add(new ItemStack(this,1, type.getMeta()));
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState state = this.getDefaultState().withProperty(VARIANT, EnumHandler.EnumWoodType.byMetadata(meta));

        if((meta & (3 * typeNum)) == 0)
        {
            return state.withProperty(LOG_AXIS, EnumAxis.Y);
        }
        else if((meta & (3 * typeNum)) == typeNum)
        {
            return state.withProperty(LOG_AXIS, EnumAxis.X);
        }
        else if((meta & (3 * typeNum)) == 2 * typeNum)
        {
            return state.withProperty(LOG_AXIS, EnumAxis.Z);
        }
        else return state.withProperty(LOG_AXIS, EnumAxis.NONE);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        //Set default
        int i = 0;
        i = i | ((EnumHandler.EnumWoodType)state.getValue(VARIANT)).getMeta();

        switch((BlockLog.EnumAxis)state.getValue(LOG_AXIS))
        {
            case X:
                i |= typeNum;
                break;

            case Y:
                i |= (2 * typeNum);
                break;

            case Z:
                i |= (3 * typeNum);
                break;
        }

        return i;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT, LOG_AXIS});
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this),1, ((EnumHandler.EnumWoodType)state.getValue(VARIANT)).getMeta());
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return ((EnumHandler.EnumWoodType)state.getValue(VARIANT)).getMeta();
    }

    @Override
    public String getSpecialName(ItemStack stack)
    {
        return EnumHandler.EnumWoodType.values()[stack.getItemDamage()].getName();
    }

    @Override
    public void registerModels()
    {
        for(int i = 0; i < EnumHandler.EnumWoodType.values().length; i++)
        {
            PokemonSkills.proxy.registerVariantRenderer(Item.getItemFromBlock(this), i, "log_" + EnumHandler.EnumWoodType.values()[i].getName(),"inventory");
        }
    }
}
